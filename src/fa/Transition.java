package fa;

public interface Transition {
    boolean isPossible(Character c);
    State state();
}
