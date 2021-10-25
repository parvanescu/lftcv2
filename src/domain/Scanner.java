package domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Scanner {
    private final String inputFile;
    private final String tokensFile;
    private final SymbolTable<String> symbolTable;
    private final PIF pif;
    private final List<String> separatorsList;
    private final List<String> reservedWordsList;
    private List<String> exceptionList;

    public Scanner(String inputFile, String tokensFile) {
        this.inputFile = inputFile;
        this.tokensFile = tokensFile;
        this.symbolTable = new SymbolTable<>(10);
        this.pif = new PIF();
        this.separatorsList = new ArrayList<>();
        this.reservedWordsList = new ArrayList<>();
        this.exceptionList = new ArrayList<>();
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
                System.out.println(tokensByLine);
                for (String token : tokensByLine) {

                }
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

                if(tokenCharArray[i] != '\"')
                builtToken = isStringFlag ? builtToken + tokenCharArray[i] : "";
                if(isStringFlag && tokenCharArray[i] == '\"'){
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

    private TokenClass classify(String token) {
        Pattern identifierPattern = Pattern.compile("");
        Pattern constantPattern = Pattern.compile("");
        Pattern separatorPattern = Pattern.compile("");

        Matcher identifierMatcher = identifierPattern.matcher(token);
        Matcher constantMatcher = constantPattern.matcher(token);

        if (identifierMatcher.matches()) {
            return TokenClass.IDENTIFIER;
        }
        return null;
    }

    public SymbolTable<String> getSymbolTable() {
        return symbolTable;
    }

    public PIF getPif() {
        return pif;
    }
}
