package krzych;

import lombok.Data;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krzych on 28.12.16.
 */
@Data
public class Point {
    private static final Logger log = Logger.getLogger(Point.class);

    private List<Double> vector;


    public Point() {
        vector = new ArrayList<>();
    }

    public Double get(int i) {
        return vector.get(i);
    }

    public void add(Double v) {
        vector.add(v);
    }

    public int size() {
        return vector.size();
    }

    public void set(int i, Double v) {
        vector.set(i, v);
    }

    public void print() {
        StringBuilder builder = new StringBuilder();
        for (Double d: vector
             ) {
            builder.append("[ " + d + " ]");
        }
        log.info(builder.toString());
    }

    public void print(ColumnsMapping mapping) {
        int column = 0;
        StringBuilder builder = new StringBuilder();
        for (Double d: vector
                ) {
            if (!mapping.isColumnMapped(column)) {
                builder.append("[ " + d + " ]");
            } else {
                builder.append("[ " + mapping.getDSMapping(column, d) + " ]");
            }
            column++;
        }
        log.info(builder.toString());
    }

    public void add(int i, double val) {
        vector.add(i, val);
    }
}
