package exercise_2_2.compiler_construction.tum.zhongpin.wang.RegexTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableInt;

import exercise_2_2.compiler_construction.tum.zhongpin.wang.Automaton.Automaton;
import exercise_2_2.compiler_construction.tum.zhongpin.wang.Automaton.State;

public abstract class RegexTree {
    private boolean empty;
    private List<RegexTree> first = new ArrayList<>();
    private List<RegexTree> next = new ArrayList<>();
    private List<RegexTree> last = new ArrayList<>();

    protected static RegexTreeEmptyFirstLastVisitor regexTreeEmptyFirstLastVisitor = new RegexTreeEmptyFirstLastVisitor();
    protected static RegexTreeNextVisitor regexTreeNextVisitor = new RegexTreeNextVisitor();

    public RegexTree() {}

    public abstract void accept(RegexTreeEmptyFirstLastVisitor regexTreeEmptyVisitor);
    public abstract void accept(RegexTreeNextVisitor regexTreeNextVisitor);
    public abstract List<RegexTree> getChildren();

    public boolean getEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public List<RegexTree> getFirst() {
        return first;
    }

    public void setFirst(List<RegexTree> first) {
        this.first = first;
    }

    public List<RegexTree> getNext() {
        return next;
    }

    public void setNext(List<RegexTree> next) {
        this.next = next;
    }

    public List<RegexTree> getLast() {
        return last;
    }

    public void setLast(List<RegexTree> last) {
        this.last = last;
    }

    public Automaton transformToAutomaton() {
        this.accept(regexTreeEmptyFirstLastVisitor);
        this.accept(regexTreeNextVisitor);

        Automaton automaton = new Automaton();
        Map<RegexTree, State> regexTreeStateMap = new HashMap<>();

        // state 0 must be start state, could be final state depending on empty attribute of root
        State state0 = new State("0", true, this.empty);
        regexTreeStateMap.put(new Epsilon(), state0);
        
        automaton.addState(state0);

        MutableInt curIdx = new MutableInt(1);

        convertStates(this, this, regexTreeStateMap, automaton, curIdx);
        convertTransitions(this, regexTreeStateMap);

        automaton.finalize();

        return automaton;
    }

    public void convertStates(RegexTree root, RegexTree curRegexTree, Map<RegexTree, State> regexTreeStateMap, Automaton automaton, MutableInt curIdx) {
        if (curRegexTree instanceof Letter) {
            State curState = new State(String.valueOf(curIdx), false, root.last.contains(curRegexTree));
            curIdx.increment();
            automaton.addState(curState);
            regexTreeStateMap.put(curRegexTree, curState);
        }

        for (RegexTree child: curRegexTree.getChildren()) {
            convertStates(root, child, regexTreeStateMap, automaton, curIdx);
        }
    }

    public void convertTransitions(RegexTree root, Map<RegexTree, State> regexTreeStateMap) {
        for (var entry: regexTreeStateMap.entrySet()) {
            RegexTree curRegexTree = entry.getKey();
            State curState = entry.getValue();

            if (curRegexTree instanceof Letter) {
                // leaves
                for (RegexTree nextRegexTree: curRegexTree.getNext()) {
                    curState.addTransistion(((Letter) nextRegexTree).getLetter(), regexTreeStateMap.get(nextRegexTree));
                }
            }

            if (curState.isStart()) {
                for (RegexTree firstRegexTree: root.getFirst()) {
                    curState.addTransistion(((Letter) firstRegexTree).getLetter(), regexTreeStateMap.get(firstRegexTree));
                }
            }
        }
    }
}
