package unorganized.machine.mapper;

import unorganized.machine.calculator.ATypeCalculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ATypeMapper implements DataMapper{

    @Override
    public Map<String, Object> map(List<String> dataList) {
        Map<String, Object> dataMap = new HashMap<>();
        if(dataList.size() == 5) {
            dataMap.put("type", dataList.get(0));
            dataMap.put("id", Long.parseLong(dataList.get(1)));
            dataMap.put("previousUnitId", List.of(Long.parseLong(dataList.get(2)), Long.parseLong(dataList.get(3))));
            if (Objects.equals(dataList.get(4), "0")) {
                dataMap.put("state", false);
            } else {
                dataMap.put("state", true);
            }
        }
        else if(dataList.size() == 4){
            dataMap.put("type", dataList.get(0));
            dataMap.put("previousUnitId", List.of(Long.parseLong(dataList.get(1)), Long.parseLong(dataList.get(2))));
            if (Objects.equals(dataList.get(3), "0")) {
                dataMap.put("state", false);
            } else {
                dataMap.put("state", true);
            }
        }
        dataMap.put("stateHandler", new ATypeCalculator());
        return dataMap;
    }
}
