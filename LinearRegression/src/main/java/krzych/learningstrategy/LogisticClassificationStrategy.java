package krzych.learningstrategy;

import krzych.inmemorydata.Model;
import krzych.inmemorydata.Point;

/**
 * Created by krzych on 29.01.17.
 */
public class LogisticClassificationStrategy implements LearningStrategy {

    @Override
    public Double cost(Point x, Double y, Model m) {
        Double htheta = g(multiply(x,m));
        return - (y*Math.log(htheta) + (1-y)*Math.log(1 - htheta));
    }

    @Override
    public Double derivative(Point p, Double y, Model m) {
        return g(multiply(p, m)) - y;
    }

    // sigmoid function
    @Override
    public Double g(Double v) {
        return 1/(1 + Math.exp(-v));
    }

    @Override
    public Double val(Double v) {
        return v >= 0.5 ? 1.0 : 0.0;
    }

    private Double multiply(Point p, Model theta) {
        Double t = 0.0;
        for (int i = 0; i < theta.getTheta().size(); ++i) {
            t += theta.getTheta().get(i) * p.get(i);
        }
        return t;
    }
}
