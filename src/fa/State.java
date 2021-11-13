package fa;

public interface State {
    State with(Transition tr);
    State transit(Character c);
    boolean isFinal();
    void setIsFinal(boolean isFinal);
    String getStateName();
}
