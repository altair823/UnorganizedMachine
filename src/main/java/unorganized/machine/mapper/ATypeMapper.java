package unorganized.machine.mapper;

import unorganized.machine.calculator.ATypeHander;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ATypeMapper implements DataMapper{
    @Override
    public Map<String, Object> map(List<String> dataList) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("type", dataList.get(0));
        dataMap.put("id", Long.parseLong(dataList.get(1)));
        dataMap.put("previousUnitId", List.of(Long.parseLong(dataList.get(2)), Long.parseLong(dataList.get(3))));
        if (Objects.equals(dataList.get(4), "0")){
            dataMap.put("state", false);
        }
        else {
            dataMap.put("state", true);
        }
        dataMap.put("stateHandler", new ATypeHander());
        return dataMap;
    }
}
