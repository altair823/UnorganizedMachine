package unorganized.machine.edges;

import org.junit.jupiter.api.Test;
import unorganized.machine.handler.StateHandler;
import unorganized.machine.units.Unit;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    private final List<Unit> units = new LinkedList<>();
    private final List<Edge> edges = new LinkedList<>();
    private final StateHandler stateHandler = new StateHandler() {
        @Override
        public boolean calculate(Collection<Boolean> states) {
            if (states instanceof List<Boolean> stateList) {
                return !(stateList.get(0) && stateList.get(1));
            }
            else {
                throw new ClassCastException();
            }
        }

        @Override
        public List<Boolean> deliver(boolean state) {
            return List.of(state);
        }
    };

    EdgeTest(){
        // create two units.
        units.add(new Unit.UnitBuilder().setState(true).setStateHandler(stateHandler).build());
        units.add(new Unit.UnitBuilder().setState(true).setStateHandler(stateHandler).build());
    }

    @Test
    void edgeConnectTest(){
        this.edges.add(new Edge.EdgeBuilder()
                .setTailUnit(units.get(0))
                .setHeadUnit(units.get(1))
                .setStateHandler(this.stateHandler)
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
                .setStateHandler(this.stateHandler)
                .build());
        this.edges.add(new Edge.EdgeBuilder()
                .setTailUnit(units.get(0))
                .setHeadUnit(units.get(1))
                .setStateHandler(this.stateHandler)
                .build());

        // set previous list instance in unit1.
        this.units.get(1).addPreviousStates(new LinkedList<>());

        // execute state hand over function.
        this.edges.forEach(Edge::deliverState);

        // check head unit's previous state list.
        this.units.get(1).calculateState();
        assertFalse(this.units.get(1).getCurrentState());
    }
}