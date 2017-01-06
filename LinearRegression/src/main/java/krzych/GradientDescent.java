package krzych;

import java.util.List;

/**
 * Created by krzych on 28.12.16.
 */
public class GradientDescent implements LinearRegressionSolver {

    public double costFunctionThreshold = 0.000000001;
    private Double alpha = 0.1;
    final double CONTROL_TEST_FACTOR = 0.2;
    private int startTrainingSet;
    private int endTrainingSet;
    private int startControlSet;
    private int endControlSet;
    private int  y_index;
    private List<Point> dataPoints;
    private Model theta;
    private DatasetNormalizer datasetNormalizer;

    public Model solve(CsvData data, Double alpha, Double costFunctionThreshold) {
        this.alpha = alpha;
        this.costFunctionThreshold = costFunctionThreshold;
        return solve(data);
    }

    public Model solve(CsvData data) {
        dataPoints = data.getDataPoints();
        data.shuffle();
        datasetNormalizer = new DatasetNormalizer(data);
        datasetNormalizer.featureScaling(false);

        Integer height = data.getDataPoints().size();
        Integer width = data.getDataPoints().get(0).getVector().size();

        startTrainingSet = 0;
        endTrainingSet = (int)( (height - 1) * (1.0-CONTROL_TEST_FACTOR));
        startControlSet = endTrainingSet + 1;
        endControlSet = height - 1;
        y_index = width - 1;

        System.out.println("Height = " + height + " " + width);
        System.out.println("End training set: " + endTrainingSet);

        return gradientDescent();
    }

    private void printTheta() {
        for (int i =0; i < theta.getPoints().size(); ++i) {
            System.out.print("theta" + i + " = "  +theta.getPoints().get(i) + " ,");
        }
        System.out.println();
    }

    public Double validateControlSet(Model theta) {
        System.out.println("Validating control set");
        return validateRange(theta, startControlSet, endControlSet);
    }

    public Double validateTrainigSet(Model theta) {
        System.out.println("Validating control set");
        return validateRange(theta, startTrainingSet, endTrainingSet);
    }

    private Double validateRange(Model theta, int start, int end) {
        Double maxError = 0.0;
        Double yValueRange = datasetNormalizer.getRange(y_index);
        for (int j = start; j < end; ++j) {
            Double computedY = multiply(dataPoints.get(j), theta);
            Double originalY = dataPoints.get(j).getVector().get(y_index);
            Double descaledComputedY = datasetNormalizer.invertNumberScale(y_index, computedY);
            Double descaledOriginalY = datasetNormalizer.invertNumberScale(y_index, originalY);
            Double diff= computedY - originalY;
            Double error = Math.abs (diff/ yValueRange);
            if (error > maxError) maxError = error;
            System.out.println(" Get: " + descaledComputedY +" expected: "
                    + descaledOriginalY
                    + " error: " + error);
        }
        return maxError;
    }

    private Model gradientDescent() {
        int features = dataPoints.get(0).getVector().size() - 1;
        theta = new Model();
        for (int i = 0;i <= features;++i) {
            theta.getPoints().add(0.0);
        }
        double cost;
        do {
            cost = htheta();
            //System.out.println("XXXXXXXXXXXXXX Cost function = " + cost);
            adjustTheta();
        } while (cost > costFunctionThreshold);
        printTheta();
        return theta;
    }

    private Double htheta() {
        Double totalCost = 0.0;
        for (int j = 0; j < endTrainingSet; ++j) {
            totalCost += Math.pow(multiply(dataPoints.get(j), theta) - dataPoints.get(j).getVector().get(y_index),2)/endTrainingSet;
        }
        return totalCost/2;
    }

    private void adjustTheta() {
        for (int i =0; i < theta.getPoints().size(); ++i) {
            Double temp = 0.0;
            for (int j = 0; j < endTrainingSet; ++j) {
                temp += (multiply(dataPoints.get(j), theta) - dataPoints.get(j).getVector().get(y_index)) * dataPoints.get(j).getVector().get(i) / endTrainingSet;
            }
            theta.getPoints().set(i, theta.getPoints().get(i) - alpha * temp);
        }
    }

    private Double multiply(Point p, Model theta) {
        Double t = 0.0;
        for (int i =0; i < theta.getPoints().size(); ++i) {
            t+= theta.getPoints().get(i) * p.getVector().get(i);
        }
        return t;
    }

}
