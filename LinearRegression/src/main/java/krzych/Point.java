package krzych;

import lombok.Data;

import java.util.ArrayList;
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
