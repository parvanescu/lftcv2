package fa;

public interface FiniteStateMachine {
    FiniteStateMachine switchState(Character c);
    boolean canStop();
}
