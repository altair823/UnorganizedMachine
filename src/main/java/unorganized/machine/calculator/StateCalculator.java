package unorganized.machine.calculator;

import java.util.Collection;

/**
 * Handler interface that Handle current state of unit.
 * The type of classes that implement this interface varies according to the unit type.
 */
public interface StateCalculator {
    /**
     * Calculating method for unit current state.
     * @param states previous state list
     * @return new current state
     */
    boolean calculate(Collection<Boolean> states);
}
