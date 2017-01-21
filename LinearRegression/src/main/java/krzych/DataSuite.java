package krzych;

import java.io.IOException;

/**
 * Created by krzych on 20.01.17.
 */
public class DataSuite {
    public final CsvData trainingSet;
    public final CsvData controlSet;
    public final DatasetNormalizer normalizer;

    private DataSuite(CsvData trainingSet, CsvData controlSet, DatasetNormalizer normalizer) {
        this.trainingSet = trainingSet;
        this.controlSet = controlSet;
        this.normalizer = normalizer;
    }

    public static DataSuite readFile(String file, int exponent) throws IOException {
        CsvData trainingSet = CsvData.readFile(file);
        trainingSet.expand(exponent);
        DatasetNormalizer normalizer = new DatasetNormalizer(trainingSet);
        normalizer.scalingFeatures();
        trainingSet.shuffle();
        trainingSet.addOnes();
        CsvData controlSet = DatasetSplitter.splitWithRatio(trainingSet, 0.2);
        return new DataSuite(trainingSet, controlSet, normalizer);
    }

    public static DataSuite readFile(String file) throws IOException {
        return readFile(file, 1);
    }
}
