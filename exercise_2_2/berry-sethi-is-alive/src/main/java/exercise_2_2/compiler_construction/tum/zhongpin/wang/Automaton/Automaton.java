package exercise_2_2.compiler_construction.tum.zhongpin.wang.Automaton;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

public class Automaton {
    public final List<State> states;
    private State currentState;
    private State startState;
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
        return finalStates.contains(currentState);
    }

    public State getCurrentState() throws RuntimeErrorException {
        checkFinalization();
        return currentState;
    }

    public void setCurrentState(State currentState) throws RuntimeErrorException {
        checkFinalization();
        this.currentState = currentState;
    }

    public void addState(State state) {
        states.add(state);
    }

    public void finalize() throws RuntimeErrorException {
        for (State state: states) {
            if (state.isStart()) {
                if (startState == null) {
                    startState = state;
                } else {
                    throw new RuntimeErrorException(
                        new Error("There can be only one start state!")
                    );
                }
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
        if (startState == null) {
            throw new RuntimeErrorException(
                new Error("There must be a start state!")
            );
        } else {
            this.currentState = startState;
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
        this.currentState = this.currentState.next(c);
    }

    public String toDOTString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("""
        digraph nfa {
            rankdir=LR;
            size="8,5"
            node [shape=none,width=0,height=0,margin=0]; start [label=""];
            node [shape=doublecircle];
            4;5;
            node [shape=circle];
        """);

        for (State state: states) {
            for (Transition transition: state.getTransitions()) {
                stringBuilder.append("    " + state.getName() + " -> " + transition.getNext().getName() + " [label=\"" + transition.getC() + "\"]\n");
            }
        }

        stringBuilder.append("""
            start -> 0;
        }
        """);

        return stringBuilder.toString();
    }
}
