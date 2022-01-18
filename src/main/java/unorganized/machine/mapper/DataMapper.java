package unorganized.machine.mapper;

import java.util.List;
import java.util.Map;

/**
 * Data mapper for building unit.
 * To create a new unit object, ID and states, previous and next unit pointer are needed.
 * We can use builder instance to set these all values.
 * But when reading a layout file, values are read in separated way for example, Map.
 * So, to create a new unit instance, there has to be a method that create a unit from the Map data.
 */
public interface DataMapper {

    /**
     * Method mapping data from list to map for matching unit type.
     * @param dataList data needed to create a new unit.
     * @return Map that contain data
     */
    Map<String, Object> map(List<String> dataList);
}
