package krzych;

import java.util.List;

/**
 * Created by krzych on 28.12.16.
 */
public interface LinearRegressionSolver {
    Model solve(CsvData data);
}
