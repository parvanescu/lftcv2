package fa;

import java.util.List;

public class ImplTransition implements Transition{

    private final List<Character> rules;
    private final State next;

    @Override
    public boolean isPossible(Character c) {
        return this.rules.contains(c);
    }

    @Override
    public State state() {
        return this.next;
    }

    public ImplTransition(List<Character> rules, State next) {
        this.rules = rules;
        this.next = next;
    }
}
