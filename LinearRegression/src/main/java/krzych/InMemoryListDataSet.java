package krzych;

import lombok.Data;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by krzych on 31.12.16.
 */
@Data
public class InMemoryListDataSet implements DataSet {
    private static final Logger log = Logger.getLogger(InMemoryListDataSet.class);

    private List<Point> featuresX;
    private List<Double> dependedVarsY;
    private ColumnsMapping columnsMapping;
    private int exponent = 1;

    InMemoryListDataSet() {
        featuresX = new ArrayList<>();
        dependedVarsY = new ArrayList<>();
    }

    @Override
    public int getHeight() {
        return featuresX.size();
    }

    @Override
    public int getWidth() {
        if (getHeight() == 0) {
            return 0;
        }
        return featuresX.get(0).size();
    }

    @Override
    public List<Double> computePartialDerivatives(final Model theta) {
        List<Double> derivatives = new ArrayList<>();
        for (int column = 0; column < getWidth(); ++column) {
            Double drv = 0.0;
            for (int row = 0; row < getHeight(); ++row) {
                drv += (multiply(featuresX.get(row), theta) - dependedVarsY.get(row))
                        * featuresX.get(row).get(column) / featuresX.size();
            }
            derivatives.add(drv);
        }
        return derivatives;
    }

    @Override
    public Double costFunction(final Model theta) {
        Double totalCost = 0.0;
        for (int row = 0; row < getHeight(); ++row)  {
            totalCost += Math.pow(multiply(featuresX.get(row), theta)
                    - dependedVarsY.get(row), 2) / featuresX.size();
        }
        totalCost /= 2;
        return totalCost;
    }

    @Override
    public Double scoreRow(Model theta, int row) {
        return theta.applyScaledWith1(featuresX.get(row));
    }

    @Override
    public Double getY(int row) {
        return dependedVarsY.get(row);
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

    @Override
    public void polynomialExpand(final int exponent) {
        featuresX.forEach(x ->
                x.expand(exponent)
        );
        this.exponent = exponent;
    }

    void addOnes() {
        featuresX.forEach(p
                -> p.add(0, 1.0)
        );
    }

    void print() {
        featuresX.forEach(Point::print);
    }

    public void printX() {
        featuresX.forEach(p ->
                p.printWithMapping(columnsMapping)
        );
    }

    private Double multiply(Point p, Model theta) {
        Double t = 0.0;
        for (int i = 0; i < theta.getTheta().size(); ++i) {
            t += theta.getTheta().get(i) * p.get(i);
        }
        return t;
    }

}
