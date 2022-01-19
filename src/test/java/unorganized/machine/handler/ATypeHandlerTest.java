package unorganized.machine.handler;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ATypeHandlerTest {

    @Test
    void calculateTest() {
        StateHandler stateHandler = new ATypeHander();
        assertFalse(stateHandler.calculate(List.of(true, true)));
    }
}