package fa;

import java.util.List;

public interface State {
    State with(Transition tr);
    State transit(Character c);
    boolean isFinal();
    void setIsFinal(boolean isFinal);
    String getStateName();
    List<Transition> getTransitionList();
}
