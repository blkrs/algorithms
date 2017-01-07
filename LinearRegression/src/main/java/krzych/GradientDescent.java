package krzych;

import java.util.List;

/**
 * Created by krzych on 28.12.16.
 */
public class GradientDescent implements LinearRegressionSolver {

    public double costFunctionThreshold = 0.0000000001;
    private Double alpha = 0.1;
    final double CONTROL_TEST_FACTOR = 0.2;
    private int startTrainingSet;
    private int endTrainingSet;
    private int startControlSet;
    private int endControlSet;
    private CsvData data;
    private Model theta;
    private DatasetNormalizer datasetNormalizer;

    public Model solve(CsvData data, Double alpha, Double costFunctionThreshold) {
        this.data =data;
        this.alpha = alpha;
        this.costFunctionThreshold = costFunctionThreshold;
        return solve(data);
    }

    public Model solve(CsvData data) {
        this.data = data;
        data.shuffle();
        datasetNormalizer = new DatasetNormalizer(data);
        datasetNormalizer.featureScaling(false);


        Integer height = data.getX().size();
        Integer width = data.getX().get(0).size();

        startTrainingSet = 0;
        endTrainingSet = (int)( (height - 1) * (1.0-CONTROL_TEST_FACTOR));
        startControlSet = endTrainingSet + 1;
        endControlSet = height - 1;

        System.out.println("Height = " + height + " " + width);
        System.out.println("End training set: " + endTrainingSet);

        return gradientDescent();
    }

    private void printTheta() {
        for (int i = 0; i < theta.getTheta().size(); ++i) {
            System.out.print("theta" + i + " = "  +theta.getTheta().get(i) + " ,");
        }
        System.out.println();
    }

    public Double validateControlSet(Model theta) {
        System.out.println("Validating control set");
        return validateRange(theta, startControlSet, endControlSet);
    }

    public Double validateTrainigSet(Model theta) {
        System.out.println("Validating training set");
        return validateRange(theta, startTrainingSet, endTrainingSet);
    }

    private Double validateRange(Model theta, int start, int end) {
        Double maxError = 0.0;
        for (int row = start; row < end; ++row) {
            Double computedY = multiply(data.getX().get(row), theta);
            Double originalY = data.getY().get(row);
            Double descaledComputedY = datasetNormalizer.invertScaleY(computedY);
            Double descaledOriginalY = datasetNormalizer.invertScaleY(originalY);
            Double scoredY = theta.applyScaled(data.getX().get(row));
            Double diff = computedY - originalY;
            Double error = Math.abs (diff/ descaledOriginalY);
            if (error > maxError) maxError = error;
            System.out.println(" get: " + descaledComputedY +" expected: "
                    + descaledOriginalY
                    + " error: " + error + " scored Y: " + scoredY);
        }
        return maxError;
    }

    private Model gradientDescent() {
        int features = data.getX().get(0).size();
        theta = new Model();
        for (int i = 0;i < features;++i) {
            theta.getTheta().add(0.0);
        }
        double cost = 10000000;
        double previousCost;
        do {
            previousCost = cost;
            cost = htheta();
            adjustTheta();
        } while (previousCost - cost > costFunctionThreshold);
        printTheta();
        theta.setNormalizer(datasetNormalizer);
        return theta;
    }

    private Double htheta() {
        Double totalCost = 0.0;
        for (int row = 0; row < endTrainingSet; ++row) {
            totalCost += Math.pow(multiply(data.getX().get(row), theta) - data.getY().get(row),2)/endTrainingSet;
        }
        return totalCost/2;
    }

    private void adjustTheta() {
        for (int column = 0; column < theta.getTheta().size(); ++column) {
            Double temp = 0.0;
            for (int row = 0; row < endTrainingSet; ++row) {
                temp += (multiply(data.getX().get(row), theta) - data.getY().get(row)) * data.getX().get(row).get(column) / endTrainingSet;
            }
            theta.getTheta().set(column, theta.getTheta().get(column) - alpha * temp);
        }
    }

    private Double multiply(Point p, Model theta) {
        Double t = 0.0;
        for (int i = 0; i < theta.getTheta().size(); ++i) {
            t+= theta.getTheta().get(i) * p.get(i);
        }
        return t;
    }

}
