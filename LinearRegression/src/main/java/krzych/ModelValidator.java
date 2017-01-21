package krzych;

import org.apache.log4j.Logger;

/**
 * Created by krzych on 17.01.17.
 */
public class ModelValidator {
    private static final Logger log = Logger.getLogger(GradientDescent.class);

    private CsvData data;

    ModelValidator(CsvData data) {
        this.data = data;
    }

    Double validateSet(Model theta) {
        log.info("Validating control set");
        return validateRange(theta, 0,  data.getHeight());
    }

    private Double validateRange(Model model, int start, int end) {
        Double maxError = 0.0;
        for (int row = start; row < end; ++row) {
            Double scoredY = model.applyScaledWith1(data.getFeaturesX().get(row));
            Double originalY = data.getDependedVarsY().get(row);
            Double descaledOriginalY = model.getNormalizer().invertScaleY(originalY);
            log.info("Descaled SCoredY " + scoredY+ " descaled Original Y " + descaledOriginalY);
            Double diff = scoredY - descaledOriginalY;
            Double error = Math.abs (diff/ descaledOriginalY);
            if (error > maxError)
                maxError = error;
        }
        return maxError;
    }
}
