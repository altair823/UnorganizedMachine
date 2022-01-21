package unorganized.machine.units;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import unorganized.machine.calculator.StateCalculator;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

    private final StateCalculator stateHandler;

    public UnitTest(){
        this.stateHandler = states -> {
            if (states instanceof List<Boolean> stateList) {
                return !(stateList.get(0) && stateList.get(1));
            }
            else {
                throw new ClassCastException();
            }
        };
    }

    @Test
    void unitBuilderTest() {
        Unit unit1 = new Unit.UnitBuilder().setId(0).setState(true).build();
        // Unit verification
        assertEquals(0, unit1.getId());
        assertTrue(unit1.getCurrentState());

        Unit unit2 = new Unit.UnitBuilder().setState(true).build();
        // Unit verification
        assertEquals(1, unit2.getId());
        assertTrue(unit2.getCurrentState());
    }

    @Test
    void inputPulseTest() {
        Unit unit = new Unit.UnitBuilder().setId(0).setState(true).setStateHandler(this.stateHandler).build();
        // Pulse test
        unit.addPreviousStates(true);
        unit.addPreviousStates(true);
        unit.calculateState();
        assertFalse(unit.getCurrentState());
    }

    @Test
    void copyTest() {
        // create original
        Unit unit1 = new Unit.UnitBuilder()
                .setId(0)
                .setState(true)
                .setStateHandler(this.stateHandler)
                .build();
        System.out.println(unit1);

        // copy!
        Unit unit2 = Unit.copy(unit1);
        System.out.println(unit2);

        // compare
        assertNotSame(unit1, unit2);
    }
}