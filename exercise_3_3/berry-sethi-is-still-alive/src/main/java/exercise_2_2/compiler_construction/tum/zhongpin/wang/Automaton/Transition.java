package exercise_2_2.compiler_construction.tum.zhongpin.wang.Automaton;

public class Transition {
    private char c;
    private State next;
    
    public Transition(char c, State next) {
        this.c = c;
        this.next = next;
    }

    public char getC() {
        return c;
    }
    public void setC(char c) {
        this.c = c;
    }
    public State getNext() {
        return next;
    }
    public void setNext(State next) {
        this.next = next;
    }
}
