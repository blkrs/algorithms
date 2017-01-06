package krzych;

import lombok.Data;

/**
 * Created by krzych on 06.01.17.
 */
@Data
public class Model {

    // we want to know the how theta
    private Point theta;
    // and
    private DatasetNormalizer normalizer;

    public Model() {
        theta = new Point();
    }

    public Double apply(Point p) {
        normalizer.scaleXVector(p);
        return applyScaled(p);
    }

    public Double applyScaled(Point p) {
        Double t = 0.0;
        for (int i = 0; i < theta.size(); ++i) {
            t+= theta.get(i) * p.getVector().get(i);
        }
        return normalizer.invertScaleY(t);
    }

    public void print() {
        System.out.println("Model size: " + theta.size());
        theta.print();
    }
}
