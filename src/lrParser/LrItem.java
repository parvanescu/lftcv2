package lrParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LrItem {
    private final String nonTerminal;
    private List<String> beforeDot;
    private List<String> afterDot;

    public LrItem(String nonTerminal){
        this.beforeDot = new ArrayList<>();
        this.afterDot = new ArrayList<>();
        this.nonTerminal = nonTerminal;
    }

    public void setBeforeDot(List<String> beforeDot) {
        this.beforeDot = beforeDot;
    }

    public void setAfterDot(List<String> afterDot) {
        this.afterDot = afterDot;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[").append(nonTerminal).append("->");
        beforeDot.forEach(token-> builder.append(token).append(" "));
        if(beforeDot.size()!=0)
        builder.deleteCharAt(builder.length()-1);
        builder.append(".");
        afterDot.forEach(token->builder.append(token).append(" "));
        if(afterDot.size()!=0)
        builder.deleteCharAt(builder.length()-1);
        builder.append("]");

        return builder.toString();
    }

    public String getNonTerminal() {
        return nonTerminal;
    }

    public List<String> getBeforeDot() {
        return beforeDot;
    }

    public List<String> getAfterDot() {
        return afterDot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LrItem lrItem = (LrItem) o;
        return Objects.equals(nonTerminal, lrItem.nonTerminal) && Objects.equals(beforeDot, lrItem.beforeDot) && Objects.equals(afterDot, lrItem.afterDot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nonTerminal, beforeDot, afterDot);
    }
}
