package krzych;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by krzych on 28.12.16.
 */
@Data
public class Point {
    private List<Double> vector;
    public Point() {
        vector = new ArrayList<Double>();
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
        for (Double d: vector
             ) {
            System.out.print("[ " + d + " ]");
        }
        System.out.println("");
    }

    public void print(ColumnsMapping mapping) {
        int column = 0;
        for (Double d: vector
                ) {
            if (!mapping.isColumnMapped(column)) {
                System.out.print("[ " + d + " ]");
            } else {
                System.out.print("[ " + mapping.getDSMapping(column, d) + " ]");
            }
            column++;
        }
        System.out.println("");
    }
}
