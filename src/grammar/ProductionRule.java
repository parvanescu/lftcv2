package grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionRule that = (ProductionRule) o;
        return Objects.equals(nonTerminal, that.nonTerminal) && Objects.equals(rules, that.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nonTerminal, rules);
    }
}
