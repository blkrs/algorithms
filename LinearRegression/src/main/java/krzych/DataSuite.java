package krzych;

import java.io.IOException;

/**
 * Created by krzych on 20.01.17.
 */
public class DataSuite {
    public final DataSet trainingSet;
    public final DataSet controlSet;
    public final DatasetNormalizer normalizer;

    private DataSuite(InMemoryListDataSet trainingSet, InMemoryListDataSet controlSet, DatasetNormalizer normalizer) {
        this.trainingSet = trainingSet;
        this.controlSet = controlSet;
        this.normalizer = normalizer;
    }

    public static DataSuite readFile(String file, int exponent) throws IOException {
        InMemoryListDataSet trainingSet = DataSetFactory.readFile(file);
        trainingSet.polynomialExpand(exponent);
        DatasetNormalizer normalizer = new DatasetNormalizer(trainingSet);
        normalizer.scalingFeatures();
        trainingSet.shuffle();
        trainingSet.addOnes();
        InMemoryListDataSet controlSet = MemoryDatasetSplitter.splitWithRatio(trainingSet, 0.2);
        return new DataSuite(trainingSet, controlSet, normalizer);
    }

    public static DataSuite readFile(String file) throws IOException {
        return readFile(file, 1);
    }
}
