package krzych;

import org.apache.log4j.Logger;

/**
 * Created by krzych on 28.12.16.
 */
public class GradientDescent {

    private static final Logger log = Logger.getLogger(GradientDescent.class);
    protected double costFunctionThreshold = 0.0000000001;

    private Double alpha = 0.1;
    private Model theta;

    private CsvData data;

    public Model solve(CsvData data, Double alpha, Double costFunctionThreshold) {
        this.data = data;
        this.alpha = alpha;
        this.costFunctionThreshold = costFunctionThreshold;
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
            if (counter == 0) {
                log.info("Cost: " + cost);
                counter = 10000;
            }
            counter--;
            if (previousCost < cost) {
                log.warn("XXXXXXXXXXXXXXXXXXXXXXXX Previous cost is lower, gradient is ascending");
            }
        } while (previousCost - cost > costFunctionThreshold);
        return getModel();
    }

    private void descent() {
        for (int column = 0; column < theta.getTheta().size(); ++column) {
            Double temp = 0.0;
            for (int row = 0; row < data.getFeaturesX().size(); ++row) {
                temp += (multiply(data.getFeaturesX().get(row), theta) - data.getDependedVarsY().get(row))
                        * data.getFeaturesX().get(row).get(column) / data.getFeaturesX().size();
            }
            theta.getTheta().set(column, theta.getTheta().get(column) - alpha * temp);
        }
    }

    private Double costFunction() {
        Double totalCost = 0.0;
        for (int row = 0; row < data.getFeaturesX().size(); ++row) {
            totalCost += Math.pow(multiply(data.getFeaturesX().get(row), theta) - data.getDependedVarsY().get(row),2)/data.getFeaturesX().size();
        }
        return totalCost/2;
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
