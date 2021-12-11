package grammar;

import java.util.ArrayList;
import java.util.List;

public class ProductionRule {
    String nonTerminal;
    List<String> rules;

    public ProductionRule(String nonTerminal,String rules) {
        this.nonTerminal = nonTerminal;
        this.rules = new ArrayList<>();
        this.rules.addAll(List.of(rules.split(" ")));
    }

    public String getNonTerminal() {
        return nonTerminal;
    }

    public List<String> getRules() {
        return rules;
    }

    public String getStringRule(){
        StringBuilder builder = new StringBuilder();
        rules.forEach(rule->builder.append(rule).append(" "));
        return builder.toString();
    }
}
