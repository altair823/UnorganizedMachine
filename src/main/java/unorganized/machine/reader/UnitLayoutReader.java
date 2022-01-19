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
     */
    private final List<Map<String, Object>> machineData = new LinkedList<>();

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
     * @return machine data
     */
    List<Map<String, Object>> getMachineData() {
        return machineData;
    }

    /**
     * Method setting all layout data to machine data Map.
     * @param dataMapper Map that contains mapper objects mapping data from reed list.
     */
    public void mapAllLine(Map<String, DataMapper> dataMapper){
        while (this.scanner.hasNext()) {
            List<String> line = this.readALine();
            Map<String, Object> lineDataMap = dataMapper.get(line.get(0)).map(line);
            this.machineData.add(lineDataMap);
        }
    }

    /**
     * Method creating all unit instances and allocate it to unitMap.
     */
    void createAllUnits(){
        for (Map<String, Object> dataMap : this.machineData){
            new Unit.UnitBuilder()
                    .setId((Long) dataMap.get("id"))
                    .setState((Boolean) dataMap.get("state"))
                    .setStateHandler((StateCalculator) dataMap.get("stateHandler"))
                    .build();
        }
    }

    /**
     * Method creating all edge instances and allocate it to edgeMap.
     */
    void createAllEdges(){
        this.machineData.forEach(unitDataMap -> {
            if (unitDataMap.get("previousUnitId") instanceof List<?> previousUnitId) {
                previousUnitId.forEach(obj -> {
                    if (obj instanceof Long previousId) {
                        new Edge.EdgeBuilder()
                                .setTailUnit(Unit.getUnitMap().get(previousId))
                                .setHeadUnit(Unit.getUnitMap().get((Long) unitDataMap.get("id")))
                                .setStateDeliver(new StateDeliver())
                                .build();
                    }
                });
            }
        });
    }

    /**
     * Method creating all units and edges.
     * To create units and edges in the right order,
     * need to use this method instead other method.
     */
    public void createAllUnitsAndEdges(){
        this.createAllUnits();
        this.createAllEdges();
    }
}
