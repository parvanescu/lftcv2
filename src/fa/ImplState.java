package fa;

import java.util.ArrayList;
import java.util.List;

public class ImplState implements State{
    List<Transition> transitionList;
    private boolean isFinal;
    String stateName;

    public ImplState(String name){
        this(name,false);
    }

    public ImplState(String name,boolean isFinal){
        this.transitionList = new ArrayList<>();
        this.isFinal = isFinal;
        this.stateName = name;
    }

    @Override
    public State with(Transition tr) {
        this.transitionList.add(tr);
        return this;
    }

    @Override
    public State transit(Character c) {
        return transitionList
                .stream()
                .filter(t->t.isPossible(c))
                .map(Transition::state)
                .findAny()
                .orElseThrow(()-> new IllegalArgumentException("Input not accepted: "+ c));
    }

    @Override
    public boolean isFinal() {
        return this.isFinal;
    }

    public void setIsFinal(boolean isFinal){
        this.isFinal = isFinal;
    }

    public String getStateName(){return this.stateName;}
}
