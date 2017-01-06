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

    private List<Point> X;
    private List<Double> Y;
    private ColumnsMapping columnsMapping;
    private int width = 0;
    private int height = 0;


    public CsvData() {
        X = new ArrayList<Point>();
        Y = new ArrayList<Double>();
    }

    public void shuffle() {
        long seed = System.nanoTime();
        Collections.shuffle(X, new Random(seed));
        Collections.shuffle(Y, new Random(seed));
    }

    public void addOnes() {
        for (int row = 0; row < height; ++row) {

        }
    }

    public void print() {
        for (Point p : X
             ) {
             p.print();
        }
    }

    public void printX() {
        for (Point p : X
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
        csvData.setHeight(csvData.getX().size());
        if (csvData.getHeight() > 0) {
            csvData.setWidth(csvData.getX().get(0).getVector().size());
        }
        return csvData;
    }

    private static void addRow(CsvData csvData, String line, ColumnsMapping columnsMapping) {
        String[] entries = line.split(",");
        Point p = new Point();
        int yIndex = entries.length - 1;
        for (int columnNo = 0; columnNo < yIndex; ++columnNo) {
            p.add(getaDouble(entries, columnNo, columnsMapping));
        }
        csvData.getX().add(p);
        csvData.getY().add(getaDouble(entries, yIndex, columnsMapping));
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
