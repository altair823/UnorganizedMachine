package unorganized.machine.control;

import lombok.Getter;
import lombok.NoArgsConstructor;
import unorganized.machine.deliver.StateDeliver;
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
@Getter
@NoArgsConstructor
public class Control {

    private final Map<String, DataMapper> dataMappers = new HashMap<>();
    private Map<Long, Unit> unitMap;
    private Map<Long, Edge> edgeMap;
    private final Map<Long, Boolean> initialUnitStates = new HashMap<>();

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
        this.unitMap = unitLayoutReader.createAllUnits();
        this.edgeMap = unitLayoutReader.createAllEdges();

        // Save initial unit states.
        this.unitMap.forEach((id, unit) -> this.initialUnitStates.put(id, unit.isCurrentState()));
    }

    /**
     * Method that make a pulse to all edges and units.
     */
    public void makePulse(){
        this.edgeMap.forEach((id, edge) -> edge.deliverState());
        this.unitMap.forEach((id, unit) -> unit.calculateState());
    }

    /**
     * Method that reverse the way a single edge delivering state between two units.
     * @see Edge
     */
    public void reverseSingleEdge(){
        this.edgeMap.get(((long) (Math.random() * 100000) % this.edgeMap.size() + 1)).reverseDeliverRule();
    }

    /**
     * Method that initializes all units with initial unit states.
     */
    public void initUnitStates(){
        this.initialUnitStates.forEach((id, state) -> this.unitMap.get(id).setCurrentState(state));
    }

    /**
     * Copy method that copy the original control object to new one deeply.
     * @param originalControl original control object
     * @return new control object
     */
    public static Control copy(Control originalControl){
        Control newControl = new Control();
        originalControl.getDataMappers().forEach(newControl::addMapper);
        newControl.unitMap = new HashMap<>();
        originalControl.unitMap.forEach((id, unit)-> newControl.unitMap.put(id, Unit.copy(unit)));
        newControl.edgeMap = new HashMap<>();
        originalControl.edgeMap.forEach((id, edge)-> newControl.edgeMap.put(id, Edge.builder()
                .id(id)
                .tailUnit(newControl.unitMap.get(edge.getTailUnit().getId()))
                .headUnit(newControl.unitMap.get(edge.getHeadUnit().getId()))
                .stateDeliver(StateDeliver.copy(edge.getStateDeliver()))
                .build()));

        return newControl;
    }
}
