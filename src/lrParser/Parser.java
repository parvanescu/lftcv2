package lrParser;

import grammar.EnhancedGrammar;
import grammar.Grammar;
import grammar.ProductionRule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Parser {

    public EnhancedGrammar G;

    public Parser(EnhancedGrammar g) {
        G = g;
    }

    public HashSet<String> closure(List<String> I) {
        HashSet<String> closureSet = new HashSet<>(I);
        boolean modified = false;

        do {
            HashSet<String> copyClosureSet = new HashSet<>(closureSet);
            for (String lrItem : closureSet) {
                var array = lrItem.split("\\.");
                String nonTerminal = array[1].substring(0, 1);
                List<ProductionRule> productionRules = G.getGrammar().getProductionRules()
                        .stream()
                        .filter(productionRule -> Objects.equals(productionRule.getNonTerminal(), nonTerminal)).collect(Collectors.toList());
                for (ProductionRule productionRule : productionRules) {
                    for (String rule : productionRule.getOutputVariations()) {
                        int nonTerminalCount = G.getGrammar().getNonTerminals().stream().filter(rule::contains).collect(Collectors.toList()).size();
                        if (nonTerminalCount == 0) {
                            String ruleBuilder = "[" + productionRule.getNonTerminal() +
                                    "->" + "." + rule + "]";
                            copyClosureSet.add(ruleBuilder);
                        }
                    }
                    modified = true;
                }
            }
            if (modified) closureSet = copyClosureSet;
        } while (modified);
        return closureSet;
    }

    public HashSet<String> goTo(String s, String nonTerminal) {
        String[] splitItems = s.substring(1, s.length() - 1).split("->");
        String lrItemNonTerminal = splitItems[0];
        String lrItemRule = splitItems[1];

        String[] splitRule = lrItemRule.split("\\.");
        if (splitRule[0].endsWith(nonTerminal)){
            String outputItem = "["+lrItemNonTerminal+"->"+splitRule[0].substring(0,splitRule[0].length()-(nonTerminal.length()+1))+"."+nonTerminal+splitRule[1]+"]";
            List<String> closureItems = new ArrayList<>();
            closureItems.add(outputItem);
            return this.closure(closureItems);
        }
        return null;
    }

    public HashSet<String> colCan(){
        HashSet<String> canonicalCollection = new HashSet<>();
        return  null;
    }
}
