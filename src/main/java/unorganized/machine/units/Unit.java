package unorganized.machine.units;

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
public class Unit {

    /**
     * Unit ID that distinguishes it from other units.
     */
    protected long id;

    /**
     * Current state of unit. A unit must have only one state.
     */
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
     * Constructor that create a new Unit object.
     * @param id unit ID
     * @param state initial unit state
     * @param stateCalculator State calculator object
     */
    Unit(long id, boolean state, StateCalculator stateCalculator) {
        this.id = id;
        this.currentState = state;
        this.stateCalculator = stateCalculator;
    }

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
     * Getter for unit ID.
     * @return unit ID
     */
    public long getId() {
        return id;
    }

    /**
     * Getter current unit state.
     * @return current unit state
     */
    public boolean getCurrentState() {
        return currentState;
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
        return new Unit.UnitBuilder()
                .setId(originalUnit.getId())
                .setState(originalUnit.getCurrentState())
                .setStateHandler(originalUnit.stateCalculator)
                .build();
    }

    /**
     * Builder class that create Unit instance.
     * Unit instance must be created through this builder.
     */
    public static class UnitBuilder {

        private long id;
        private boolean state;
        private StateCalculator stateHandler;

        /**
         * Setter for unit ID.
         * @param id unit ID
         * @return UnitBuilder instance
         */
        public UnitBuilder setId(long id) {
            this.id = id;
            return this;
        }

        /**
         * Setter for unit state.
         * @param state unit state
         * @return UnitBuilder instance
         */
        public UnitBuilder setState(boolean state){
            this.state = state;
            return this;
        }

        /**
         * Setter for state calculator
         * @param stateHandler state calculator
         * @return UnitBuilder instance
         */
        public UnitBuilder setStateHandler(StateCalculator stateHandler){
            this.stateHandler = stateHandler;
            return this;
        }

        /**
         * Build method that made new Unit instance.
         * @return new Unit instance
         */
        public Unit build(){
            return new Unit(this.id, this.state, this.stateHandler);
        }
    }
}

