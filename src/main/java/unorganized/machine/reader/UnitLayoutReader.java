package unorganized.machine.reader;

import unorganized.machine.calculator.StateHandler;
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
    protected final Scanner scanner;

    /**
     * Map that contains all unit layout data read from ULF.
     */
    protected final Map<Long, Map<String, Object>> unitMap = new HashMap<>();

    /**
     * Map that contains all edge layout data read from ULF.
     */
    protected final Map<Long, Map<String, Object>> edgeMap = new HashMap<>();

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
    protected List<String> readALine(){
        String line = this.scanner.nextLine();
        return List.of(line.split(" "));
    }


    /**
     * Method setting all layout data to machine data Map.
     * @param dataMapper Map that contains mapper objects mapping data from reed list.
     * @return Map containing total unit data.
     */
    protected List<Map<String, Object>> mapAllLine(Map<String, DataMapper> dataMapper){
        List<Map<String, Object>> machineDataMapList = new LinkedList<>();
        while (this.scanner.hasNext()) {
            List<String> line = this.readALine();
            Map<String, Object> lineDataMap = dataMapper.get(line.get(0)).ListToUnitDataMap(line);
            machineDataMapList.add(lineDataMap);
        }
        return machineDataMapList;
    }

    /**
     * Method creating all unit instances and allocate it to unitMap.
     * @param machineData Map that contains data read from unit layout file.
     */
    public void createAllUnits(List<Map<String, Object>> machineData){
        for (Map<String, Object> dataMap : machineData){
            new Unit.UnitBuilder()
                    .setId((Long) dataMap.get("id"))
                    .setState((Boolean) dataMap.get("state"))
                    .setStateHandler((StateHandler) dataMap.get("stateHandler"))
                    .build();
        }
    }

    /**
     * Method creating all edge instances and allocate it to edgeMap.
     * @param machineData Map that contains data read from unit layout file.
     */
    public void createAllEdges(List<Map<String, Object>> machineData){
        machineData.forEach(unitDataMap -> {
            if (unitDataMap.get("previousUnitId") instanceof List<?> previousUnitId) {
                previousUnitId.forEach(obj -> {
                    if (obj instanceof Long previousId) {
                        new Edge.EdgeBuilder()
                                .setTailUnit(Unit.getUnitMap().get(previousId))
                                .setHeadUnit(Unit.getUnitMap().get((Long) unitDataMap.get("id")))
                                .setStateHandler((StateHandler) unitDataMap.get("stateHandler"))
                                .build();
                    }
                });
            }
        });
    }
}
