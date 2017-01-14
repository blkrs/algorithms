package krzych;

/**
 * Created by krzych on 28.12.16.
 */
public abstract class LinearRegressionSolver {

    protected double costFunctionThreshold = 0.0000000001;

    public abstract Model solve(CsvData data);

    public Model gradientDescent() {
        initTheta();
        double cost = 10000000;
        double previousCost;
        do {
            previousCost = cost;
            cost = costFunction();
            adjustTheta();
        } while (previousCost - cost > costFunctionThreshold);
        return getModel();
    }

    protected abstract Model getModel();

    protected abstract void initTheta();

    protected abstract Double costFunction();

    protected abstract void adjustTheta();
}
