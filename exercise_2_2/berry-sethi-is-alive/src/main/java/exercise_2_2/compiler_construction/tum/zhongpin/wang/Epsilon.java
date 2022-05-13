package exercise_2_2.compiler_construction.tum.zhongpin.wang;

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
}
