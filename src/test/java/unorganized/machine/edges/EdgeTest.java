package unorganized.machine.edges;

import org.junit.jupiter.api.Test;
import unorganized.machine.units.Unit;
import unorganized.machine.units.*;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    private final List<Unit> units = new LinkedList<>();
    private final List<Edge> edges = new LinkedList<>();

    EdgeTest(){
        // create calculator.
        StateCalculator stateCalculator = states -> {
            if (states instanceof List<Boolean> stateList) {
                return !(stateList.get(0) && stateList.get(1));
            }
            else {
                throw new ClassCastException();
            }
        };

        // create two units.
        units.add(new Unit.UnitBuilder().setState(true).setStateCalculator(stateCalculator).build());
        units.add(new Unit.UnitBuilder().setState(true).setStateCalculator(stateCalculator).build());
    }

    @Test
    void edgeConnectTest(){
        this.edges.add(new Edge(units.get(0), units.get(1)));

        // check tail and head unit.
        assertEquals(units.get(0), edges.get(0).getTailUnit());
        assertEquals(units.get(1), edges.get(0).getHeadUnit());
    }

    @Test
    void handOverStateTest(){
        // set two edges between two units.
        this.edges.add(new Edge(units.get(0), units.get(1)));
        this.edges.add(new Edge(units.get(0), units.get(1)));

        // set previous list instance in unit1.
        this.units.get(1).addPreviousStates(new LinkedList<>());

        // execute state hand over function.
        this.edges.forEach(Edge::handOverState);

        // check head unit's previous state list.
        this.units.get(1).inputPulse();
        assertFalse(this.units.get(1).getCurrentState());
    }
}