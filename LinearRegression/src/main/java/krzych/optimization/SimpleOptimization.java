package krzych.optimization;

/**
 * Created by krzych on 30.01.17.
 */
public class SimpleOptimization implements Optimization {

    private int counter = 0;
    private double previousCost = 100000000;

    @Override
    public Double optimizeAlpha(final Double alpha, final Double cost) {
        if (counter == 100) {
            counter = 0;
            previousCost = cost;
            return alpha*1.5;
        }
        counter ++;
        if (cost > previousCost) {
            counter = 0;
            previousCost = cost;
            return alpha/2;
        }
        return alpha;
    }
}
