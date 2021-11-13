package domain;

import exceptions.InvalidTokenException;
import fa.FiniteStateMachine;
import fa.ImplFiniteAutomata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Scanner {
    private final String inputFile;
    private final String identifierAutomaton;
    private final String integerConstantAutomaton;
    private final String tokensFile;
    private final SymbolTable<String> symbolTable;
    private final PIF pif;
    private final List<String> separatorsList;
    private final List<String> reservedWordsList;
    private final List<InvalidTokenException> exceptionList;

    public Scanner(String inputFile, String tokensFile,String identifierAutomaton,String integerConstantAutomaton) {
        this.inputFile = inputFile;
        this.tokensFile = tokensFile;
        this.symbolTable = new SymbolTable<>(10);
        this.pif = new PIF();
        this.separatorsList = new ArrayList<>();
        this.reservedWordsList = new ArrayList<>();
        this.exceptionList = new ArrayList<>();
        this.identifierAutomaton = identifierAutomaton;
        this.integerConstantAutomaton = integerConstantAutomaton;
    }

    public void startScanning() {
        getTokens();
        buildStAndPif();
    }

    private void buildStAndPif() {
        File programFile = new File(this.inputFile);
        Integer line = 0;
        try {
            java.util.Scanner fileScanner = new java.util.Scanner(programFile);
            while (fileScanner.hasNextLine()) {
                String tokenLine = fileScanner.nextLine().strip();
                List<String> tokensByLine = detect(tokenLine);
                for (int i = 0; i < tokensByLine.size(); i++) {
                    TokenClass tokenClass = classify(tokensByLine.get(i), i > 1 ? tokensByLine.get(i - 1) : null, i < tokensByLine.size() - 1 ? tokensByLine.get(i + 1) : null);
                    if (tokenClass.equals(TokenClass.INVALID_TOKEN))
                        exceptionList.add(new InvalidTokenException(line, i));
                    this.pif.put(new PIFtoken(tokenClass.toString(), this.symbolTable.pos(tokensByLine.get(i))));
                }
                line++;
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void getTokens() {
        File tokenFile = new File(this.tokensFile);
        try {
            java.util.Scanner fileScanner = new java.util.Scanner(tokenFile);
            while (fileScanner.hasNextLine()) {
                String token = fileScanner.nextLine();
                if (token.length() <= 2 && !token.equals("if"))
                    this.separatorsList.add(token);
                else this.reservedWordsList.add(token);
            }
            fileScanner.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> detect(String token) {
        List<String> tokens = new ArrayList<>();
        String builtToken = "";
        String cmpSeparator = "";
        boolean isStringFlag = false;
        char[] tokenCharArray = token.toCharArray();
        for (int i = 0; i < tokenCharArray.length; i++) {
            if (!separatorsList.contains(Character.toString(tokenCharArray[i]))) {
                builtToken += tokenCharArray[i];
            } else {
                if (!isStringFlag) {
                    cmpSeparator += tokenCharArray[i];
                    if (i + 1 < tokenCharArray.length && separatorsList.contains(cmpSeparator + tokenCharArray[i + 1])) {
                        cmpSeparator += tokenCharArray[++i];
                    }
                }

                if (!builtToken.equals("") && !isStringFlag)
                    tokens.add(builtToken);

                if (!cmpSeparator.equals(""))
                    tokens.add(cmpSeparator);

                if (tokenCharArray[i] != '\"')
                    builtToken = isStringFlag ? builtToken + tokenCharArray[i] : "";
                if (isStringFlag && tokenCharArray[i] == '\"') {
                    tokens.add(builtToken);
                    tokens.add("\"");
                    builtToken = "";
                }
                cmpSeparator = "";

                isStringFlag = Character.toString(tokenCharArray[i]).equals("\"") != isStringFlag;
            }
        }

        return tokens.stream().filter(tokenString -> !Objects.equals(tokenString, " ")).collect(Collectors.toList());
    }

    private TokenClass classify(String token, String prevToken, String nextToken) {

        FiniteStateMachine automataForIdentifiers = new ImplFiniteAutomata(this.identifierAutomaton);
        FiniteStateMachine automataForIntConstants = new ImplFiniteAutomata(this.integerConstantAutomaton);
        Pattern stringConstantPattern = Pattern.compile("(^\".*\"$)|(^'.'$)");
        Pattern floatConstantPattern = Pattern.compile("[1-9][0-9]*,[0-9]*?|0");
        Pattern separatorPattern = Pattern.compile("[,; \\[\\](){}<>'\"]");
        Pattern operatorPattern = Pattern.compile("\\+|-|\\*|/|=|<|(<=)|(>=)|>|(!=)|(\\*\\*)|(\\+\\+)");

        Matcher floatConstantMatcher = floatConstantPattern.matcher(token);
        Matcher separatorMatcher = separatorPattern.matcher(token);
        Matcher operatorMatcher = operatorPattern.matcher(token);

        if (reservedWordsList.contains(token)) {
            return TokenClass.RESERVED_WORD;
        }

        try{
            for(int i=0;i< token.length();i++){
                automataForIdentifiers = automataForIdentifiers.switchState(token.charAt(i));
            }
            if(automataForIdentifiers.canStop())
                return TokenClass.IDENTIFIER;
        }catch (Exception ignored){}

        try{
            for(int i=0;i<token.length();i++){
                automataForIntConstants = automataForIntConstants.switchState(token.charAt(i));
            }
            if(automataForIntConstants.canStop())
                return TokenClass.CONSTANT;
        }catch (Exception ignored){}

        if (floatConstantMatcher.matches()) {
            return TokenClass.CONSTANT;
        }
        if (prevToken != null && nextToken != null) {
            Matcher stringConstantMatcher = stringConstantPattern.matcher(prevToken + token + nextToken);
            if (stringConstantMatcher.matches()) {
                return TokenClass.CONSTANT;
            }
        }

        if (separatorMatcher.matches() && !Objects.equals(prevToken, "list")) {
            return TokenClass.SEPARATOR;
        }

        if (operatorMatcher.matches()) {
            return TokenClass.OPERATOR;
        }

        return TokenClass.INVALID_TOKEN;
    }

    public SymbolTable<String> getSymbolTable() {
        return symbolTable;
    }

    public PIF getPif() {
        return pif;
    }

    public List<InvalidTokenException> getExceptionList() {
        return exceptionList;
    }
}
