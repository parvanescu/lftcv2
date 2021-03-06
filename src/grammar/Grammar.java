package grammar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Grammar {
    Set<String> terminals, nonTerminals;
    String startingSymbol, filename;
    List<ProductionRule> productionRules;

    public Grammar(String filename) {
        this.filename = filename;
        this.nonTerminals = new HashSet<>();
        this.terminals = new HashSet<>();
        this.productionRules = new ArrayList<>();
        this.readFromFile();
        this.checkCFG();
    }

    public Grammar(Grammar grammar) {
        this.filename = grammar.filename;
        this.nonTerminals = new HashSet<>(grammar.nonTerminals);
        this.terminals = new HashSet<>(grammar.terminals);
        this.productionRules = new ArrayList<>(grammar.productionRules);
        this.checkCFG();
    }

    private void readFromFile() {
        File file = new File(this.filename);
        try {
            Scanner fileScanner = new Scanner(file);
            int lineNr = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (lineNr == 0) {
                    String lineWithoutBrackets = line.substring(1, line.length() - 1);
                    nonTerminals.addAll(List.of(lineWithoutBrackets.split(",")));
                }
                if (lineNr == 1) {
                    String lineWithoutBrackets = line.substring(1, line.length() - 1);
                    terminals.addAll(getListOfNonTerminals(lineWithoutBrackets));
                }
                if (lineNr == 2) {
                    this.startingSymbol = line;
                }
                if (lineNr > 2) {
                    String[] splitProduction = line.split("->");
                    String[] splitRules = splitProduction[1].split("\\|");
                    for (String rule : splitRules)
                        this.productionRules.add(new ProductionRule(splitProduction[0], rule));
                }
                lineNr++;
            }
        } catch (Exception ignored) {
        }
    }

    private void checkCFG() {
        for (ProductionRule rule : productionRules) {
            if (rule.nonTerminal.length() > 2) {
                System.out.println("Grammar is not cfg");
                break;
            } else {
                char production = rule.nonTerminal.charAt(0);
                if (Character.isLowerCase(production)) {
                    System.out.println("terminal is in production rule");
                    break;
                }
            }
        }
    }

    public void showMenu() {
        System.out.println("1.Print set of nonTerminals");
        System.out.println("2.Print set of terminals");
        System.out.println("3.Print set of productions");
        System.out.println("4.Print productions for a nonTerminal");
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String input = scanner.nextLine();
                switch (input) {
                    case "1" -> {
                        System.out.println(this.nonTerminals);
                    }
                    case "2" -> {
                        System.out.println(this.terminals);
                    }
                    case "3" -> {
                        this.productionRules.forEach(productionRule -> System.out.println(productionRule.nonTerminal + "->" + productionRule.getStringRule()));
                    }
                    case "4" -> {
                        System.out.println("Please enter a nonTerminal");
                        String nonTerminal = scanner.nextLine();
                        ProductionRule productionRule = this.productionRules.stream().filter(rule -> Objects.equals(rule.nonTerminal, nonTerminal)).collect(Collectors.toList()).get(0);
                        System.out.println(productionRule.getRules());
                    }
                    case "x" -> {
                        throw new RuntimeException("Close console");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Set<String> getTerminals() {
        return terminals;
    }

    public Set<String> getNonTerminals() {
        return nonTerminals;
    }

    public String getStartingSymbol() {
        return startingSymbol;
    }

    public String getFilename() {
        return filename;
    }

    public List<ProductionRule> getProductionRules() {
        return productionRules;
    }

    private static List<String> getListOfNonTerminals(String lineWithoutBrackets){
        List<String> nonTerminalsList = new ArrayList<>();

        for(String element: lineWithoutBrackets.split(",")){
            if(element.contains("-") && element.length() == 3){
                char startingValue = element.split("-")[0].toCharArray()[0];
                char endingValue = element.split("-")[1].toCharArray()[0];
                for (char i = startingValue; i <= endingValue; i++) {
                    nonTerminalsList.add(String.valueOf(i));
                }
            }else nonTerminalsList.add(element);
        }

        return nonTerminalsList;
    }
}
