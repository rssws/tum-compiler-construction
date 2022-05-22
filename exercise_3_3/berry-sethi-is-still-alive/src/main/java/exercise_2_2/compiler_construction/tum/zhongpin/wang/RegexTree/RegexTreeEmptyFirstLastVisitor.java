package exercise_2_2.compiler_construction.tum.zhongpin.wang.RegexTree;

import org.apache.commons.collections4.ListUtils;

/**
 * DFS post-order traversal
 */
public class RegexTreeEmptyFirstLastVisitor implements RegexTreeVisitor {
    @Override
    public void visit(Concat concat) {
        RegexTree left = concat.getLeft();
        RegexTree right = concat.getRight();

        left.accept(RegexTree.regexTreeEmptyFirstLastVisitor);
        right.accept(RegexTree.regexTreeEmptyFirstLastVisitor);

        concat.setEmpty(left.getEmpty() && right.getEmpty());
        concat.setFirst(
            left.getEmpty() 
                ? ListUtils.union(left.getFirst(), right.getFirst())
                : left.getFirst()
        );
        concat.setLast(
            right.getEmpty() 
                ? ListUtils.union(left.getLast(), right.getLast())
                : right.getLast()
        );
    }

    @Override
    public void visit(Epsilon epsilon) {
        epsilon.setEmpty(true);
        epsilon.getFirst().add(epsilon);
        epsilon.getLast().add(epsilon);
    }

    @Override
    public void visit(Letter letter) {
        letter.setEmpty(false);
        letter.getFirst().add(letter);
        letter.getLast().add(letter);
    }

    @Override
    public void visit(Or or) {
        RegexTree left = or.getLeft();
        RegexTree right = or.getRight();

        left.accept(RegexTree.regexTreeEmptyFirstLastVisitor);
        right.accept(RegexTree.regexTreeEmptyFirstLastVisitor);

        or.setEmpty(left.getEmpty() || right.getEmpty());
        or.setFirst(ListUtils.union(left.getFirst(), right.getFirst()));
        or.setLast(ListUtils.union(left.getLast(), right.getLast()));
    }

    @Override
    public void visit(Star star) {
        RegexTree child = star.getChild();

        child.accept(RegexTree.regexTreeEmptyFirstLastVisitor);

        star.setEmpty(true);
        star.setFirst(star.getChild().getFirst());
        star.setLast(star.getChild().getLast());
    }
}
