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
    private UnitLayoutReader unitLayoutReader;

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
        this.unitLayoutReader = unitLayoutReader;
        this.unitLayoutReader.mapAllLine(this.dataMappers);
        this.unitLayoutReader.createAllUnitsAndEdges();
    }

    /**
     * Method that make a pulse to all edges and units.
     */
    public void makePulse(){
        this.unitLayoutReader.getEdgeMap().forEach((id, edge)-> edge.deliverState());
        this.unitLayoutReader.getUnitMap().forEach((id, unit)-> unit.calculateState());
    }

    /**
     * Method that reverse the way a single edge delivering state between two units.
     * @see Edge
     */
    public void reverseSingleEdge(){
        this.unitLayoutReader.getEdgeMap().get(((long) (Math.random() * 100000) % this.unitLayoutReader.getEdgeMap().size() + 1)).reverseDeliverRule();
    }

    /**
     * Getter for total unit map.
     * @return unit map
     */
    public Map<Long, Unit> getUnitMap(){
        return this.unitLayoutReader.getUnitMap();
    }

    /**
     * Getter for total edge map.
     * @return edge map
     */
    public Map<Long, Edge> getEdgeMap(){
        return this.unitLayoutReader.getEdgeMap();
    }
}
