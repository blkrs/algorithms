package krzych.learningstrategy;

import krzych.inmemorydata.Model;
import krzych.inmemorydata.Point;

/**
 * Created by krzych on 29.01.17.
 */
public class LinearRegressionStrategy implements LearningStrategy {

    @Override
    public Double cost(Point x, Double y, Model m) {
        return Math.pow(multiply(x, m)
                - y, 2);
    }

    @Override
    public Double derivative(Point p, Double y, Model m) {
        return (multiply(p, m) - y);
    }


    private Double multiply(Point p, Model theta) {
        Double t = 0.0;
        for (int i = 0; i < theta.getTheta().size(); ++i) {
            t += theta.getTheta().get(i) * p.get(i);
        }
        return t;
    }
}
