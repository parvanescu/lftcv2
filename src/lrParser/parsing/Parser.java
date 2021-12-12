package lrParser.parsing;

import grammar.EnhancedGrammar;
import grammar.ProductionRule;
import lrParser.LrItem;
import lrParser.State;

import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    public EnhancedGrammar G;
    public LrTable table;

    public Parser(EnhancedGrammar g) {
        G = g;
        table = new LrTable(g, this);
    }

    public List<LrItem> closure(List<LrItem> I) {
        List<LrItem> closureSet = new ArrayList<>(I);
        boolean modified;

        do {
            modified = false;
            List<LrItem> copyClosureSet = new ArrayList<>(closureSet);
            for (LrItem lrItem : closureSet) {
                String firstTokenAfterDot;
                if (lrItem.getAfterDot().size() > 0)
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

    public List<LrItem> goTo(State state, String symbol) {
        List<LrItem> newList = new ArrayList<>();
        for (LrItem item : state.getLrItems()) {
            if (item.getAfterDot().size() > 0)
                if (Objects.equals(item.getAfterDot().get(0), symbol)) {
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

    public List<State> colCan() {
        List<String> NuEpsilon = new ArrayList<>();
        NuEpsilon.addAll(G.getGrammar().getNonTerminals());
        NuEpsilon.addAll(G.getGrammar().getTerminals());

        List<State> canonicalCollection = new ArrayList<>();
        ProductionRule initialProduction = G.getGrammar().getProductionRules()
                .stream()
                .filter(rule -> Objects.equals(rule.getNonTerminal(), G.getGrammar().getStartingSymbol()))
                .collect(Collectors.toList())
                .get(0);
        LrItem initialLrItem = new LrItem(initialProduction.getNonTerminal());
        initialLrItem.setBeforeDot(new ArrayList<>());
        initialLrItem.setAfterDot(initialProduction.getRules());

        State initialState = new State(0, closure(new ArrayList<>(List.of(initialLrItem))));

        canonicalCollection.add(initialState);

        boolean modified;

        do {
            modified = false;
            List<State> copyCanonicalCollection = new ArrayList<>(canonicalCollection);
            for (State state : canonicalCollection) {
                for (String symbol : NuEpsilon) {
                    List<LrItem> gotoResult = this.goTo(state, symbol);
                    State intermediaryState = new State(copyCanonicalCollection.get(copyCanonicalCollection.size() - 1).getStateNr() + 1, gotoResult);
                    if (gotoResult.size() != 0 && !canonicalCollection.contains(intermediaryState)) {
                        copyCanonicalCollection.add(intermediaryState);
                        modified = true;
                    }
                }
            }
            if (modified) canonicalCollection = copyCanonicalCollection;
        } while (modified);

        return canonicalCollection;
    }

    public List<Integer> parseSequence(List<String> sequence) {
        LrConfig config = new LrConfig(sequence);
        List<Integer> returnSequence = null;
        var actionTable = this.table.getActionTablePart();
        var gotoTable = this.table.getGoToTablePart();
        boolean end = false;
        do {
            WorkingStackObject lastWorkingStackObj = config.getWorkingStack().get(config.getWorkingStack().size() - 1);
            Action stateAction = actionTable.get(lastWorkingStackObj.stateNo);
            if (stateAction.type == ActionType.Shift) {
                config.shift(lastWorkingStackObj.stateNo, gotoTable);
            } else if (stateAction.type == ActionType.Reduce) {
                ProductionRule lProductionRule = this.G.getGrammar().getProductionRules().get(stateAction.productionNo);
                config.reduce(gotoTable, lProductionRule, stateAction.productionNo);
            } else if (stateAction.type == ActionType.Accept) {
                returnSequence = config.getOutputBand();
                end = true;
            } else {
                throw new RuntimeException("Error");
            }
        } while (!end);

        return returnSequence;
    }
}
