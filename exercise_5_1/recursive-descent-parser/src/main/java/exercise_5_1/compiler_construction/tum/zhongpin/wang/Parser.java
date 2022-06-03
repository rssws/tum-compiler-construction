package exercise_5_1.compiler_construction.tum.zhongpin.wang;

import java.time.LocalDate;
import java.util.List;

/**
 * Regex recursive descent parser with LL(1)-grammar.
 * 
 * [delta_1: Arithmetic Expression Grammar]
 * <regex>  ->  <concat> '|' <regex>
 *           |  <concat> 
 * <concat> ->  <rep> <concat>
 *           |  <rep>
 * <rep>    ->  <atom> '*'
 *           |  <atom>
 * <atom>   ->  '(' <regex> ')'
 *           |  ['a'...'z', 'ε']
 * 
 * [delta_2: Eliminate intersection of the first sets]
 * <regex>  ->  <concat> A1
 * A1       ->  '|' <regex>
 *           |  'ε'
 * <concat> ->  <rep> A2
 * A2       ->  <concat>
 *           |  'ε'
 * <rep>    ->  <atom> A3
 * A3       ->  '*'
 *           |  'ε'
 * <atom>   ->  '(' <regex> ')'
 *           |  ['a'...'z', 'ε']
 */
public class Parser {
    private String input;
    private char lookahead;
    private int curIndex;

    /**
     * initialize and start parsing of s
     */
    public void parse(String s) {
        // initialize attributes
        input = s + "$";
        lookahead = input.charAt(0);
        input = input.substring(1);
        curIndex = 0;

        // start parsing with the start symbol if input is not empty
        if (parseRegex()) {
            if (lookahead == '$') {
                System.out.println(s + " accepted!");
            } else {
                printException(lookahead);
                System.out.println(s);
                for (int i = 0; i < curIndex; i++) {
                    System.out.print(' ');
                }
                System.out.println('^');
            }
        } else {
            // reason has been printed during the parsing
            System.out.println(s);
            for (int i = 0; i < curIndex; i++) {
                System.out.print(' ');
            }
            System.out.println('^');
        }
        System.out.println("---------");
    }

    /* consumes next input character, and compares to c
       returns true if c equals next input character, false otherwise
       side-effect: shifts lookahead window one step forward
    */
    private boolean nextChar(char c) {
        // System.out.println(c);

        if (lookahead != c) {
            printException(lookahead);
            return false;
        };
        lookahead = input.charAt(0);
        input = input.substring(1);
        curIndex++;
        return true;
    }

    /**
     * <regex>  ->  <concat> A1
     * @return
     */
    private boolean parseRegex() {
        return parseConcat() && parseA1();
    }

    /**
     * A1       ->  '|' <regex>
     *           |  epsilon
     * @return
     */
    private boolean parseA1() {
        switch(lookahead) {
            case '|':
                return nextChar('|') && parseRegex();
            case ')':
            case '$':
                return true;
            default:
                printException(lookahead);
                return false;
        }
    }

    /**
     * <concat> ->  <rep> A2
     * @return
     */
    private boolean parseConcat() {
        return parseRep() && parseA2();
    }

    /**
     * A2       ->  <concat>
     *           |  ε
     * @return
     */
    private boolean parseA2() {
        if (lookahead == '(' || lookahead >= 'a' && lookahead <= 'z' || lookahead >= 'A' && lookahead <= 'Z' || lookahead == 'ε') {
            return parseConcat();
        } else if (lookahead == ')' || lookahead == '|' || lookahead == '$') {
            return true;
        } else {
            printException(lookahead);
            return false;
        }
    }

    /**
     * <rep>    ->  <atom> A3
     * @return
     */
    private boolean parseRep() {
        return parseAtom() && parseA3();
    }

    /**
     * A3       ->  '*'
     *           |  'ε'
     * @return
     */
    private boolean parseA3() {
        if (lookahead == '*') {
            return nextChar('*');
        } else if (lookahead == '(' || lookahead >= 'a' && lookahead <= 'z' || lookahead >= 'A' && lookahead <= 'Z' || lookahead == 'ε' || lookahead == ')' || lookahead == '|' || lookahead == '$') {
            return true;
        } else {
            printException(lookahead);
            return false;
        }
    }

    /**
     * <atom>   ->  '(' <regex> ')'
     *           |  ['a'...'z', 'ε']
     * @return
     */
    private boolean parseAtom() {
        if (lookahead == '(') {
            return nextChar('(') && parseRegex() && nextChar(')');
        } else if (lookahead >= 'a' && lookahead <= 'z' || lookahead >= 'A' && lookahead <= 'Z' || lookahead == 'ε') {
            return nextChar(lookahead);
        } else {
            printException(lookahead);
            return false;
        }
    }

    private void printException(char lookahead) {
        if (lookahead == '$') {
            System.out.println("Unexpected end of input.");
        } else {
            System.out.println("Unexpected character '" + lookahead + "' at index " + curIndex + " received.");
        }
    }
}
