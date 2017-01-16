package krzych;

/**
 * Created by krzych on 28.12.16.
 */
public abstract class LinearRegressionSolver {

    public abstract Model solve(CsvData data);

    protected abstract Model getModel();

    protected abstract void initTheta();

    protected abstract Double costFunction();

    protected abstract void adjustTheta();
}
