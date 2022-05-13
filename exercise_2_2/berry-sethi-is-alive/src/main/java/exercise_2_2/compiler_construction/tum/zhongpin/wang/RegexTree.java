package exercise_2_2.compiler_construction.tum.zhongpin.wang;

import java.util.ArrayList;
import java.util.List;

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
}
