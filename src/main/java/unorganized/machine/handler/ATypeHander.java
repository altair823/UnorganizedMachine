package unorganized.machine.handler;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * State calculator for unorganized machine units.
 * Generally used in type A units described in Turing's paper(Intelligent Machinery, 1948).
 */
public class ATypeHander implements StateHandler {
    @Override
    public boolean calculate(Collection<Boolean> states) {
        if (states instanceof List<Boolean> stateList) {
            // Calculate new state with NAND gate.
            return !(stateList.get(0) && stateList.get(1));
        }
        else {
            throw new ClassCastException();
        }
    }

    @Override
    public List<Boolean> deliver(boolean state) {
        return new LinkedList<>(List.of(state));
    }
}
