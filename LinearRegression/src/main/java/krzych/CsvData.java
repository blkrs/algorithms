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

    CsvData() {
        featuresX = new ArrayList<>();
        dependedVarsY = new ArrayList<>();
    }

    int getHeight() {
        return featuresX.size();
    }

    int getWidth() {
        if (getHeight() == 0) {
            return 0;
        }
        return featuresX.get(0).size();
    }

    void shuffle() {
        long seed = 123452;
        Collections.shuffle(featuresX, new Random(seed));
        Collections.shuffle(dependedVarsY, new Random(seed));
    }

    public void shuffleRandom() {
        long seed = System.nanoTime();
        Collections.shuffle(featuresX, new Random(seed));
        Collections.shuffle(dependedVarsY, new Random(seed));
    }

    void addOnes() {
        featuresX.forEach(p
                -> p.add(0,1.0)
        );
    }

    void print() {
        featuresX.forEach(Point::print);
    }

    public void printX() {
        featuresX.forEach( p ->
            p.print(columnsMapping)
        );
    }

    void expand(int exponent) {
        featuresX.forEach( x ->
            x.expand(exponent)
        );
        this.exponent = exponent;
    }
}
