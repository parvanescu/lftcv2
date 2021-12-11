package lrParser;

import grammar.EnhancedGrammar;
import grammar.Grammar;
import grammar.ProductionRule;

import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    public EnhancedGrammar G;

    public Parser(EnhancedGrammar g) {
        G = g;
    }

    public List<LrItem> closure(List<LrItem> I) {
        List<LrItem> closureSet = new ArrayList<>(I);
        boolean modified;

        do {
            modified = false;
            List<LrItem> copyClosureSet = new ArrayList<>(closureSet);
            for (LrItem lrItem : closureSet) {
                String firstTokenAfterDot;
                if(lrItem.getAfterDot().size() > 0)
                    firstTokenAfterDot = lrItem.getAfterDot().get(0);
                else continue;

                if (G.getGrammar().getNonTerminals().contains(firstTokenAfterDot)) {
                    List<ProductionRule> productionRules = G.getGrammar().getProductionRules()
                            .stream()
                            .filter(productionRule -> Objects.equals(productionRule.getNonTerminal(), firstTokenAfterDot)).collect(Collectors.toList());
                    for (ProductionRule productionRule : productionRules) {
                        LrItem item = new LrItem(productionRule.getNonTerminal());
                        item.setBeforeDot(new ArrayList<>());
                        item.setAfterDot(productionRule.getRules());
                        if (!copyClosureSet.contains(item)) {
                            copyClosureSet.add(item);
                            modified = true;
                        }
                    }
                }
            }
            if (modified) closureSet = copyClosureSet;
        } while (modified);
        return closureSet;
    }

    public List<LrItem> goTo(List<LrItem> state, String token) {
        List<LrItem> newList = new ArrayList<>();
        for (LrItem item : state) {
            if (Objects.equals(item.getAfterDot().get(0), token)) {
                LrItem newItem = new LrItem(item.getNonTerminal());

                List<String> beforeDot = new ArrayList<>(item.getBeforeDot());
                beforeDot.add(item.getAfterDot().get(0));

                List<String> afterDot = new ArrayList<>();
                if (item.getAfterDot().size() >= 2) {
                    afterDot.addAll(item.getAfterDot().subList(1, item.getAfterDot().size()));
                }

                newItem.setBeforeDot(beforeDot);
                newItem.setAfterDot(afterDot);

                newList.add(newItem);
            }
        }
        return this.closure(newList);
    }

    public HashSet<String> colCan() {
        HashSet<String> canonicalCollection = new HashSet<>();
        return null;
    }
}
