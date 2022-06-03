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
        if (lookahead == '$' || parse_S()) {
            System.out.println(s + " accepted!");
        } else {
            System.out.println(s + " rejected!");
        }
        System.out.println("---------");
    }

    /* consumes next input character, and compares to c
       returns true if c equals next input character, false otherwise
       side-effect: shifts lookahead window one step forward
    */
    private boolean nextChar(char c) {
        // System.out.println(c);
        if (lookahead != c) return false;
        lookahead = input.charAt(0);
        input = input.substring(1);
        return true;
    }

    /* S -> As
        returns true if parsing according to rule S -> As succeeds
        returns false otherwise
    */
    private boolean parse_S() {
        return parse_A() && nextChar('s');
    }

    /* A -> aA | 𝝐
        returns true if parsing according to rule A -> aA | 𝝐 succeeds
        returns false otherwise
    */
    private boolean parse_A() {
        switch(lookahead) {
            case 'a':
                return nextChar('a') && parse_A();
            case 's':
                return true;
            default:
                if (lookahead == '$') {
                    System.out.println("Expect the next char to be 'a' or 's', but reached the end of the input.");
                } else {
                    System.out.println("Expect the next char to be 'a' or 's', but received '" + lookahead + "' instead.");
                }
                return false;
        }
    }
}
