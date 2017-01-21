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
    private int exponent = 1;


    public int getHeight() {
        return featuresX.size();
    }

    public int getWidth() {
        if (getHeight() == 0) return 0;
        return featuresX.get(0).getVector().size();
    }

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
        featuresX.forEach(p -> {
                    p.add(0,1.0);
                }
        );
    }

    public void print() {
        featuresX.forEach(p -> {
            p.print();
        });
    }

    public void printX() {
        featuresX.forEach( p -> {
            p.print(columnsMapping);
            }
        );
    }

    public void expand(int exponent) {
        featuresX.forEach( x ->  {
            x.expand(exponent);
            }
        );
        this.exponent = exponent;
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
