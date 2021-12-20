package lrParser.parsing;

import grammar.EnhancedGrammar;
import grammar.ProductionRule;
import lrParser.LrItem;
import lrParser.State;

import java.util.*;

class GoToPosition implements Comparable<GoToPosition> {
    public Integer stateNo;
    public String symbol;

    public GoToPosition(String symbol, Integer stateNo) {
        this.stateNo = stateNo;
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "(" + stateNo + "," + symbol + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoToPosition that = (GoToPosition) o;
        return Objects.equals(stateNo, that.stateNo) && Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateNo, symbol);
    }

    @Override
    public int compareTo(GoToPosition o) {
        return o.stateNo;
    }
}

enum ActionType {
    Accept,
    Shift,
    Reduce
}

class Action {
    public ActionType type;
    public Integer productionNo = -1;

    public Action(ActionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return productionNo != -1 ? type + " " + productionNo : type.toString();
    }
}

public class LrTable {
    private final LinkedHashMap<GoToPosition, Integer> goToTablePart;
    private final LinkedHashMap<Integer, Action> actionTablePart;
    private final Parser parser;

    public LrTable(EnhancedGrammar g,Parser p) {
        goToTablePart = new LinkedHashMap<>();
        actionTablePart = new LinkedHashMap<>();
        this.parser = p;
        this.generateTable(g);

    }

    private void generateTable(EnhancedGrammar g) {
        List<State> canonicalCollection = parser.colCan();

        List<String> NuEpsilon = new ArrayList<>();
        NuEpsilon.addAll(g.getGrammar().getNonTerminals());
        NuEpsilon.addAll(g.getGrammar().getTerminals());

        for (State state : canonicalCollection) {

            //gotoTable
            for (String symbol : NuEpsilon) {
                List<LrItem> symbolGoto = parser.goTo(state,symbol);
                State intermediaryState = new State(-1,symbolGoto);
                Integer followingStateNr = canonicalCollection.indexOf(intermediaryState);
                this.goToTablePart.put(new GoToPosition(symbol,state.getStateNr()),followingStateNr);
            }

            // action table
            boolean isShift = true;
            boolean isAccept = false;
            for (LrItem item : state.getLrItems()) {
                if (item.getNonTerminal().equals(g.getGrammar().getStartingSymbol()) && item.getAfterDot().size() == 0) {
                    isAccept = true;
                    break;
                }

                if (item.getAfterDot().size() == 0) {
                    isShift = false;
                }
            }

            if (isAccept) {
                this.actionTablePart.put(state.getStateNr(), new Action(ActionType.Accept));
                continue;
            }

            if (isShift) {
                this.actionTablePart.put(state.getStateNr(), new Action(ActionType.Shift));
            } else {
                Action reduceL = new Action(ActionType.Reduce);
                int productionNo = -1;
                for (LrItem item : state.getLrItems()) {
                    StringBuilder builder = new StringBuilder();
                    item.getBeforeDot().forEach(symbol -> builder.append(symbol).append(" "));
                    item.getAfterDot().forEach(symbol -> builder.append(symbol).append(" "));
                    if (builder.length() > 1)
                        builder.deleteCharAt(builder.length() - 1);
                    ProductionRule productionRule = new ProductionRule(item.getNonTerminal(), builder.toString());
                    Integer intermediaryProductionNo = g.getGrammar().getProductionRules().indexOf(productionRule);
                    if (!intermediaryProductionNo.equals(productionNo) && productionNo != -1) {
                        throw new RuntimeException("Reduce-reduce conflict");
                    }
                    productionNo = intermediaryProductionNo;
                }
                reduceL.productionNo = productionNo;

                this.actionTablePart.put(state.getStateNr(), reduceL);
            }
        }
    }

    public LinkedHashMap<GoToPosition, Integer> getGoToTablePart() {
        return goToTablePart;
    }

    public LinkedHashMap<Integer, Action> getActionTablePart() {
        return actionTablePart;
    }
}
