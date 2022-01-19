package unorganized.machine.units;

import unorganized.machine.handler.StateHandler;
import unorganized.machine.edges.Edge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
     * Table that stores all existing units with their IDs.
     */
    private static final Map<Long, Unit> unitMap = new HashMap<>();

    /**
     * ID of unit object that was created latest.
     */
    private static long latestUnitId = -1;

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
    private final StateHandler stateHandler;

    /**
     * Constructor that create a new Unit object.
     * @param id unit ID
     * @param state initial unit state
     * @param stateHandler State calculator object
     */
    Unit(long id, boolean state, StateHandler stateHandler) {
        this.id = id;
        this.currentState = state;
        this.stateHandler = stateHandler;
        // Registering new Unit
        unitMap.put(this.id, this);
    }

    /**
     * The function that receives a pulse from the control and updates the current state.
     */
    public void calculateState(){
        if (stateHandler != null) {
            this.currentState = stateHandler.calculate(previousStates);
            this.previousStates = null;
        }
        else {
            throw new NullPointerException("There is no existing state calculator!");
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
    public void addPreviousStates(List<Boolean> states){
        if (this.previousStates != null) {
            this.previousStates.addAll(states);
        }
        else {
            this.previousStates = states;
        }
    }

    /**
     * Getter for unitTable.
     * @return Map that contains all unit data
     */
    public static Map<Long, Unit> getUnitMap(){
        return unitMap;
    }

    @Override
    public String toString(){
        return "Unit ID: " + this.id + "\n"
                + "state: " + this.currentState + "\n"
                + "state calculator: " + this.stateHandler.getClass().getSimpleName() + "\n";
    }

    /**
     * Builder class that create Unit instance.
     * Unit instance must be created through this builder.
     */
    public static class UnitBuilder {

        private long id;
        private boolean state;
        private StateHandler stateHandler;

        /**
         * Constructor for Unit builder without unit ID.
         */
        public UnitBuilder(){
            long newId = latestUnitId;
            while (unitMap.containsKey(newId)){
                newId--;
            }
            this.id = newId;
            latestUnitId = this.id;
        }

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
        public UnitBuilder setStateHandler(StateHandler stateHandler){
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

