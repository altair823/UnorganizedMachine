package unorganized.machine.control;

import org.junit.jupiter.api.Test;
import unorganized.machine.edges.Edge;
import unorganized.machine.mapper.ATypeMapper;
import unorganized.machine.mapper.DataMapper;
import unorganized.machine.reader.UnitLayoutReader;
import unorganized.machine.units.Unit;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.nio.file.FileSystemException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ControlTest {

    private final Control control = new Control();

    ControlTest(){
        control.addMapper("A", new ATypeMapper());
    }

    @Test
    void readLayoutTest() throws FileSystemException, FileNotFoundException {
        control.readLayout(new UnitLayoutReader(new File("/Users/altair823/IdeaProjects/UnorganizedMachine/layout/TuringExample.ulf")));
        //Unit.getUnitMap().forEach((id, unit)-> System.out.println(unit));
        //Edge.getEdgeMap().forEach((id, edge)-> System.out.println(edge));

        // test the base example in paper.
        Edge.getEdgeMap().forEach((id, edge)->{
            if (edge.getHeadUnit().getId() == 1L){
                assert edge.getTailUnit().getId() == 3L || edge.getTailUnit().getId() == 2L;
            } else if (edge.getHeadUnit().getId() == 2L) {
                assert edge.getTailUnit().getId() == 3L || edge.getTailUnit().getId() == 5L;
            } else if (edge.getHeadUnit().getId() == 3L) {
                assert edge.getTailUnit().getId() == 4L || edge.getTailUnit().getId() == 5L;
            } else if (edge.getHeadUnit().getId() == 4L) {
                assert edge.getTailUnit().getId() == 3L || edge.getTailUnit().getId() == 4L;
            } else if (edge.getHeadUnit().getId() == 5L) {
                assert edge.getTailUnit().getId() == 2L || edge.getTailUnit().getId() == 5L;
            } else {
                assert false;
            }
        });
    }

    @Test
    void makePulseTest() throws FileSystemException, FileNotFoundException {
        control.readLayout(new UnitLayoutReader(new File("/Users/altair823/IdeaProjects/UnorganizedMachine/layout/TuringExample.ulf")));
        Unit.getUnitMap().forEach((id, unit)-> System.out.print((unit.getCurrentState() ? 1 : 0) + " "));
        System.out.println();
        for (int i = 0; i < 10; i++) {
            control.makePulse();
            Unit.getUnitMap().forEach((id, unit) -> System.out.print((unit.getCurrentState() ? 1 : 0) + " "));
            System.out.println();
        }
    }
}