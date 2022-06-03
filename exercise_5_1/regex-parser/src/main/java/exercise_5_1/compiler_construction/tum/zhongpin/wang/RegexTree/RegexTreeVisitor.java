package exercise_5_1.compiler_construction.tum.zhongpin.wang.RegexTree;

public interface RegexTreeVisitor {
    public void visit(Concat concat);
    public void visit(Epsilon epsilon);
    public void visit(Letter letter);
    public void visit(Or or);
    public void visit(Star star);
}
