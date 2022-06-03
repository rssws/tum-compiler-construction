package exercise_5_1.compiler_construction.tum.zhongpin.wang.Automaton;

import java.util.ArrayList;
import java.util.List;

public class State {
    private String name;
    private List<Transition> transitions;
    private final boolean isStart;
    private final boolean isFinal;

    public static final State errorState = new State("error", false, true);

    public State(String name, boolean isStart, boolean isFinal) {
        this(name, new ArrayList<>(), isStart, isFinal);
    }

    public State(String name, List<Transition> transitions, boolean isStart, boolean isFinal) {
        this.name = name;
        this.transitions = transitions;
        this.isStart = isStart;
        this.isFinal = isFinal;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Transition> getTransitions() {
        return transitions;
    }
    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }
    public boolean isStart() {
        return isStart;
    }
    public boolean isFinal() {
        return isFinal;
    }
    public void addTransistion(char c, State next) {
        transitions.add(new Transition(c, next));
    }
    public List<State> next(char c) {
        List<Transition> filteredTransitions = transitions.stream().filter(transition -> transition.getC() == c).toList();
        List<State> next = new ArrayList<>();
        for (Transition transition: filteredTransitions) {
            next.add(transition.getNext());
        }
        return next;
    }
}
