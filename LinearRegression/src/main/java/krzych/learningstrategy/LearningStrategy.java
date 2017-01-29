package krzych.learningstrategy;

import krzych.inmemorydata.Model;
import krzych.inmemorydata.Point;

/**
 * Created by krzych on 29.01.17.
 */
public interface LearningStrategy {
    Double cost(Point p, Double y, Model m);
    Double derivative(Point p, Double y, Model m);
}
