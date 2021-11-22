package grammar;

import java.util.ArrayList;
import java.util.List;

public class ProductionRule {
    String nonTerminal;
    List<String> outputVariations;

    public ProductionRule(String nonTerminal) {
        this.nonTerminal = nonTerminal;
        this.outputVariations = new ArrayList<>();
    }

    public void addProduction(String production){
        outputVariations.add(production);
    }
}
