package unorganized.machine.calculator;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ATypeCalculatorTest {

    @Test
    void calculateTest() {
        StateCalculator stateCalculator = new ATypeCalculator();
        assertFalse(stateCalculator.calculate(List.of(true, true)));
    }
}