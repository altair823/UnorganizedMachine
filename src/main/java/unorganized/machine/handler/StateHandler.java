package unorganized.machine.handler;

import java.util.Collection;
import java.util.List;

/**
 * Handler interface that Handle current state of unit.
 * The type of classes that implement this interface varies according to the unit type.
 */
public interface StateHandler {
    /**
     * Calculating method for unit current state.
     * @param states previous state list
     * @return new current state
     */
    boolean calculate(Collection<Boolean> states);

    /**
     * Delivering method for edge class.
     * All edges can pass state to the head unit using the implementation of this function.
     * @param state state of tail unit
     * @return status to be delivered to the head unit
     */
    List<Boolean> deliver(boolean state);
}
