package krzych.inmemorydata;

import krzych.learningstrategy.LearningStrategy;
import lombok.Data;
import org.apache.log4j.Logger;

/**
 * Created by krzych on 06.01.17.
 */
@Data
public class Model {
    private static final Logger log = Logger.getLogger(Model.class);

    // we need to know the theta vector
    private Point theta;
    // and we want to know how to normalize input data
    private DatasetNormalizer normalizer;

    private LearningStrategy strategy;

    public Model() {
        theta = new Point();
    }

    public Double apply(Point p) {
        normalizer.scaleXVector(p);
        return strategy.val(applyScaled(p));
    }

    public Double applyScaledWith1(Point p) {
        Double t = 0.0;
        for (int i = 0; i < theta.size(); ++i) {
            t+= theta.get(i) * p.get(i);
        }
        t = strategy.g(t);
        return strategy.val(normalizer.invertScaleY(t));
    }

    public void print() {
        log.info(this.toString());
    }

    private Double applyScaled(Point p) {
        p.add(0,1);
        return applyScaledWith1(p);
    }


}
