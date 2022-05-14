package exercise_2_2.compiler_construction.tum.zhongpin.wang;

import exercise_2_2.compiler_construction.tum.zhongpin.wang.Automaton.Automaton;
import exercise_2_2.compiler_construction.tum.zhongpin.wang.RegexTree.Concat;
import exercise_2_2.compiler_construction.tum.zhongpin.wang.RegexTree.Epsilon;
import exercise_2_2.compiler_construction.tum.zhongpin.wang.RegexTree.Letter;
import exercise_2_2.compiler_construction.tum.zhongpin.wang.RegexTree.Or;
import exercise_2_2.compiler_construction.tum.zhongpin.wang.RegexTree.RegexTree;
import exercise_2_2.compiler_construction.tum.zhongpin.wang.RegexTree.Star;

public class App {
    public static void main(String[] args) {
        System.out.println("Constructing RegexTree ...");
        RegexTree regexTree = concat(
            star(
                or(
                    letter('a'), 
                    letter('b')
                )
            ),
            concat(
                letter('a'),
                or(
                    letter('a'), 
                    letter('b')
                )
            )
        );

        System.out.println("Transforming RegexTree to NFA ...");
        Automaton automaton = regexTree.transformToAutomaton();

        System.out.println("Printing DOT string...");
        System.out.println("\n------------");
        System.out.println(automaton.toDOTString());
    }

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
