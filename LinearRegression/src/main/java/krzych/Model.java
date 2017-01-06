package krzych;

import lombok.Data;

/**
 * Created by krzych on 06.01.17.
 */
@Data
public class Model {

    // we want to know the how theta
    private Point points;
    // and
    private DatasetNormalizer normalizer;

    public Model() {
        points = new Point();
    }

    public Double apply(Point p) {
        normalizer.scaleVector(p);
        Double t = 0.0;
        for (int i =0; i < points.size(); ++i) {
            t+= points.get(i) * p.getVector().get(i);
        }
        return normalizer.invertScaleY(t);
    }
}
