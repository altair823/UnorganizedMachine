package unorganized.machine.edges;

import org.junit.jupiter.api.Test;
import unorganized.machine.calculator.StateCalculator;
import unorganized.machine.deliver.StateDeliver;
import unorganized.machine.units.Unit;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    private final List<Unit> units = new LinkedList<>();
    private final List<Edge> edges = new LinkedList<>();
    private final StateDeliver stateDeliver = new StateDeliver();

    EdgeTest(){
        // create two units.
        StateCalculator stateHandler = states -> {
            if (states instanceof List<Boolean> stateList) {
                return !(stateList.get(0) && stateList.get(1));
            } else {
                throw new ClassCastException();
            }
        };
        units.add(new Unit.UnitBuilder().setState(true).setStateHandler(stateHandler).build());
        units.add(new Unit.UnitBuilder().setState(true).setStateHandler(stateHandler).build());
    }

    @Test
    void edgeConnectTest(){
        this.edges.add(new Edge.EdgeBuilder()
                .setTailUnit(units.get(0))
                .setHeadUnit(units.get(1))
                .setStateDeliver(new StateDeliver())
                .build());

        // check tail and head unit.
        assertEquals(units.get(0), edges.get(0).getTailUnit());
        assertEquals(units.get(1), edges.get(0).getHeadUnit());
        System.out.println(this.edges.get(0).getId());
    }

    @Test
    void handOverStateTest(){
        // set two edges between two units.
        this.edges.add(new Edge.EdgeBuilder()
                .setTailUnit(units.get(0))
                .setHeadUnit(units.get(1))
                .setStateDeliver(this.stateDeliver)
                .build());
        this.edges.add(new Edge.EdgeBuilder()
                .setTailUnit(units.get(0))
                .setHeadUnit(units.get(1))
                .setStateDeliver(this.stateDeliver)
                .build());


        // execute state hand over function.
        this.edges.forEach(Edge::deliverState);

        // check head unit's previous state list.
        this.units.get(1).calculateState();
        assertFalse(this.units.get(1).getCurrentState());
    }
}