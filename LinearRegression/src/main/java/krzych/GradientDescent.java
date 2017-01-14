package krzych;

import org.apache.log4j.Logger;

/**
 * Created by krzych on 28.12.16.
 */
public class GradientDescent extends LinearRegressionSolver {

    private static final Logger log = Logger.getLogger(GradientDescent.class);

    private Double alpha = 0.1;
    private final double controlTestRatio = 0.2;
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

    @Override
    public Model solve(CsvData data) {
        this.data = data;
        data.shuffle();

        datasetNormalizer = new DatasetNormalizer(data);
        datasetNormalizer.featureScaling(false);

        data.addOnes();


        Integer height = data.getFeaturesX().size();
        Integer width = data.getFeaturesX().get(0).size();

        startTrainingSet = 0;
        endTrainingSet = (int)( (height - 1) * (1.0- controlTestRatio));
        startControlSet = endTrainingSet + 1;
        endControlSet = height - 1;

        log.info("Height = " + height + " " + width);
        log.info("End training set: " + endTrainingSet);

        return gradientDescent();
    }

    private void printTheta() {
        log.info("theta " + theta.toString());
    }

    Double validateControlSet(Model theta) {
        log.info("Validating control set");
        return validateRange(theta, startControlSet, endControlSet);
    }

    Double validateTrainigSet(Model theta) {
        log.info("Validating training set");
        return validateRange(theta, startTrainingSet, endTrainingSet);
    }

    private Double validateRange(Model theta, int start, int end) {
        Double maxError = 0.0;
        for (int row = start; row < end; ++row) {
            Double scoredY = theta.applyScaledWith1(data.getFeaturesX().get(row));
            Double originalY = data.getDependedVarsY().get(row);
            Double descaledComputedY = datasetNormalizer.invertScaleY(scoredY);
            Double descaledOriginalY = datasetNormalizer.invertScaleY(originalY);
            log.info("SCoredY " + scoredY + " descaled Computed Y " + descaledComputedY);
            Double diff = scoredY - originalY;
            Double error = Math.abs (diff/ descaledOriginalY);
            if (error > maxError)
                maxError = error;
        }
        return maxError;
    }

    @Override
    protected Model getModel() {
        theta.setNormalizer(datasetNormalizer);
        return theta;
    }

    @Override
    protected void initTheta() {
        int features = data.getFeaturesX().get(0).size();
        theta = new Model();
        for (int i = 0;i < features;++i) {
            theta.getTheta().add(0.0);
        }
    }

    @Override
    protected Double costFunction() {
        Double totalCost = 0.0;
        for (int row = 0; row < endTrainingSet; ++row) {
            totalCost += Math.pow(multiply(data.getFeaturesX().get(row), theta) - data.getDependedVarsY().get(row),2)/endTrainingSet;
        }
        return totalCost/2;
    }

    @Override
    protected void adjustTheta() {
        for (int column = 0; column < theta.getTheta().size(); ++column) {
            Double temp = 0.0;
            for (int row = 0; row < endTrainingSet; ++row) {
                temp += (multiply(data.getFeaturesX().get(row), theta) - data.getDependedVarsY().get(row)) * data.getFeaturesX().get(row).get(column) / endTrainingSet;
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
