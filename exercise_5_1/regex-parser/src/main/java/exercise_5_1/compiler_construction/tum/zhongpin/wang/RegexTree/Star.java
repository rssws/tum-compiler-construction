package exercise_5_1.compiler_construction.tum.zhongpin.wang.RegexTree;

import java.util.ArrayList;
import java.util.List;

public class Star extends RegexTree {
    private RegexTree child;

    public Star(RegexTree child) {
        super();
        this.child = child;
    }

    public RegexTree getChild() {
        return child;
    }

    public void setChild(RegexTree child) {
        this.child = child;
    }

    @Override
    public List<RegexTree> getChildren() {
        return new ArrayList<RegexTree>() {{
            add(child);
        }};
    }

    @Override
    public void accept(RegexTreeEmptyFirstLastVisitor regexTreeEmptyFirstLastVisitor) {
        regexTreeEmptyFirstLastVisitor.visit(this);
    }

    @Override
    public void accept(RegexTreeNextVisitor regexTreeNextVisitor) {
        regexTreeNextVisitor.visit(this);
    }
}
