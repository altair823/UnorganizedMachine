package unorganized.machine.units;

import unorganized.machine.edges.Edge;

import java.util.Collection;
import java.util.HashMap;
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
    private static final Map<Long, Unit> unitTable = new HashMap<>();

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
    private Collection<Boolean> previousStates;

    /**
     * Calculator for current unit state.
     */
    private final StateCalculator calculator;

    /**
     * Constructor that create a new Unit object.
     * @param id unit ID
     * @param state initial unit state
     */
    Unit(long id, boolean state, StateCalculator stateCalculator) {
        this.id = id;
        this.currentState = state;
        this.calculator = stateCalculator;
        // Registering new Unit
        unitTable.put(this.id, this);
    }

    /**
     * The function that receives a pulse from the control and updates the current state.
     */
    public void inputPulse(){
        if (calculator != null) {
            this.currentState = calculator.calculate(previousStates);
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
     * Add state of previous unit.
     * @param state state of previous unit
     */
    public void addPreviousStates(boolean state){
        this.previousStates.add(state);
    }

    /**
     * Add state collection from previous units.
     * @param states collection of states
     */
    public void addPreviousStates(Collection<Boolean> states){
        if (this.previousStates != null) {
            this.previousStates.addAll(states);
        }
        else {
            this.previousStates = states;
        }
    }

    /**
     * Builder class that create Unit instance.
     * Unit instance must be created through this builder.
     */
    public static class UnitBuilder {

        protected long id;
        protected boolean state;
        protected StateCalculator stateCalculator;

        /**
         * Constructor for Unit builder without unit ID.
         */
        public UnitBuilder(){
            long newId = latestUnitId;
            while (unitTable.containsKey(newId)){
                newId--;
            }
            this.id = newId;
        }

        /**
         * Constructor for Unit builder.
         * @param id unit ID
         */
        public UnitBuilder(long id){
            this.id = id;
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
         * @param stateCalculator state calculator
         * @return UnitBuilder instance
         */
        public UnitBuilder setStateCalculator(StateCalculator stateCalculator){
            this.stateCalculator = stateCalculator;
            return this;
        }

        /**
         * Build method that made new Unit instance.
         * @return new Unit instance
         */
        public Unit build(){
            return new Unit(this.id, this.state, this.stateCalculator);
        };
    }
}

