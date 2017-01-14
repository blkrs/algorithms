package krzych;

import lombok.Data;
import org.apache.log4j.Logger;

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
    private static final Logger log = Logger.getLogger(CsvData.class);

    private List<Point> featuresX;
    private List<Double> dependedVarsY;
    private ColumnsMapping columnsMapping;
    private int width = 0;
    private int height = 0;


    public CsvData() {
        featuresX = new ArrayList<>();
        dependedVarsY = new ArrayList<>();
    }

    public void shuffle() {
        long seed = 123452;
        Collections.shuffle(featuresX, new Random(seed));
        Collections.shuffle(dependedVarsY, new Random(seed));
    }

    public void shuffleRandom() {
        long seed = System.nanoTime();
        Collections.shuffle(featuresX, new Random(seed));
        Collections.shuffle(dependedVarsY, new Random(seed));
    }

    public void addOnes() {
        for (Point x: featuresX) {
            x.add(0,1.0);
        }
    }

    public void print() {
        for (Point p : featuresX
             ) {
             p.print();
        }
    }

    public void printX() {
        for (Point p : featuresX
                ) {
            p.print(columnsMapping);
        }
    }

    public static CsvData readFile(String filePath) throws IOException {
        ColumnsMapping columnsMapping = new ColumnsMapping();
        CsvData csvData = new CsvData();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;

            while ((line = br.readLine()) != null) {
                addRow(csvData, line, columnsMapping);
            }

            csvData.setColumnsMapping(columnsMapping);
            csvData.setHeight(csvData.getFeaturesX().size());
            if (csvData.getHeight() > 0) {
                csvData.setWidth(csvData.getFeaturesX().get(0).getVector().size());
            }
            return csvData;
        }
    }

    private static void addRow(CsvData csvData, String line, ColumnsMapping columnsMapping) {
        String[] entries = line.split(",");
        Point p = new Point();
        int yIndex = entries.length - 1;
        for (int columnNo = 0; columnNo < yIndex; ++columnNo) {
            p.add(getaDouble(entries, columnNo, columnsMapping));
        }
        csvData.getFeaturesX().add(p);
        csvData.getDependedVarsY().add(getaDouble(entries, yIndex, columnsMapping));
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
