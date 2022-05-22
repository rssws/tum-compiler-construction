package exercise_3_3.compiler_construction.tum.zhongpin.wang.RegexTree;

import java.util.ArrayList;
import java.util.List;

public class Epsilon extends RegexTree {
    public Epsilon() {
        super();
    }

    @Override
    public void accept(RegexTreeEmptyFirstLastVisitor regexTreeEmptyFirstLastVisitor) {
        regexTreeEmptyFirstLastVisitor.visit(this);
    }

    @Override
    public void accept(RegexTreeNextVisitor regexTreeNextVisitor) {
        regexTreeNextVisitor.visit(this);
    }

    @Override
    public List<RegexTree> getChildren() {
        return new ArrayList<>();
    }
}
