package lrParser.parsing;

import grammar.ProductionRule;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class LrConfig {
    private final Stack<WorkingStackObject> workingStack; // alpha
    private final Stack<String> inputStack; // beta
    private final Stack<Integer> outputBand; // phi

    public LrConfig(List<String> inputStack) {
        this.inputStack = new Stack<>();
        for (int i = inputStack.size() - 1; i >= 0; i--) {
            this.inputStack.push(inputStack.get(i));
        }
        this.workingStack = new Stack<>();
        workingStack.add(new WorkingStackObject(0, ""));
        this.outputBand = new Stack<>();
    }

    public void shift(Integer stateNo, HashMap<GoToPosition, Integer> goToTable) {
        try{
            String firstToken = inputStack.pop();
            GoToPosition position = new GoToPosition(firstToken, stateNo);
            Integer newState = goToTable.get(position);
            if(newState == -1){
                throw new RuntimeException("In state "+stateNo+" with token "+firstToken+" no action is defined");
            }
            workingStack.add(new WorkingStackObject(newState, firstToken));
        }catch (NullPointerException e){
            System.out.println(e);
        }

    }

    public void reduce(HashMap<GoToPosition, Integer> goToTable, ProductionRule productionRuleL,Integer l) {
        int workingStackLastPosition = workingStack.size();
        WorkingStackObject prevWorkingStackObj = workingStack.get(workingStackLastPosition - productionRuleL.getRules().size() - 1);
        GoToPosition goToPosition = new GoToPosition(productionRuleL.getNonTerminal(), prevWorkingStackObj.stateNo);
        Integer newState = goToTable.get(goToPosition);
        outputBand.push(l);
        for (String symbol : productionRuleL.getRules()) {
            workingStack.pop();
        }
        workingStack.push(new WorkingStackObject(newState, productionRuleL.getNonTerminal()));
    }

    public List<WorkingStackObject> getWorkingStack() {
        return workingStack;
    }

    public List<Integer> getOutputBand() {
        return outputBand;
    }

}
