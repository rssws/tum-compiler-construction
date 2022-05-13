package exercise_2_2.compiler_construction.tum.zhongpin.wang;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    public Epsilon epsilon() {
        return new Epsilon();
    }
    
    public Letter letter(char letter) {
        return new Letter(letter);
    }

    public Concat concat(RegexTree l, RegexTree r) {
        return new Concat(l, r);
    }

    public Or or(RegexTree l, RegexTree r) {
        return new Or(l, r);
    }

    public Star star(RegexTree child) {
        return new Star(child);
    }
}
