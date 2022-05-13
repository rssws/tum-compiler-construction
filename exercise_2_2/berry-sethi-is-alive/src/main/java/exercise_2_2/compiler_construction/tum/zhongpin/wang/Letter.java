package exercise_2_2.compiler_construction.tum.zhongpin.wang;

public class Letter extends RegexTree {
    private final char letter;

    public Letter(char letter) {
        super();
        this.letter = letter;
        this.empty = false;
    }

    public char getLetter() {
        return letter;
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
