package unorganized.machine.reader;

import org.junit.jupiter.api.Test;
import unorganized.machine.edges.Edge;
import unorganized.machine.mapper.ATypeMapper;
import unorganized.machine.mapper.DataMapper;
import unorganized.machine.units.Unit;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UnitLayoutReaderTest {

    private final UnitLayoutReader unitLayoutReader;

    public UnitLayoutReaderTest() throws FileNotFoundException, FileSystemException {
        this.unitLayoutReader = new UnitLayoutReader(new File("/Users/altair823/IdeaProjects/UnorganizedMachine/layout/machine1.ulf"));
    }

    @Test
    public void verifyUnitLayoutFileTest(){
        assertDoesNotThrow(()->{
            UnitLayoutReader unitLayoutReaderTest = new UnitLayoutReader(new File("/Users/altair823/IdeaProjects/UnorganizedMachine/layout/machine1.ulf"));
        });
    }

    @Test
    public void readALineTest(){
        List<String> testData = this.unitLayoutReader.readALine();
        for (String c: testData){
            System.out.println(c);
        }
    }

    @Test
    public void mapAllLineTest(){
        Map<String, DataMapper> dataMapperMap = new HashMap<>();
        dataMapperMap.put("A", new ATypeMapper());
        this.unitLayoutReader.mapAllLine(dataMapperMap);
        for (Map<String, Object> map: this.unitLayoutReader.getLineData()){
            map.forEach((key, value)-> System.out.println(key + " : " + value));
            System.out.println("-----------------");
        }
    }

    @Test
    public void createAllUnitsTest(){
        Map<String, DataMapper> dataMapperMap = new HashMap<>();
        dataMapperMap.put("A", new ATypeMapper());
        this.unitLayoutReader.mapAllLine(dataMapperMap);
        Map<Long, Unit> unitMap = this.unitLayoutReader.createAllUnits();
        unitMap.forEach((id, unit)-> System.out.println(unit));
    }

    @Test
    public void createAllEdgesTest(){
        Map<String, DataMapper> dataMapperMap = new HashMap<>();
        dataMapperMap.put("A", new ATypeMapper());
        this.unitLayoutReader.mapAllLine(dataMapperMap);
        this.unitLayoutReader.createAllUnits();
        Map<Long, Edge> edgeMap = this.unitLayoutReader.createAllEdges();
        edgeMap.forEach((id, edge)-> System.out.println(edge));
    }
}