package exercise_2_2.compiler_construction.tum.zhongpin.wang;

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
