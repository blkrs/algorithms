package krzych;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by krzych on 31.12.16.
 */
public class ColumnsMapping {
    private Map<Integer, Map<String,Double>> columnsMapping = new HashMap<Integer, Map<String, Double>>();
    private Map<Integer, Map<Double,String>> reverseMappings = new HashMap<Integer, Map<Double, String>>();
    private Map<Integer, Double> columnsMax = new HashMap<Integer, Double>();

    public Double getSDMapping(String e, int columnNo) {
        if (!isColumnMapped(columnNo)) {
            initRow(columnNo);
        }
        return getOrAddMapping(columnNo, e);
    }

    public String getDSMapping(int columnNo, Double key) {
        if (isColumnMapped(columnNo)) {
            return reverseMappings.get(columnNo).get(key);
        }
        return null;
    }

    public boolean isColumnMapped(int columnNo) {
        return columnsMax.containsKey(columnNo);
    }


    private void initRow(int columnNo) {
        columnsMax.put(columnNo, 0.0);
        columnsMapping.put(columnNo, new HashMap<String, Double>());
        reverseMappings.put(columnNo, new HashMap<Double, String>());
    }

    private Double getOrAddMapping(int columnNo, String e) {
        Map<String, Double> columnMapping = columnsMapping.get(columnNo);
        Map<Double, String> reverseMapping = reverseMappings.get(columnNo);
        if (columnMapping.containsKey(e)) {
            return columnMapping.get(e);
        }
        columnsMax.put(columnNo, columnsMax.get(columnNo) + 1.0);
        columnMapping.put(e, columnsMax.get(columnNo));
        reverseMapping.put(columnsMax.get(columnNo), e);
        return columnsMax.get(columnNo);
    }
}
