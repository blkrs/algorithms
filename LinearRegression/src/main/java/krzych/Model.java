package krzych;

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
    // and
    private DatasetNormalizer normalizer;

    public Model() {
        theta = new Point();
    }

    Double apply(Point p) {
        normalizer.scaleXVector(p);
        return applyScaled(p);
    }

    Double applyScaled(Point p) {
        p.add(0,1);
        Double t = 0.0;
        for (int i = 0; i < theta.size(); ++i) {
            t+= theta.get(i) * p.getVector().get(i);
        }
        return normalizer.invertScaleY(t);
    }

    Double applyScaledWith1(Point p) {
        Double t = 0.0;
        for (int i = 0; i < theta.size(); ++i) {
            t+= theta.get(i) * p.getVector().get(i);
        }
        return normalizer.invertScaleY(t);
    }

    void print() {
        log.info(this.toString());
    }
}
