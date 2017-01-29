package krzych.inmemorydata;

import krzych.ColumnsMapping;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krzych on 28.12.16.
 */
public class Point {
    private static final Logger log = Logger.getLogger(Point.class);
    private List<Double> v;

    public Point() {
        v = new ArrayList<>();
    }

    public Double get(int i) {
        return v.get(i);
    }

    public void add(Double v) {
        this.v.add(v);
    }

    public int size() {
        return v.size();
    }

    public void set(int i, Double v) {
        this.v.set(i, v);
    }

    public void add(int i, double val) {
        v.add(i, val);
    }

    public void expand(int exponent) {
        int originalSize = v.size();
        for (int i = 0;i < originalSize;++i) {
            for (int exp = 2;exp <= exponent;exp++) {
                v.add(Math.pow(v.get(i),exp));
            }
        }
    }

    @Override
    public String toString() {
        return v.toString();
    }

    public void print() {
        StringBuilder builder = new StringBuilder();
        for (Double d: v
             ) {
            builder.append("[ ");
            builder.append(d);
            builder.append(" ]");
        }
        log.info(builder.toString());
    }

    public void printWithMapping(ColumnsMapping mapping) {
        int column = 0;
        StringBuilder builder = new StringBuilder();
        for (Double d: v
                ) {
            if (!mapping.isColumnMapped(column)) {
                builder.append("[ ");
                builder.append(d);
                builder.append(" ]");
            } else {
                builder.append("[ ");
                builder.append(mapping.getDSMapping(column, d));
                builder.append(" ]");
            }
            column++;
        }
        log.info(builder.toString());
    }

}
