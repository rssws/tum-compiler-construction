package exercise_5_1.compiler_construction.tum.zhongpin.wang.RegexTree;

import java.util.ArrayList;
import java.util.List;

public class Letter extends RegexTree {
    private final char letter;

    public Letter(char letter) {
        super();
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
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
