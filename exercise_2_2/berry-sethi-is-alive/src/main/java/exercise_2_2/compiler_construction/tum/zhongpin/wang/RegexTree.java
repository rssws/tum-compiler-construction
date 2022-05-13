package exercise_2_2.compiler_construction.tum.zhongpin.wang;

import java.util.ArrayList;
import java.util.List;

public abstract class RegexTree {
    protected boolean empty;
    protected List<RegexTree> first = new ArrayList<>();
    protected List<RegexTree> next = new ArrayList<>();
    protected List<RegexTree> last = new ArrayList<>();

    public RegexTree() {}

    public abstract void accept(RegexTreeEmptyVisitor regexTreeEmptyVisitor);
    public abstract void accept(RegexTreeFirstVisitor regexTreeFirstVisitor);
    public abstract void accept(RegexTreeNextVisitor regexTreeNextVisitor);
    public abstract void accept(RegexTreeLastVisitor regexTreeLastVisitor);
}
