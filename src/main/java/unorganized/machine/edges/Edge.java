package unorganized.machine.edges;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import unorganized.machine.deliver.StateDeliver;
import unorganized.machine.units.Unit;

/**
 * Edge class that connect two unit objects.
 * All units communicate with each other through these edges.
 * @author altair823
 * @see Unit
 */
@Getter
@AllArgsConstructor
@Builder
public class Edge {

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
     * Deliver the state of previous(tail) unit to next(head) unit.
     */
    public void deliverState(){
        if (this.tailUnit != null) {
            this.headUnit.addPreviousStates(this.stateDeliver.deliver(tailUnit.isCurrentState()));
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
                + "headUnitId: " + headUnitId + "\n"
                + "state flip: " + this.stateDeliver.isDeliverWay() + "\n";
    }

    /**
     * Copy factory method that copy the original edge object deeply.
     * @param originalEdge original edge object
     * @return new copied edge object
     */
    public static Edge copy(Edge originalEdge){
        return Edge.builder()
                .id(originalEdge.getId())
                .tailUnit(originalEdge.getTailUnit())
                .headUnit(originalEdge.getHeadUnit())
                .stateDeliver(StateDeliver.copy(originalEdge.stateDeliver))
                .build();
    }
}
