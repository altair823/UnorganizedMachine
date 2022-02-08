package unorganized.machine.units;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import unorganized.machine.calculator.StateCalculator;
import unorganized.machine.edges.Edge;

import java.util.LinkedList;
import java.util.List;


/**
 * Unit class that makes up Turing's unorganized machine. Unit object serve of role a vertex in graph.
 * These classes communicate with each other through the Edge class,
 * but does not store Edge's data or unit connection data.
 * Those data are stored in Edge class objects.
 * Actual unit class of use case has to be inherited this abstract Unit class.
 * @author altair823
 * @see Edge
 */
@Getter
@AllArgsConstructor
@Builder
public class Unit {

    /**
     * Unit ID that distinguishes it from other units.
     */
    protected long id;

    /**
     * Current state of unit. A unit must have only one state.
     */
    @Setter
    protected boolean currentState;

    /**
     * List that holds the state of previous units.
     */
    private List<Boolean> previousStates;

    /**
     * Calculator for current unit state.
     */
    private final StateCalculator stateCalculator;

    /**
     * The function that receives a pulse from the control and updates the current state.
     */
    public void calculateState(){
        if (stateCalculator != null && this.previousStates != null) {
            this.currentState = stateCalculator.calculate(previousStates);
            this.previousStates = null;
        }
    }

    /**
     * Add collection of state from previous units.
     * @param states collection of states
     */
    public void addPreviousStates(boolean states){
        if (this.previousStates != null) {
            this.previousStates.add(states);
        }
        else {
            this.previousStates = new LinkedList<>(List.of(states));
        }
    }

    @Override
    public String toString(){
        return "Unit ID: " + this.id + "\n"
                + "state: " + this.currentState + "\n"
                + "state calculator: " + this.stateCalculator.getClass().getSimpleName() + "\n";
    }

    /**
     * Copy factory method that copy the original unit object deeply.
     * @param originalUnit original unit object
     * @return new copied unit object
     */
    public static Unit copy(Unit originalUnit){
        return Unit.builder()
                .id(originalUnit.getId())
                .currentState(originalUnit.isCurrentState())
                .stateCalculator(originalUnit.stateCalculator)
                .build();
    }
}

