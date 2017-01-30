package krzych.learningstrategy;

import krzych.inmemorydata.Model;
import krzych.inmemorydata.Point;

/**
 * Created by krzych on 29.01.17.
 */
public interface LearningStrategy {
    Double cost(final Point p, final Double y, final Model m);
    Double derivative(final Point p, final Double y, final Model m);
    default Double g(final Double v) {
        return v;
    };
    default Double val(final Double v) {
        return v;
    }
}
