package exercise_5_1.compiler_construction.tum.zhongpin.wang.Parser;

import java.time.LocalDate;
import java.util.List;

import exercise_5_1.compiler_construction.tum.zhongpin.wang.RegexTree.RegexTree;
import exercise_5_1.compiler_construction.tum.zhongpin.wang.RegexTree.RegexTreeHelper;

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
    private String originalInput;
    private String input;
    private char lookahead;
    private int curIndex;
    private RegexTree regexTree;

    /**
     * initialize and start parsing of s
     */
    public RegexTree parse(String s) throws RegexParserException {
        // initialize attributes
        originalInput = s;
        input = s + "$";
        lookahead = input.charAt(0);
        input = input.substring(1);
        curIndex = 0;
        regexTree = null;

        // start parsing with the start symbol
        regexTree = parseRegex();
        if (lookahead == '$') {
            System.out.println(s + " accepted!");
        } else {
            throwException(lookahead);
        }
        System.out.println("---------");
        return regexTree;
    }

    /* consumes next input character, and compares to c
       returns true if c equals next input character, false otherwise
       side-effect: shifts lookahead window one step forward
    */
    private void nextChar(char c) throws RegexParserException {
        // System.out.println(c);
        if (lookahead != c) {
            throwException(lookahead);
        };
        lookahead = input.charAt(0);
        input = input.substring(1);
        curIndex++;
    }

    /**
     * <regex>  ->  <concat> A1
     * @return
     * @throws RegexParserException
     */
    private RegexTree parseRegex() throws RegexParserException {
        RegexTree l = parseConcat();
        RegexTree r = parseA1();
        if (r != null) {
            return RegexTreeHelper.or(l, r);
        } else {
            return l;
        }
    }

    /**
     * A1       ->  '|' <regex>
     *           |  ε
     * @return
     * @throws RegexParserException
     */
    private RegexTree parseA1() throws RegexParserException {
        switch(lookahead) {
            case '|':
                nextChar('|');
                return parseRegex();
            case ')':
            case '$':
                return null;
            default:
                throwException(lookahead);
                return null;
        }
    }

    /**
     * <concat> ->  <rep> A2
     * @return
     * @throws RegexParserException
     */
    private RegexTree parseConcat() throws RegexParserException {
        RegexTree l = parseRep();
        RegexTree r = parseA2();
        if (r != null) {
            return RegexTreeHelper.concat(l, r);
        }
        return l;
    }

    /**
     * A2       ->  <concat>
     *           |  ε
     * @return
     * @throws RegexParserException
     */
    private RegexTree parseA2() throws RegexParserException {
        if (lookahead == '(' || lookahead >= 'a' && lookahead <= 'z' || lookahead >= 'A' && lookahead <= 'Z' || lookahead == 'ε') {
            return parseConcat();
        } else if (lookahead == ')' || lookahead == '|' || lookahead == '$') {
            return null;
        } else {
            throwException(lookahead);
            return null;
        }
    }

    /**
     * <rep>    ->  <atom> A3
     * @return
     * @throws RegexParserException
     */
    private RegexTree parseRep() throws RegexParserException {
        RegexTree regexTree = parseAtom();
        if (parseA3()) {
            return RegexTreeHelper.star(regexTree);
        }
        return regexTree;
    }

    /**
     * A3       ->  '*'
     *           |  'ε'
     * @return
     * @throws RegexParserException
     */
    private boolean parseA3() throws RegexParserException {
        if (lookahead == '*') {
            nextChar('*');
            return true;
        } else if (lookahead == '(' || lookahead >= 'a' && lookahead <= 'z' || lookahead >= 'A' && lookahead <= 'Z' || lookahead == 'ε' || lookahead == ')' || lookahead == '|' || lookahead == '$') {
            return false;
        } else {
            throwException(lookahead);
            return false;
        }
    }

    /**
     * <atom>   ->  '(' <regex> ')'
     *           |  ['a'...'z', 'ε']
     * @return
     * @throws RegexParserException
     */
    private RegexTree parseAtom() throws RegexParserException {
        if (lookahead == '(') {
            nextChar('(');
            RegexTree regexTree = parseRegex();
            nextChar(')');
            return regexTree;
        } else if (lookahead >= 'a' && lookahead <= 'z' || lookahead >= 'A' && lookahead <= 'Z') {
            char curLookahead = lookahead;
            nextChar(lookahead);
            return RegexTreeHelper.letter(curLookahead);
        } else if (lookahead == 'ε') {
            nextChar(lookahead);
            return RegexTreeHelper.epsilon();
        } else {
            throwException(lookahead);
            return null;
        }
    }

    private void throwException(char lookahead) throws RegexParserException {
        StringBuilder msg = new StringBuilder();
        if (lookahead == '$') {
            msg.append("Unexpected end of input.\n");
        } else {
            msg.append("Unexpected character '" + lookahead + "' at index " + curIndex + " received.\n");
        }
        msg.append(originalInput + "\n");
        for (int i = 0; i < curIndex; i++) {
            msg.append(" ");
        }
        msg.append("^\n");
        throw new RegexParserException(msg.toString());
    }
}
