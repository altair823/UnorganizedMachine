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
        Unit unit1 = Unit.builder().id(0).currentState(true).build();
        // Unit verification
        assertEquals(0, unit1.getId());
        assertTrue(unit1.isCurrentState());

        Unit unit2 = Unit.builder().currentState(true).build();
        // Unit verification
        assertEquals(0, unit2.getId());
        assertTrue(unit2.isCurrentState());
    }

    @Test
    void inputPulseTest() {
        Unit unit = Unit.builder().id(0).currentState(true).stateCalculator(this.stateHandler).build();
        // Pulse test
        unit.addPreviousStates(true);
        unit.addPreviousStates(true);
        unit.calculateState();
        assertFalse(unit.isCurrentState());
    }

    @Test
    void copyTest() {
        // create original
        Unit unit1 = Unit.builder()
                .id(0)
                .currentState(true)
                .stateCalculator(this.stateHandler)
                .build();
        System.out.println(unit1);

        // copy!
        Unit unit2 = Unit.copy(unit1);
        System.out.println(unit2);

        // compare
        assertNotSame(unit1, unit2);
    }
}