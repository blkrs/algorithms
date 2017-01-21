package krzych;

import org.apache.log4j.Logger;

/**
 * Created by krzych on 28.12.16.
 */
public class GradientDescent {

    private static final Logger log = Logger.getLogger(GradientDescent.class);
    protected double costFunctionThreshold = 0.0000000001;

    private Double alpha = 0.1;
    private Double lambda = 0.0;
    private Model theta;


    private CsvData data;

    public Model solve(CsvData data, Double alpha, Double costFunctionThreshold, Double lambda) {
        this.data = data;
        this.alpha = alpha;
        this.costFunctionThreshold = costFunctionThreshold;
        this.lambda = lambda;
        return solve(data);
    }

    public Model solve(CsvData data) {
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
        for (int column = 0; column < theta.getTheta().size(); ++column) {
            Double derivative = computePartialDerivative(column);
            Double regularization = 0.0;
            if (column > 0)  {
                regularization = computeRegularization(column);
            }
            theta.getTheta().set(column, theta.getTheta().get(column) - alpha * (derivative + regularization));
        }
    }

    private Double computeRegularization(int column) {
        return lambda * theta.getTheta().get(column) / data.getFeaturesX().size();
    }

    private Double computePartialDerivative(int column) {
        Double temp = 0.0;
        for (int row = 0; row < data.getFeaturesX().size(); ++row) {
            temp += (multiply(data.getFeaturesX().get(row), theta) - data.getDependedVarsY().get(row))
                    * data.getFeaturesX().get(row).get(column) / data.getFeaturesX().size();
        }
        return temp;
    }

    private Double costFunction() {
        Double totalCost = 0.0;
        for (int row = 0; row < data.getFeaturesX().size(); ++row) {
            totalCost += Math.pow(multiply(data.getFeaturesX().get(row), theta)
                    - data.getDependedVarsY().get(row),2)/data.getFeaturesX().size();
        }
        totalCost /= 2;
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

    private Double multiply(Point p, Model theta) {
        Double t = 0.0;
        for (int i = 0; i < theta.getTheta().size(); ++i) {
            t+= theta.getTheta().get(i) * p.get(i);
        }
        return t;
    }


    private Model getModel() {
        return theta;
    }

    private void initTheta() {
        int features = data.getFeaturesX().get(0).size();
        theta = new Model();
        for (int i = 0;i < features;++i) {
            theta.getTheta().add(0.0);
        }
    }


}
