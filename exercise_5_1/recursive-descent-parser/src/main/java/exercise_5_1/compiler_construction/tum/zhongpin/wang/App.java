package exercise_5_1.compiler_construction.tum.zhongpin.wang;

public class App {
    public static void main(String[] args) {
        // test the implementation
        Parser p = new Parser();
        p.parse("");
        p.parse("a");
        p.parse("((a))");
        p.parse("((a)");
        p.parse("a)))");
        p.parse("a|b*Az*");
        p.parse("(a|b)*a(a|b)");
    }
}
