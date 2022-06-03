package exercise_5_1.compiler_construction.tum.zhongpin.wang.RegexTree;

public class RegexTreeHelper {
    public static Epsilon epsilon() {
        return new Epsilon();
    }
    
    public static Letter letter(char letter) {
        return new Letter(letter);
    }

    public static Concat concat(RegexTree l, RegexTree r) {
        return new Concat(l, r);
    }

    public static Or or(RegexTree l, RegexTree r) {
        return new Or(l, r);
    }

    public static Star star(RegexTree child) {
        return new Star(child);
    }
}
