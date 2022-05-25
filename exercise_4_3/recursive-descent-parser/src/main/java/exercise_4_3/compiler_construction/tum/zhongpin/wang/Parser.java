package exercise_4_3.compiler_construction.tum.zhongpin.wang;

/*
  recursive descent parser for the LL(1)-grammar
  S -> As
  A -> aA | 𝝐
*/
public class Parser {
    private String input;
    private char lookahead;

    /*
    initialize and start parsing of s
    */
    public void parse(String s) {
        // initialize attributes
        input = s + "$";
        lookahead = input.charAt(0);
        input = input.substring(1);

        // start parsing with the start symbol if input is not empty

        // TODO

    }

    /* consumes next input character, and compares to c
       returns true if c equals next input character, false otherwise
       side-effect: shifts lookahead window one step forward
    */
    private boolean nextChar(char c) {
        // TODO
        return false;
    }

    /* S -> As
        returns true if parsing according to rule S -> As succeeds
        returns false otherwise
    */
    private boolean parse_S() {
        // TODO
        return false;
    }

    /* A -> aA | 𝝐
        returns true if parsing according to rule A -> aA | 𝝐 succeeds
        returns false otherwise
    */
    private boolean parse_A() {
        //TODO
        return false;
    }
}
