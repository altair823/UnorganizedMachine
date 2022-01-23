package unorganized.machine.reader;

import unorganized.machine.calculator.StateCalculator;
import unorganized.machine.deliver.StateDeliver;
import unorganized.machine.edges.Edge;
import unorganized.machine.mapper.DataMapper;
import unorganized.machine.units.Unit;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.*;

/**
 * Reader class reading a file that contains units layout.
 * If there is other types of unit in a layout file,
 * must implement a new reader class that inherited this layout reader class to handle those units.
 * @author altair823
 */
public class UnitLayoutReader {

    /**
     * File scanner.
     */
    private final Scanner scanner;

    /**
     * Data containing layout for unit of machine read from file.
     * An element of this list includes the details of particular unit and its edges.
     */
    private final List<Map<String, Object>> lineData = new LinkedList<>();

    /**
     * Map containing units of current machine.
     * The key is not the unit ID, but the sequence number of unit which on the current machine.
     */
    private final List<Unit> currentUnitList = new ArrayList<>();

    /**
     * Constructor that create scanner for reading unit layout file.
     * @param layoutFile File object contains unit layout file
     * @throws FileNotFoundException There is no target unit layout file.
     * @throws FileSystemException The given file is not unit layout file or has wrong file system.
     */
    public UnitLayoutReader(File layoutFile) throws FileNotFoundException, FileSystemException {
        this.scanner = new Scanner(layoutFile);
        if (!this.verifyUnitLayoutFile()){
            throw new FileSystemException(layoutFile.getAbsolutePath());
        }
    }

    /**
     * Method for verify unit layout file.
     * @return boolean value for whether the file is correct
     */
    private boolean verifyUnitLayoutFile(){
        return scanner.hasNext() && Objects.equals(scanner.nextLine(), "UnitLayoutDataFile");
    }

    /**
     * Method reading a line of the layout file and split it to given string.
     * @return a list of strings.
     */
    List<String> readALine(){
        String line = this.scanner.nextLine();
        return List.of(line.split(" "));
    }

    /**
     * Getter for machine data read from file.
     * @return line data
     */
    List<Map<String, Object>> getLineData() {
        return lineData;
    }

    /**
     * Method setting all layout data to machine data Map.
     * @param dataMapper Map that contains mapper objects mapping data from reed list.
     */
    public void mapAllLine(Map<String, DataMapper> dataMapper){
        while (this.scanner.hasNext()) {
            List<String> line = this.readALine();
            Map<String, Object> unitDataMap = dataMapper.get(line.get(0)).map(line);
            this.lineData.add(unitDataMap);
        }
    }

    /**
     * Method creating all unit instances and allocate it to unitMap.
     * @return Map containing data of all units.
     */
    public Map<Long, Unit> createAllUnits(){
        Map<Long, Unit> unitMap = new HashMap<>();
        long id = 1;
        for (Map<String, Object> dataMap : this.lineData){
            Unit newUnit;
            if (dataMap.containsKey("id")) {
                newUnit = new Unit.UnitBuilder()
                        .setId((long) dataMap.get("id"))
                        .setState((boolean) dataMap.get("state"))
                        .setStateHandler((StateCalculator) dataMap.get("stateHandler"))
                        .build();
            }
            else {
                if (unitMap.containsKey(id)){
                    id++;
                }
                newUnit = new Unit.UnitBuilder()
                        .setId(id)
                        .setState((boolean) dataMap.get("state"))
                        .setStateHandler((StateCalculator) dataMap.get("stateHandler"))
                        .build();
            }
            currentUnitList.add(newUnit);
            unitMap.put(newUnit.getId(), newUnit);
        }
        return unitMap;
    }

    /**
     * Method creating all edge instances and allocate it to edgeMap.
     * @return Map of Edge instances
     */
    public Map<Long, Edge> createAllEdges(){
        if (this.currentUnitList.isEmpty()){
            throw new NoSuchElementException();
        }
        Map<Long, Edge> edgeMap = new HashMap<>();
        long id = 1;
        for (Map<String, Object> unitData : this.lineData) {
            if (unitData.get("previousUnitId") instanceof List<?> previousUnitIds) {
                for (Object obj : previousUnitIds) {
                    if (obj instanceof Long previousId) {
                        if (previousId != 0) {
                            if (edgeMap.containsKey(id)) {
                                id++;
                            }
                            Edge newEdge = new Edge.EdgeBuilder()
                                    .setId(id)
                                    .setTailUnit(this.currentUnitList.get(Integer.parseInt(String.valueOf(previousId - 1))))
                                    .setHeadUnit(this.currentUnitList.get(lineData.indexOf(unitData)))
                                    .setStateDeliver(new StateDeliver())
                                    .build();
                            edgeMap.put(newEdge.getId(), newEdge);
                        }
                    }
                }
            }
        }
        return edgeMap;
    }
}
