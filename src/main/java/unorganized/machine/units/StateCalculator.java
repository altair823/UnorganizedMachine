package unorganized.machine.units;

import java.util.Collection;

/**
 * Calculator interface that calculate current state of unit using states of previous units.
 */
public interface StateCalculator {
    /**
     * Calculating method for unit current state.
     *
     * @param states previous state list
     * @return new current state
     */
    boolean calculate(Collection<Boolean> states);
}
