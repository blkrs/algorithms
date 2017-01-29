package krzych.inmemorydata;

import krzych.ColumnsMapping;
import krzych.DataSet;
import krzych.learningstrategy.LearningStrategy;
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

    public InMemoryListDataSet() {
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
    public List<Double> computePartialDerivatives(final Model theta, LearningStrategy learningStrategy) {
        List<Double> derivatives = new ArrayList<>();
        for (int column = 0; column < getWidth(); ++column) {
            Double drv = 0.0;
            for (int row = 0; row < getHeight(); ++row) {
                drv += (learningStrategy.derivative(featuresX.get(row),
                                                    dependedVarsY.get(row),
                                                    theta))
                                                * featuresX.get(row).get(column)
                        / featuresX.size();

            }
            derivatives.add(drv);
        }
        return derivatives;
    }

    @Override
    public Double costFunction(final Model theta, LearningStrategy learningStrategy) {
        Double totalCost = 0.0;
        for (int row = 0; row < getHeight(); ++row)  {
            totalCost += learningStrategy.cost(
                         featuresX.get(row),
                         dependedVarsY.get(row), theta)
                        / featuresX.size();
        }
        totalCost /= 2;
        return totalCost;
    }

    @Override
    public Double scoreRow(final Model theta,final int row) {
        return theta.applyScaledWith1(featuresX.get(row));
    }

    @Override
    public Double getY(int row) {
        return dependedVarsY.get(row);
    }

    @Override
    public void shuffle() {
        long seed = 123452;
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

    @Override
    public void addOnes() {
        featuresX.forEach(p
                -> p.add(0, 1.0)
        );
    }

    public void print() {
        featuresX.forEach(Point::print);
    }

    public void printX() {
        featuresX.forEach(p ->
                p.printWithMapping(columnsMapping)
        );
    }



}
