package unorganized.machine.control;

import unorganized.machine.edges.Edge;
import unorganized.machine.mapper.DataMapper;
import unorganized.machine.reader.UnitLayoutReader;
import unorganized.machine.units.Unit;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that implements the control described in Turing's paper.
 * This Class controls all edges and units in way of using some interface methods.
 */
public class Control {

    private final Map<String, DataMapper> dataMappers = new HashMap<>();

    /**
     * Method to add a new data mapper
     * @param mapperType a string that indicates type of data mapper
     * @param dataMapper a new data mapper
     */
    public void addMapper(String mapperType, DataMapper dataMapper){
        this.dataMappers.put(mapperType, dataMapper);
    }

    /**
     * Method to read unit layout and create all units and edges.
     * @param unitLayoutReader reader object that read unit layout
     */
    public void readLayout(UnitLayoutReader unitLayoutReader){
        unitLayoutReader.mapAllLine(this.dataMappers);
        unitLayoutReader.createAllUnitsAndEdges();
    }

    /**
     * Method that make a pulse to all edges and units.
     */
    public void makePulse(){
        Edge.getEdgeMap().forEach((id, edge)-> edge.deliverState());
        Unit.getUnitMap().forEach((id, unit)-> unit.calculateState());
    }
}
