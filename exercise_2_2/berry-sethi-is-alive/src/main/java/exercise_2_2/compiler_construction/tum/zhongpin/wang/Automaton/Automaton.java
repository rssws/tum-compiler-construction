package exercise_2_2.compiler_construction.tum.zhongpin.wang.Automaton;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

public class Automaton {
    public final List<State> states;
    private List<State> currentStates = new ArrayList<>();
    private List<State> startStates = new ArrayList<>();
    private List<State> finalStates = new ArrayList<>();
    private boolean isFinalized = false;

    public Automaton() {
        this(new ArrayList<>());
    }

    public Automaton(List<State> states) {
        this.states = states;
    }

    public boolean isFinished() throws RuntimeErrorException {
        checkFinalization();
        for (State state: currentStates) {
            if (finalStates.contains(state)) {
                return true;
            }
        }
        return false;
    }

    public List<State> getCurrentState() throws RuntimeErrorException {
        checkFinalization();
        return currentStates;
    }

    public void addState(State state) {
        states.add(state);
    }

    public void finalize() throws RuntimeErrorException {
        for (State state: states) {
            if (state.isStart()) {
                startStates.add(state);
            }
            if (state.isFinal()) {
                finalStates.add(state);
            }
            if (states.stream().filter(s -> s.getName() == state.getName()).count() != 1) {
                throw new RuntimeErrorException(
                    new Error("State name must be unique!")
                );
            }
        }
        if (startStates.size() == 0) {
            throw new RuntimeErrorException(
                new Error("There must be at least one start state!")
            );
        } else {
            this.currentStates.addAll(startStates);
        }
        isFinalized = true;
    }

    private void checkFinalization() throws RuntimeErrorException {
        if (!isFinalized) {
            throw new RuntimeErrorException(
                new Error("Graph needs to be finalized first!")
            );
        }
    }

    public void next(char c) {
        checkFinalization();
        List<State> nextStates = new ArrayList<>();
        for (State state: currentStates) {
            nextStates.addAll(state.next(c));
        }
        this.currentStates.clear();
        this.currentStates.addAll(nextStates);
        if (this.currentStates.size() == 0) {
            this.currentStates.add(State.errorState);
        }
    }
    
    public String toDOTString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("""
        digraph nfa {
            rankdir=LR;
            size="8,5"
            node [shape=none,width=0,height=0,margin=0]; start [label=""];
            node [shape=doublecircle];
        """);
        for (State state: states) {
            if (state.isFinal()) {
                
                stringBuilder.append("    " + state.getName() + "\n");
            }
        }

        stringBuilder.append("""
            node [shape=circle];
        """);

        for (State state: states) {
            for (Transition transition: state.getTransitions()) {
                stringBuilder.append("    " + state.getName() + " -> " + transition.getNext().getName() + " [label=\"" + transition.getC() + "\"]\n");
            }
        }

        for (State state: currentStates) {
            stringBuilder.append("    " + state.getName() + " [style=filled, fillcolor=green]\n");
        }

        stringBuilder.append("""
            start -> 0;
        }
        """);

        return stringBuilder.toString();
    }
}
