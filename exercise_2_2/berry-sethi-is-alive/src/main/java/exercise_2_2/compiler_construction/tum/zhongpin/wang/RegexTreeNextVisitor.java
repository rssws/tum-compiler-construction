package exercise_2_2.compiler_construction.tum.zhongpin.wang;

import org.apache.commons.collections4.ListUtils;

/**
 * DFS pre-order traversal
 */
public class RegexTreeNextVisitor implements RegexTreeVisitor {
    @Override
    public void visit(Concat concat) {
        RegexTree left = concat.getLeft();
        RegexTree right = concat.getRight();

        left.setNext(
            right.getEmpty()
                ? ListUtils.union(right.getFirst(), concat.getNext())
                : right.getFirst() 
        );
        right.setNext(concat.getNext());

        left.accept(RegexTree.regexTreeNextVisitor);
        right.accept(RegexTree.regexTreeNextVisitor);
    }

    @Override
    public void visit(Epsilon epsilon) {
        return;
    }

    @Override
    public void visit(Letter letter) {
        return;
    }

    @Override
    public void visit(Or or) {
        RegexTree left = or.getLeft();
        RegexTree right = or.getRight();

        left.setNext(or.getNext());
        right.setNext(or.getNext());

        left.accept(RegexTree.regexTreeNextVisitor);
        right.accept(RegexTree.regexTreeNextVisitor);
    }

    @Override
    public void visit(Star star) {
        RegexTree child = star.getChild();

        child.setNext(ListUtils.union(child.getFirst(), star.getNext()));

        child.accept(RegexTree.regexTreeNextVisitor);
    }
}
