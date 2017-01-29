package krzych;

import krzych.inmemorydata.Model;
import krzych.learningstrategy.LearningStrategy;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by krzych on 28.12.16.
 */
public class GradientDescent {

    private static final Logger log = Logger.getLogger(GradientDescent.class);
    private final double costFunctionThreshold;
    private final Double alpha;
    private final Double lambda;
    private final LearningStrategy learningStrategy;

    private Model theta;

    private DataSet data;

    public GradientDescent(Double alpha, Double lambda, Double gradientThreshold, LearningStrategy learningStrategy) {
        this.alpha = alpha;
        this.costFunctionThreshold = gradientThreshold;
        this.lambda = lambda;
        this.learningStrategy = learningStrategy;
    }

    public Model solve(DataSet data) {
        this.data = data;
        return gradientDescent();
    }

    private Model gradientDescent() {
        initTheta();
        double cost = 10000000;
        double previousCost;
        int counter = 0;
        do {
            previousCost = cost;
            cost = costFunction();
            descent();
            if (counter % 100 == 0) {
                log.info("Cost: " + cost);
            }
            counter++;
            if (previousCost < cost) {
                log.warn("XXXXXXXXXXXXXXXXXXXXXXXX Iteration " + counter + " Previous cost is lower, gradient is ascending");
            }
        } while (Math.abs(previousCost - cost) > costFunctionThreshold);
        return getModel();
    }

    private void descent() {
        List<Double> derivatives = data.computePartialDerivatives(theta,learningStrategy);
        for (int column = 0; column < theta.getTheta().size(); ++column) {
            Double derivative = derivatives.get(column);
            Double regularization = 0.0;
            if (column > 0)  {
                regularization = computeRegularization(column);
            }
            theta.getTheta().set(column, theta.getTheta().get(column) - alpha * (derivative + regularization));
        }
    }

    private Double computeRegularization(int column) {
        return lambda * theta.getTheta().get(column) / data.getHeight();
    }


    private Double costFunction() {
        Double totalCost = data.costFunction(theta, learningStrategy);
        if (lambda != 0) {
            totalCost += regularizationParameter();
        }
        return totalCost;
    }

    private Double regularizationParameter() {
        Double value = 0.0;
        for (int column = 1;column < theta.getTheta().size(); ++ column) {
            value += Math.pow(theta.getTheta().get(column), 2);
        }
        return lambda*value;
    }

    private Model getModel() {
        return theta;
    }

    private void initTheta() {
        int features = data.getWidth();
        theta = new Model();
        for (int i = 0;i < features;++i) {
            theta.getTheta().add(0.0);
        }
    }

}
