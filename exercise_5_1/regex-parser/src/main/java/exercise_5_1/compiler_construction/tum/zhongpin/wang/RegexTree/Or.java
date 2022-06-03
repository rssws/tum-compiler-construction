package exercise_5_1.compiler_construction.tum.zhongpin.wang.RegexTree;

import java.util.ArrayList;
import java.util.List;

public class Or extends RegexTree {
    private RegexTree left;
    private RegexTree right;

    public Or(RegexTree left, RegexTree right) {
        super();
        this.left = left;
        this.right = right;
    }

    public RegexTree getLeft() {
        return left;
    }

    public void setLeft(RegexTree left) {
        this.left = left;
    }

    public RegexTree getRight() {
        return right;
    }

    public void setRight(RegexTree right) {
        this.right = right;
    }

    @Override
    public List<RegexTree> getChildren() {
        return new ArrayList<RegexTree>() {{
            add(left);
            add(right);
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
