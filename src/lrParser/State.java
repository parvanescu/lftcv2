package lrParser;

import java.util.List;
import java.util.Objects;

public class State {
    private final List<LrItem> lrItems;
    private final Integer stateNr;

    public State(Integer stateNo, List<LrItem> stateItems) {
        this.lrItems = stateItems;
        this.stateNr = stateNo;
    }

    public List<LrItem> getLrItems() {
        return lrItems;
    }

    public Integer getStateNr() {
        return stateNr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(lrItems, state.lrItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lrItems);
    }

    @Override
    public String toString() {
        return "\ns" + stateNr + "=" + lrItems + "\n";
    }
}
