package krzych;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krzych on 28.12.16.
 */
public class GradientDescent implements LinearRegressionSolver {

    public static final double COST_FUNCTION_THRESHOLD = 20.0;
    final double CONTROL_TEST_FACTOR = 0.1;
    int startTrainingSet;
    int endTrainingSet;
    int startControlSet;
    int endControlSet;
    List<Point> dataPoints;
    List<Double> theta;

    public void solve(CsvData data) {
        dataPoints = data.getPoints();
        featureScaling(data.getPoints());
        data.shuffle();

        Integer height = data.getPoints().size();
        Integer width = data.getPoints().get(0).getVector().size();

        startTrainingSet = 0;
        endTrainingSet = (int)( (height - 1) * (1.0-CONTROL_TEST_FACTOR));
        startControlSet = endTrainingSet + 1;
        endControlSet = height - 1;

        System.out.println("Height = " + height + " " + width);
        System.out.println("End training set: " + endTrainingSet);
        featureScaling();
        gradientDescent();

    }

    private void gradientDescent() {
        int features = dataPoints.get(0).getVector().size() - 1;
        theta = new ArrayList<Double>();
        for (int i = 0;i <= features;++i) {
            theta.add(0.0);
        }
        Double cost = 0.0;
        do {
            cost = computeCost();
            System.out.println("Cost function = " + cost);
            adjustTheta();
        } while (cost > COST_FUNCTION_THRESHOLD);

    }

    public void featureScaling() {
        int width = dataPoints.get(0).getVector().size();
        int height = dataPoints.size();
        Point minVals = new Point();
        Point maxVals = new Point();

        for (int i = 0;i < width; ++i) {
            maxVals.getVector()
                    .add(dataPoints.get(0).getVector().get(i));
            minVals.getVector()
                    .add(dataPoints.get(0).getVector().get(i));
        }

        for (int j = 1; j < height; ++j ) {
            for (int i = 0;i < width; ++i) {
                if (maxVals.getVector().get(i) < dataPoints.get(j).getVector().get(i)) {
                    maxVals.getVector().set(i,dataPoints.get(j).getVector().get(i));
                }
                if (minVals.getVector().get(i) > dataPoints.get(j).getVector().get(i)) {
                    minVals.getVector().set(i, dataPoints.get(j).getVector().get(i));
                }
            }
        }

        for (int j = 0; j < height; ++j ) {
            for (int i = 0;i < width; ++i) {
                Double x = dataPoints.get(j).getVector().get(i);
                Double xp = (x - minVals.getVector().get(i))/ (maxVals.getVector().get(i) - minVals.getVector().get(i));
                dataPoints.get(j).getVector().set(i, xp);
            }
        }

    }

}