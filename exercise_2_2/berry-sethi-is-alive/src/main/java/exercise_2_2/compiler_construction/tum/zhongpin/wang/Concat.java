package exercise_2_2.compiler_construction.tum.zhongpin.wang;

public class Concat extends RegexTree {
    private RegexTree left;
    private RegexTree right;

    public Concat(RegexTree left, RegexTree right) {
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
    public void accept(RegexTreeEmptyFirstLastVisitor regexTreeEmptyFirstLastVisitor) {
        regexTreeEmptyFirstLastVisitor.visit(this);
    }

    @Override
    public void accept(RegexTreeNextVisitor regexTreeNextVisitor) {
        regexTreeNextVisitor.visit(this);
    }
}
