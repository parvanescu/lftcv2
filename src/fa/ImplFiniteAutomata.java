package fa;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ImplFiniteAutomata implements FiniteStateMachine {

    private State currentState;

    private String file;
    public List<State> stateList;
    private List<String> alphabet;

    public ImplFiniteAutomata(String file) {
        this.file = file;
        init();
    }

    public ImplFiniteAutomata(State initial) {
        this.currentState = initial;
    }

    @Override
    public FiniteStateMachine switchState(Character c) {
        return new ImplFiniteAutomata(this.currentState.transit(c));
    }

    @Override
    public boolean canStop() {
        return this.currentState.isFinal();
    }

    private void init() {
        File faFile = new File(this.file);
        int lineNr = 0;
        try {
            java.util.Scanner fileScanner = new java.util.Scanner(faFile);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().strip();
                switch (lineNr) {
                    case 0 -> {
                        buildSetOfStates(line);
                        lineNr++;
                    }
                    case 1 -> {
                        buildAlphabet(line);
                        lineNr++;
                    }
                    case 2 -> {
                        this.currentState = new ImplState(line);
                        lineNr++;
                    }
                    case 3 -> {
                        this.buildSetOfRules(line);
                        lineNr++;
                    }
                    case 4 -> {
                        this.buildFinalStatesList(line);
                        lineNr++;
                    }
                }
            }
            this.currentState = this.stateList.stream().filter(state->Objects.equals(state.getStateName(),this.currentState.getStateName())).toList().get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void buildSetOfStates(String csvStates) {
        this.stateList = new ArrayList<>();
        List<String> states = List.of(csvStates.split(","));
        for (String stateString : states) {
            State state = new ImplState(stateString);
            stateList.add(state);
        }
    }

    private void buildAlphabet(String alphabetsCsv) {
        this.alphabet = new ArrayList<>();
        List<String> alphabetParts = List.of(alphabetsCsv.split(","));
        this.alphabet.addAll(alphabetParts);
    }

    private void buildSetOfRules(String csvRules) {
        List<String> rulesList = List.of(csvRules.split(";"));
        for (String rule : rulesList) {
            List<String> rulesParts = List.of(rule.split("="));
            List<String> ruleEntry = List.of(rulesParts.get(0).split(","));

            String ruleEntryState = ruleEntry.get(0).substring(1);
            List<Character> ruleAlphabetValues = new ArrayList<>();

            String ruleConsumingValues = ruleEntry.get(1).substring(0, ruleEntry.get(1).length() - 1);
            List<String> valuesIntervals = List.of(ruleConsumingValues.split("\\|"));
            for (String value : valuesIntervals) {
                List<String> interval = List.of(value.split("-"));
                if (interval.size() == 1) {
                    if (interval.get(0).toCharArray()[0] == '*') {
                        this.addAllAlphabetToList(ruleAlphabetValues);
                    } else ruleAlphabetValues.add(interval.get(0).toCharArray()[0]);
                    break;
                }
                char startingValue = interval.get(0).toCharArray()[0];
                char endingValue = interval.get(1).toCharArray()[0];
                for (char i = startingValue; i <= endingValue; i++) {
                    ruleAlphabetValues.add(i);
                }
            }

            Transition newTransition = new ImplTransition(ruleAlphabetValues,
                    this.stateList.stream().filter(state -> Objects.equals(state.getStateName(), rulesParts.get(1))).collect(Collectors.toList()).get(0));
            State stateToUpdate = this.stateList.stream().filter(state -> Objects.equals(state.getStateName(),ruleEntryState)).collect(Collectors.toList()).get(0);
            stateToUpdate.with(newTransition);
        }
    }

    private void addAllAlphabetToList(List<Character> alphabetValues) {
        for (char i = 'a'; i < 'z'; i++) {
            alphabetValues.add(i);
        }
        for (char i = 'A'; i < 'Z'; i++) {
            alphabetValues.add(i);
        }
        for (char i = '0'; i <= '9'; i++) {
            alphabetValues.add(i);
        }
    }

    private void buildFinalStatesList(String csvStates) {
        List<String> finalStatesList = List.of(csvStates.split(","));
        this.stateList.forEach(state -> state.setIsFinal(finalStatesList.contains(state.getStateName())));
    }
}
