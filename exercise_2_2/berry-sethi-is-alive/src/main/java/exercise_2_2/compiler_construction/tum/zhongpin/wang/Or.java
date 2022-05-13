package exercise_2_2.compiler_construction.tum.zhongpin.wang;

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
