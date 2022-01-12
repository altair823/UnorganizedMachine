package unorganized.machine.edges;

import unorganized.machine.units.Unit;

import java.util.LinkedList;

/**
 * Edge class that connect two unit objects.
 * All units communicate with each other through these edges.
 * @author altair823
 * @see Unit
 */
public class Edge {

    /**
     * Unit that provides state.
     */
    private final Unit tailUnit;

    /**
     * Unit that receive state and calculate it.
     */
    private final Unit headUnit;

    /**
     * Constructor for edge instance.
     * @param tailUnit tail unit
     * @param headUnit head unit
     */
    public Edge(Unit tailUnit, Unit headUnit){
        this.tailUnit = tailUnit;
        this.headUnit = headUnit;
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
     * Hand over the state of previous(tail) unit to next(head) unit.
     */
    public void handOverState(){
        this.headUnit.addPreviousStates(tailUnit.getCurrentState());
    }
}
