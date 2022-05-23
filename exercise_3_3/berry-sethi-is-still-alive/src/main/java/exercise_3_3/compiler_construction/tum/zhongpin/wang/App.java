package exercise_3_3.compiler_construction.tum.zhongpin.wang;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import exercise_3_3.compiler_construction.tum.zhongpin.wang.Automaton.Automaton;
import exercise_3_3.compiler_construction.tum.zhongpin.wang.Automaton.State;
import exercise_3_3.compiler_construction.tum.zhongpin.wang.RegexTree.Concat;
import exercise_3_3.compiler_construction.tum.zhongpin.wang.RegexTree.Epsilon;
import exercise_3_3.compiler_construction.tum.zhongpin.wang.RegexTree.Letter;
import exercise_3_3.compiler_construction.tum.zhongpin.wang.RegexTree.Or;
import exercise_3_3.compiler_construction.tum.zhongpin.wang.RegexTree.RegexTree;
import exercise_3_3.compiler_construction.tum.zhongpin.wang.RegexTree.Star;

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

        // regexTree = letter('a');

        // regexTree = concat(
        //     letter('a'),
        //     concat(
        //         letter('b'), 
        //         concat(
        //             letter('c'),
        //             or(
        //                 star(
        //                     concat(
        //                         letter('a'),
        //                         concat(
        //                             letter('b'), 
        //                             letter('c')
        //                         )
        //                     )
        //                 ), 
        //                 letter('a')
        //             )
        //         )
        //     )
        // );

        boolean usePartialDFA = true;

        System.out.println("Transforming RegexTree to NFA ...");
        Automaton automaton = regexTree.transformToAutomaton(usePartialDFA);

        System.out.println("---------");
        System.out.println(automaton.toDOTString());
        if (usePartialDFA) {
            System.out.println("---------");
            System.out.println(automaton.partialToDOTString());
        }

        writeStringToFile(automaton.toDOTString(), "nfa.gv");
        convertAndShowPDF("nfa.gv");
        if (usePartialDFA) {
            writeStringToFile(automaton.partialToDOTString(), "partial_dfa.gv");
            convertAndShowPDF("partial_dfa.gv");
        }

        while (true) {
            try {
                char c = (char) System.in.read();
                if (c < 32 || c > 126) continue;
                automaton.next(c);
                
                System.out.print("State: ");
                for (State state: automaton.getCurrentState()) {
                    System.out.print(state.getName() + " ");
                }
                System.out.print("\t| ");
                System.out.print("Finished: " + automaton.isFinished());
                System.out.println();
                
                writeStringToFile(automaton.toDOTString(), "nfa.gv");
                convertAndShowPDF("nfa.gv");
                if (usePartialDFA) {
                    writeStringToFile(automaton.partialToDOTString(), "partial_dfa.gv");
                    convertAndShowPDF("partial_dfa.gv");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeStringToFile(String s, String filename) {
        // System.out.println("Writing string to file ...");
        PrintWriter writer;
        try {
            writer = new PrintWriter(filename, "UTF-8");
            writer.println(s);
            writer.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
    }

    public static void convertAndShowPDF(String filename) {
        // System.out.println("Converting DOT file to pdf ...");
        Runtime rt = Runtime.getRuntime();
        try {
            Process dot = rt.exec("dot -Tpdf " + filename + " -O");
            dot.waitFor();
            Process open = rt.exec("open -a Skim.app " + filename + ".pdf");
            open.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
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
