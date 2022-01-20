package unorganized.machine.edges;

import unorganized.machine.deliver.StateDeliver;
import unorganized.machine.units.Unit;

import java.util.HashMap;
import java.util.Map;

/**
 * Edge class that connect two unit objects.
 * All units communicate with each other through these edges.
 * @author altair823
 * @see Unit
 */
public class Edge {

    public long getId() {
        return id;
    }

    /**
     * Edge ID.
     */
    private final long id;

    /**
     * Unit that provides state.
     */
    private final Unit tailUnit;

    /**
     * Unit that receive state and calculate it.
     */
    private final Unit headUnit;

    /**
     * Handler that set a rule handing over the state from tail unit to head unit.
     */
    private final StateDeliver stateDeliver;

    /**
     * Constructor for edge instance.
     * @param tailUnit tail unit
     * @param headUnit head unit
     */
    public Edge(long id, Unit tailUnit, Unit headUnit, StateDeliver stateDeliver){
        this.id = id;
        this.tailUnit = tailUnit;
        this.headUnit = headUnit;
        this.stateDeliver = stateDeliver;
    }

    /**
     * Getter for tail unit.
     * @return tail unit
     */
    public Unit getTailUnit() {
        return tailUnit;
    }

    /**
     * Getter for head unit.
     * @return head unit
     */
    public Unit getHeadUnit() {
        return headUnit;
    }

    /**
     * Deliver the state of previous(tail) unit to next(head) unit.
     */
    public void deliverState(){
        if (this.tailUnit != null) {
            this.headUnit.addPreviousStates(this.stateDeliver.deliver(tailUnit.getCurrentState()));
        }
    }

    /**
     * Reverse the rule used when delivering state.
     */
    public void reverseDeliverRule(){
        this.stateDeliver.reverse();
    }


    @Override
    public String toString(){
        long tailUnitId = -1;
        if (this.tailUnit != null) {
            tailUnitId = this.tailUnit.getId();
        }
        long headUnitId = -1;
        if (this.headUnit != null){
            headUnitId = this.headUnit.getId();
        }
        return "Edge ID: " + this.id + "\n"
                + "tailUnitId: " + tailUnitId + "\n"
                + "headUnitId: " + headUnitId + "\n";
    }

    /**
     * Builder class creating Edge instances.
     */
    public static class EdgeBuilder{
        private long id;
        private Unit tailUnit;
        private Unit headUnit;
        private StateDeliver stateDeliver;

        /**
         * Setter for edge ID.
         * In general, don't need an ID to control the edges.
         * Therefore, it is better not to set the ID.
         * @param id edge ID
         * @return EdgeBuilder instance
         */
        public EdgeBuilder setId(long id){
            this.id = id;
            return this;
        }

        /**
         * Setter for tail unit.
         * @param tailUnit tail unit of edge
         * @return EdgeBuilder instance
         */
        public EdgeBuilder setTailUnit(Unit tailUnit){
            this.tailUnit = tailUnit;
            return this;
        }

        /**
         * Setter for head unit.
         * @param headUnit head unit of edge
         * @return EdgeBuilder instance
         */
        public EdgeBuilder setHeadUnit(Unit headUnit){
            this.headUnit = headUnit;
            return this;
        }

        /**
         * Setter for state handler for setting rule of hand over the state.
         * @param stateDeliver state handler instance
         * @return EdgeBuilder instance
         */
        public EdgeBuilder setStateDeliver(StateDeliver stateDeliver){
            this.stateDeliver = stateDeliver;
            return this;
        }

        /**
         * Method that build a new Edge instance.
         * @return new Edge instance
         */
        public Edge build(){
            return new Edge(this.id, this.tailUnit, this.headUnit, this.stateDeliver);
        }
    }
}
