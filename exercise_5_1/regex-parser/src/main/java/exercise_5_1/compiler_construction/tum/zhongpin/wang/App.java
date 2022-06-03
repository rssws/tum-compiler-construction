package exercise_5_1.compiler_construction.tum.zhongpin.wang;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import exercise_5_1.compiler_construction.tum.zhongpin.wang.Automaton.Automaton;
import exercise_5_1.compiler_construction.tum.zhongpin.wang.Automaton.State;
import exercise_5_1.compiler_construction.tum.zhongpin.wang.Parser.Parser;
import exercise_5_1.compiler_construction.tum.zhongpin.wang.Parser.RegexParserException;
import exercise_5_1.compiler_construction.tum.zhongpin.wang.RegexTree.RegexTree;

public class App {
    public static void main(String[] args) { 
        System.out.println("Constructing RegexTree ...");
        RegexTree regexTree;
        try {
            //regexTree = (new Parser()).parse("(a|b)*a(a|b)");
            regexTree = (new Parser()).parse("((a|bb*c|bc)*)c|d*(a*|aab*)");
        } catch (RegexParserException e) {
            System.out.println(e.getMessage());
            return;
        }

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
}
