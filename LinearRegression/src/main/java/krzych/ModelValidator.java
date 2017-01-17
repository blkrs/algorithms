package krzych;

import org.apache.log4j.Logger;

/**
 * Created by krzych on 17.01.17.
 */
public class ModelValidator {
    private static final Logger log = Logger.getLogger(GradientDescent.class);
    private final double controlTestRatio = 0.2;
    private int startTrainingSet;
    private int endTrainingSet;
    private int startControlSet;
    private int endControlSet;

    private CsvData data;

    public ModelValidator(CsvData data) {

        this.data = data;
        Integer height = data.getFeaturesX().size();
        Integer width = data.getFeaturesX().get(0).size();
        startTrainingSet = 0;
        endTrainingSet = (int)( (height - 1) * (1.0- controlTestRatio));
        startControlSet = endTrainingSet + 1;
        endControlSet = height - 1;

        log.info("Height = " + height + " " + width);
        log.info("End training set: " + endTrainingSet);
    }

    Double validateControlSet(Model theta) {
        log.info("Validating control set");
        return validateRange(theta, startControlSet, endControlSet);
    }

    Double validateTrainigSet(Model theta) {
        log.info("Validating training set");
        return validateRange(theta, startTrainingSet, endTrainingSet);
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
