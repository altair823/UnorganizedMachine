package unorganized.machine.control;

import org.junit.jupiter.api.Test;
import unorganized.machine.edges.Edge;
import unorganized.machine.mapper.ATypeMapper;
import unorganized.machine.reader.UnitLayoutReader;
import unorganized.machine.units.Unit;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControlTest {

    private final Control control = new Control();
    private final UnitLayoutReader unitLayoutReader;

    ControlTest() throws FileSystemException, FileNotFoundException {
        control.addMapper("A", new ATypeMapper());
        this.unitLayoutReader = new UnitLayoutReader(new File("/Users/altair823/IdeaProjects/UnorganizedMachine/layout/machine1.ulf"));
    }

    @Test
    void readLayoutTest(){
        control.readLayout(this.unitLayoutReader);
        //Unit.getUnitMap().forEach((id, unit)-> System.out.println(unit));
        //Edge.getEdgeMap().forEach((id, edge)-> System.out.println(edge));

        // test the base example in paper.
        this.control.getEdgeMap().forEach((id, edge)->{
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
    void makePulseTest() {
        // Read data.
        control.readLayout(this.unitLayoutReader);

        // Print initial states.
        this.control.getUnitMap().forEach((id, unit)-> System.out.print((unit.getCurrentState() ? 1 : 0) + " "));
        System.out.println();

        // Repeat.
        for (int i = 0; i < 10; i++) {

            // Make a pulse.
            control.makePulse();

            // Print result states.
            this.control.getUnitMap().forEach((id, unit) -> System.out.print((unit.getCurrentState() ? 1 : 0) + " "));
            System.out.println();
        }
    }

    @Test
    void reverseDeliverRuleTest() throws FileSystemException, FileNotFoundException {
        // Read data.
        Control control1 = new Control();
        control1.addMapper("A", new ATypeMapper());
        control1.readLayout(this.unitLayoutReader);

        // Print initial states.
        control1.getUnitMap().forEach((id, unit)-> System.out.print((unit.getCurrentState() ? 1 : 0) + " "));
        System.out.println();

        // Repeat.
        for (int i = 0; i < 10; i++) {

            // Make a pulse.
            control1.makePulse();

            // Print result states.
            control1.getUnitMap().forEach((id, unit) -> System.out.print((unit.getCurrentState() ? 1 : 0) + " "));
            System.out.println();

            control1.reverseSingleEdge();
            control1.reverseSingleEdge();
            control1.reverseSingleEdge();
            control1.reverseSingleEdge();
            control1.reverseSingleEdge();
        }
        System.out.println();
        System.out.println();
        // Read data.
        Control control2 = new Control();
        control2.addMapper("A", new ATypeMapper());
        control2.readLayout(new UnitLayoutReader(new File("/Users/altair823/IdeaProjects/UnorganizedMachine/layout/machine1.ulf")));

        // Print initial states.
        control2.getUnitMap().forEach((id, unit)-> {
            if (id == 39){
                System.out.print("  ");
            }
            System.out.print((unit.getCurrentState() ? 1 : 0) + " ");
        });
        System.out.println();

        // Repeat.
        for (int i = 0; i < 20; i++) {

            // Make a pulse.
            control2.makePulse();

            // Print result states.
            control2.getUnitMap().forEach((id, unit) -> {
                if (id == 39){
                    System.out.print("  ");
                }
                System.out.print((unit.getCurrentState() ? 1 : 0) + " ");
            });
            System.out.println();

            control2.reverseSingleEdge();
            control2.reverseSingleEdge();
            control2.reverseSingleEdge();
        }
    }

    @Test
    void MultipleMachineTest() throws FileSystemException, FileNotFoundException {
        List<Control> controlList = new LinkedList<>();
        controlList.add(new Control());
        controlList.add(new Control());
        controlList.add(new Control());
        controlList.add(new Control());

        for (Control control: controlList){
            control.addMapper("A", new ATypeMapper());
            control.readLayout(new UnitLayoutReader(new File("/Users/altair823/IdeaProjects/UnorganizedMachine/layout/machine1.ulf")));
        }

        for (Control control: controlList){
            for (int i=0; i<20; i++) {
                control.getUnitMap().forEach((id, unit) -> System.out.print((unit.getCurrentState() ? 1 : 0) + " "));
                System.out.println();
                control.makePulse();
                control.reverseSingleEdge();
                control.reverseSingleEdge();
                control.reverseSingleEdge();
            }
            System.out.println();
        }
    }

    @Test
    void copy() throws FileSystemException, FileNotFoundException {
        // create original
        Control control1 = new Control();
        control1.addMapper("A", new ATypeMapper());
        control1.readLayout(new UnitLayoutReader(new File("/Users/altair823/IdeaProjects/UnorganizedMachine/layout/machine1.ulf")));

        // copy!
        Control control2 = Control.copy(control1);

        // compare objectively difference
        assertNotSame(control1, control2);

        control1.getUnitMap().forEach((id, unit) -> System.out.print(unit.getCurrentState() ? "1 " : "0 "));
        System.out.println();
        control2.getUnitMap().forEach((id, unit) -> System.out.print(unit.getCurrentState() ? "1 " : "0 "));
        System.out.println();

        control1.reverseSingleEdge();
        control1.reverseSingleEdge();
        control1.reverseSingleEdge();
        control1.reverseSingleEdge();
        control1.reverseSingleEdge();
        control1.reverseSingleEdge();
        control1.reverseSingleEdge();
        control1.reverseSingleEdge();
        control1.reverseSingleEdge();
        control1.reverseSingleEdge();
        control1.reverseSingleEdge();
        control1.reverseSingleEdge();
        control1.reverseSingleEdge();
        control1.reverseSingleEdge();
        control1.reverseSingleEdge();
        control1.reverseSingleEdge();
        control1.reverseSingleEdge();

        control1.makePulse();
        control2.makePulse();
        control1.makePulse();
        control2.makePulse();
        control1.makePulse();
        control2.makePulse();

        // compare result!
        control1.getUnitMap().forEach((id, unit) -> System.out.print(unit.getCurrentState() ? "1 " : "0 "));
        System.out.println();
        control2.getUnitMap().forEach((id, unit) -> System.out.print(unit.getCurrentState() ? "1 " : "0 "));
        System.out.println();

        // compare edges!
        control1.getEdgeMap().forEach((id, edge)->System.out.println(edge));
        control2.getEdgeMap().forEach((id, edge)->System.out.println(edge));
    }

    @Test
    void initUnitStates() throws FileSystemException, FileNotFoundException {
        // create control.
        this.control.readLayout(new UnitLayoutReader(new File("/Users/altair823/IdeaProjects/UnorganizedMachine/layout/machine1.ulf")));

        // print initial states list.
        control.getUnitMap().forEach((id, unit) -> System.out.print(unit.getCurrentState() ? "1 " : "0 "));
        System.out.println();

        // execute.
        this.control.reverseSingleEdge();
        this.control.reverseSingleEdge();
        this.control.reverseSingleEdge();
        this.control.reverseSingleEdge();
        this.control.reverseSingleEdge();
        this.control.reverseSingleEdge();
        this.control.makePulse();

        // print current states list.
        control.getUnitMap().forEach((id, unit) -> System.out.print(unit.getCurrentState() ? "1 " : "0 "));
        System.out.println();

        // initialize.
        this.control.initUnitStates();

        // print initialized states list.
        control.getUnitMap().forEach((id, unit) -> System.out.print(unit.getCurrentState() ? "1 " : "0 "));
        System.out.println();
    }
}