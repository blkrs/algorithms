package krzych;

import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by krzych on 31.12.16.
 */
@Data
public class CsvData {

    private List<Point> dataPoints;
    private List<String> header;
    private ColumnsMapping columnsMapping;
    private int width = 0;
    private int height = 0;


    public CsvData() {
        dataPoints = new ArrayList<Point>();
        header = new ArrayList<String>();
    }

    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(dataPoints, new Random(seed));
    }

    public void print() {
        for (Point p : dataPoints
             ) {
             p.print();
        }
    }

    public void printX() {
        for (Point p : dataPoints
                ) {
            p.print(columnsMapping);
        }
    }


    public static CsvData readFile(String filePath) throws IOException {
        ColumnsMapping columnsMapping = new ColumnsMapping();
        CsvData csvData = new CsvData();
        BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
        String line;

        while ((line = br.readLine()) != null) {
            addRow(csvData, line, columnsMapping);
        }

        csvData.setColumnsMapping(columnsMapping);
        csvData.setHeight(csvData.getDataPoints().size());
        if (csvData.getHeight() > 0) {
            csvData.setWidth(csvData.getDataPoints().get(0).getVector().size());
        }
        return csvData;
    }

    private static void addRow(CsvData csvData, String line, ColumnsMapping columnsMapping) {
        String[] entries = line.split(",");
        Point p = new Point();
        for (int columnNo = 0; columnNo < entries.length; ++columnNo) {
            Double value = getaDouble(entries, columnNo, columnsMapping);
            p.getVector().add(value);
        }
        csvData.getDataPoints().add(p);
    }

    private static Double getaDouble(String entries[],int columnNo, ColumnsMapping columnsMapping) {
        String e = entries[columnNo];
        try {
            return Double.valueOf(e);
        } catch (NumberFormatException ex) {
            // text value found - we have to create mapping
            return columnsMapping.getSDMapping(e, columnNo);
        }
    }


}
