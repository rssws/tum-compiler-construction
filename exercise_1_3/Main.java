package exercise_1_3;

import java.io.IOException;

public class Main {
     public static void main(String[] args) {
        Automaton automaton = new Automaton();

        State state0 = new State("0", true, false);
        State state1 = new State("1", false, false);
        State state2 = new State("2", false, true);
        State state3 = new State("3", false, false);

        state0.addTransistion('a', state1);
        state0.addTransistion('b', state2);

        state1.addTransistion('d', state2);
        state1.addTransistion('c', state3);

        state2.addTransistion('f', state3);

        state3.addTransistion('e', state1);

        automaton.addState(state0);
        automaton.addState(state1);
        automaton.addState(state2);
        automaton.addState(state3);

        automaton.finalize();

        while (true) {
            try {
                char c = (char) System.in.read();
                if (c < 32 || c > 126) continue;
                automaton.next(c);
                System.out.print("State: " + automaton.getCurrentState().getName());
                System.out.print("\t| ");
                System.out.print("Finished: " + automaton.isFinished());
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
