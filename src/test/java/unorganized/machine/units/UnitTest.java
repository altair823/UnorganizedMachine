package unorganized.machine.units;

import org.junit.jupiter.api.Test;
import unorganized.machine.calculator.StateHandler;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {

    private final Unit.UnitBuilder unitBuilder;
    private final StateHandler stateHandler;

    public UnitTest(){
        this.stateHandler = new StateHandler() {
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
            public boolean deliver(boolean state) {
                return state;
            }
        };
        this.unitBuilder = new Unit.UnitBuilder();
    }

    @Test
    void unitBuilderTest() {
        Unit unit = this.unitBuilder.setId(0).setState(true).build();
        // Unit verification
        assertEquals(0, unit.getId());
        assertTrue(unit.getCurrentState());
    }

    @Test
    void inputPulseTest() {
        Unit unit = this.unitBuilder.setId(0).setState(true).setStateHandler(this.stateHandler).build();
        // Pulse test
        unit.addPreviousStates(new LinkedList<>(List.of(true, true)));
        unit.inputPulse();
        assertFalse(unit.getCurrentState());
    }
}