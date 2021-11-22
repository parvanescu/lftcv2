package grammar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
                    terminals.addAll(List.of(lineWithoutBrackets.split(",")));
                }
                if (lineNr == 2) {
                    this.startingSymbol = line;
                }
                if (lineNr > 2) {
                    String[] splitProduction = line.split("->");
                    String[] splitRules = line.split("\\|");
                    ProductionRule productionRule = new ProductionRule(splitProduction[0]);
                    for (String rule : splitRules)
                        productionRule.addProduction(rule);
                }
                lineNr++;
            }
        } catch (Exception ignored) {
        }
    }

    private void checkCFG(){
        for(ProductionRule rule:productionRules){
            if(rule.nonTerminal.length()>2){
                System.out.println("Grammar is not cfg");
            }else{
                char production = rule.nonTerminal.charAt(0);
                if(Character.isLowerCase(production))
                    System.out.println("terminal is in production rule");
            }
        }
    }
}
