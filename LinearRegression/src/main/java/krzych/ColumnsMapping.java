package krzych;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by krzych on 31.12.16.
 */
public class ColumnsMapping {
    private static final Logger log = Logger.getLogger(ColumnsMapping.class);

    private Map<Integer, Map<String,Double>> mappings = new HashMap<>();
    private Map<Integer, Map<Double,String>> reverseMappings = new HashMap<>();
    private Map<Integer, Double> columnsMax = new HashMap<>();

    Double getSDMapping(String e, int columnNo) {
        if (!isColumnMapped(columnNo)) {
            initRow(columnNo);
        }
        return getOrAddMapping(columnNo, e);
    }

    String getDSMapping(int columnNo, Double key) {
        if (isColumnMapped(columnNo)) {
            return reverseMappings.get(columnNo).get(key);
        }
        return null;
    }

    boolean isColumnMapped(int columnNo) {
        return columnsMax.containsKey(columnNo);
    }

    private void initRow(int columnNo) {
        log.info("Initialize mapping of row " +  columnNo);
        columnsMax.put(columnNo, 0.0);
        mappings.put(columnNo, new HashMap<>());
        reverseMappings.put(columnNo, new HashMap<>());
    }

    private Double getOrAddMapping(int columnNo, String e) {
        Map<String, Double> columnMapping = mappings.get(columnNo);
        Map<Double, String> reverseMapping = reverseMappings.get(columnNo);
        if (columnMapping.containsKey(e)) {
            return columnMapping.get(e);
        }
        // increase max value
        columnsMax.put(columnNo, columnsMax.get(columnNo) + 1.0);
        log.info("Adding mapping of " + e + " with value " + columnsMax.get(columnNo) );
        columnMapping.put(e, columnsMax.get(columnNo));
        reverseMapping.put(columnsMax.get(columnNo), e);
        return columnsMax.get(columnNo);
    }
}
