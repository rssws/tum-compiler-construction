package exercise_3_3.compiler_construction.tum.zhongpin.wang.Automaton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

public class Automaton {
    public final List<State> states;
    private List<State> currentStates = new ArrayList<>();
    private List<State> startStates = new ArrayList<>();
    private List<State> finalStates = new ArrayList<>();
    private final Automaton partialDFA;
    private final boolean isNFA;

    public Automaton() {
        this(new ArrayList<>(), true, true);
    }

    public Automaton(boolean hasPartialDFA) {
        this(true, hasPartialDFA);
    }

    private Automaton(boolean isNFA, boolean hasPartialDFA) {
        this(new ArrayList<>(), isNFA, hasPartialDFA);
    }

    private Automaton(List<State> states, boolean isNFA, boolean hasPartialDFA) {
        this.states = states;
        this.isNFA = isNFA;
        if (hasPartialDFA) {
            partialDFA = new Automaton(false, false);
        } else {
            partialDFA = null;
        }
    }

    public boolean isFinished() throws RuntimeErrorException {
        for (State state: currentStates) {
            if (finalStates.contains(state)) {
                return true;
            }
        }
        return false;
    }

    public List<State> getCurrentState() throws RuntimeErrorException {
        return currentStates;
    }

    public void addState(State state) {
        if (states.stream().filter(s -> s.getName().equals(state.getName())).count() == 1) {
            throw new RuntimeErrorException(
                new Error("State name must be unique!")
            );
        }

        states.add(state);
        if (state.isStart()) {
            if (partialDFA != null) {
                // This automaton is the NFA with partialDFA
                startStates.add(state);
                if (currentStates.isEmpty() || currentStates.stream().allMatch(s -> s.isStart())) {
                    this.currentStates.add(state);
                    List<String> startStatePartialDFANames = new ArrayList<>();
                    boolean startStatePartialDFAIsFinal = false;
                    for (State s: startStates) {
                        startStatePartialDFANames.add(s.getName());
                        startStatePartialDFAIsFinal |= s.isFinal();
                    }

                    this.partialDFA.addState(new State(String.join(", ", startStatePartialDFANames), true, startStatePartialDFAIsFinal));
                }
            } else {
                // TODO: if partialDFA is null, this could means
                // 1. I am a NFA without DFA; (extend this)
                // 2. I am a partial DFA. (Already done)
                if (isNFA) {
                    startStates.add(state);
                    if (currentStates.isEmpty() || currentStates.stream().allMatch(s -> s.isStart())) {
                        this.currentStates.add(state);
                    }
                } else {
                    if (!this.startStates.isEmpty()) {
                        this.states.remove(this.startStates.get(0));
                        this.startStates.clear();
                    }
    
                    this.startStates.add(state);
                    if (currentStates.isEmpty() || currentStates.get(0).isStart()) {
                        currentStates.clear();
                        currentStates.add(state);
                    }
                }
            }
        }

        if (state.isFinal()) {
            finalStates.add(state);
        }
    }

    public void next(char c) {
        if (this.currentStates.contains(State.errorState)) {
            return;
        }

        if (isNFA) {
            if (partialDFA == null) {
                // It has no partialDFA, use the default NFA algorithm
                List<State> nextStates = new ArrayList<>();
                for (State state: currentStates) {
                    nextStates.addAll(state.next(c));
                }
                this.currentStates.clear();
                this.currentStates.addAll(nextStates);
                if (this.currentStates.size() == 0) {
                    this.currentStates.add(State.errorState);
                }
            } else {
                // It has partialDFA, depend on the partialDFA
                if (!partialDFA.hasNext(c)) {
                    // Partial DFA has no next state for input c
                    State currentStatePartialDFA = partialDFA.getCurrentState().get(0);
                    String[] currentStateNFANames = currentStatePartialDFA.getName().split(", ");
                    List<String> nextStatePartialDFANames = new ArrayList<>();
                    Map<String, Boolean> nextStatePartialDFAStartFinal = new HashMap<>();
                    nextStatePartialDFAStartFinal.put("start", false);
                    nextStatePartialDFAStartFinal.put("final", false);

                    for (String currentStateNFAName: currentStateNFANames) {
                        State currentStateNFA = states.stream().filter(s -> s.getName().equals(currentStateNFAName)).findFirst().get();
                        currentStateNFA
                            .getTransitions()
                            .stream()
                            .filter(t -> t.getC() == c)
                            .forEach(t -> {
                                State nextState = t.getNext();
                                nextStatePartialDFANames.add(nextState.getName());
                                nextStatePartialDFAStartFinal.put("start", nextStatePartialDFAStartFinal.get("start") || nextState.isStart());
                                nextStatePartialDFAStartFinal.put("final", nextStatePartialDFAStartFinal.get("final") || nextState.isFinal());
                            });
                    }

                    String nextStatePartialDFAName = String.join(", ", nextStatePartialDFANames);
                    if (!nextStatePartialDFAName.equals("")) {
                        State nextStatePartialDFA = partialDFA.getState(nextStatePartialDFAName);
                        if (nextStatePartialDFA == null) {
                            nextStatePartialDFA = new State(nextStatePartialDFAName, nextStatePartialDFAStartFinal.get("start"), nextStatePartialDFAStartFinal.get("final"));
                            partialDFA.addState(nextStatePartialDFA);
                        }
                        currentStatePartialDFA.addTransistion(c, nextStatePartialDFA);
                    }
                }
                partialDFA.next(c);
                updateCurrentState();
            }
        } else {
            // This automaton is the partial DFA.
            List<State> nextStates = new ArrayList<>();
            for (State state: currentStates) {
                nextStates.addAll(state.next(c));
            }
            this.currentStates.clear();
            this.currentStates.addAll(nextStates);

            assert this.currentStates.size() <= 1;

            if (this.currentStates.size() == 0) {
                this.currentStates.add(State.errorState);
            }
        }
    }

    public boolean hasNext(char c) {
        for (State state: currentStates) {
            if (!state.next(c).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void updateCurrentState() {
        if (partialDFA != null) {
            // Update the current states if using partialDFA
            State currentStatePartialDFA = partialDFA.getCurrentState().get(0);
            String[] currentStateNFANames = currentStatePartialDFA.getName().split(", ");
            this.currentStates.clear();

            for (String currentStateNFAName: currentStateNFANames) {
                this.currentStates.add(states.stream().filter(s -> s.getName().equals(currentStateNFAName)).findFirst().orElseGet(() -> State.errorState));
            }
        }
    }

    public State getState(String name) {
        for (State state: this.states) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        return null;
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
                stringBuilder.append("    \"" + state.getName() + "\"\n");
            }
        }

        stringBuilder.append("""
            node [shape=circle];
        """);

        for (State state: states) {
            for (Transition transition: state.getTransitions()) {
                stringBuilder.append("    \"" + state.getName() + "\" -> \"" + transition.getNext().getName() + "\" [label=\"" + transition.getC() + "\"]\n");
            }
        }

        for (State state: currentStates) {
            stringBuilder.append("    \"" + state.getName() + "\" [style=filled, fillcolor=green]\n");
        }

        stringBuilder.append("""
            start -> \"0\";
        }
        """);

        return stringBuilder.toString();
    }

    public String partialToDOTString() {
        return partialDFA == null ? "" : partialDFA.toDOTString();
    }
}
