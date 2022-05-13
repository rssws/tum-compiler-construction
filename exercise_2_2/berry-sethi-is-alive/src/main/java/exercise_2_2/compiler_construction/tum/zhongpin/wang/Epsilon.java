package exercise_2_2.compiler_construction.tum.zhongpin.wang;

public class Epsilon extends RegexTree {
    public Epsilon() {
        super();
        this.empty = true;
    }

    @Override
    public void accept(RegexTreeEmptyVisitor regexTreeEmptyVisitor) {
        regexTreeEmptyVisitor.visit(this);
    }

    @Override
    public void accept(RegexTreeFirstVisitor regexTreeFirstVisitor) {
        regexTreeFirstVisitor.visit(this);
    }

    @Override
    public void accept(RegexTreeNextVisitor regexTreeNextVisitor) {
        regexTreeNextVisitor.visit(this);
    }

    @Override
    public void accept(RegexTreeLastVisitor regexTreeLastVisitor) {
        regexTreeLastVisitor.visit(this);
    }
}
