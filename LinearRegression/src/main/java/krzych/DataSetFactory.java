package krzych;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by krzych on 22.01.17.
 */
public class DataSetFactory {

    private DataSetFactory() {
    }

    static InMemoryListDataSet readFile(String filePath) throws IOException {
        ColumnsMapping columnsMapping = new ColumnsMapping();
        InMemoryListDataSet dataSet = new InMemoryListDataSet();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;

            while ((line = br.readLine()) != null) {
                addRow(dataSet, line, columnsMapping);
            }
            dataSet.setColumnsMapping(columnsMapping);
            return dataSet;
        }
    }

    private static void addRow(InMemoryListDataSet dataSet, String line, ColumnsMapping columnsMapping) {
        String[] entries = line.split(",");
        Point p = new Point();
        int yIndex = entries.length - 1;
        for (int columnNo = 0; columnNo < yIndex; ++columnNo) {
            p.add(getaDouble(entries, columnNo, columnsMapping));
        }
        dataSet.getFeaturesX().add(p);
        dataSet.getDependedVarsY().add(getaDouble(entries, yIndex, columnsMapping));
    }

    private static Double getaDouble(String[] entries,int columnNo, ColumnsMapping columnsMapping) {
        String e = entries[columnNo];
        try {
            return Double.valueOf(e);
        } catch (NumberFormatException ex) {
            // text value found - we have to create mapping text to double
            return columnsMapping.getSDMapping(e, columnNo);
        }
    }
}
