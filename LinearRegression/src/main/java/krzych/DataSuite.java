package krzych;

import krzych.inmemorydata.DatasetNormalizer;
import krzych.inmemorydata.InMemoryListDataSet;
import krzych.inmemorydata.MemoryDatasetSplitter;

import java.io.IOException;

/**
 * Created by krzych on 20.01.17.
 */
public class DataSuite {
    public final DataSet trainingSet;
    public final DataSet controlSet;
    public final DatasetNormalizer normalizer;

    private DataSuite(DataSet trainingSet, DataSet controlSet, DatasetNormalizer normalizer) {
        this.trainingSet = trainingSet;
        this.controlSet = controlSet;
        this.normalizer = normalizer;
    }

    public static DataSuite readFile(String file, int exponent) throws IOException {
        DataSet trainingSet = DataSetFactory.readFile(file);
        trainingSet.polynomialExpand(exponent);
        DatasetNormalizer normalizer = new DatasetNormalizer((InMemoryListDataSet)trainingSet);
        normalizer.scalingFeatures();
        trainingSet.shuffle();
        trainingSet.addOnes();
        InMemoryListDataSet controlSet = MemoryDatasetSplitter.splitWithRatio((InMemoryListDataSet)trainingSet, 0.2);
        return new DataSuite(trainingSet, controlSet, normalizer);
    }

    public static DataSuite readFile(String file) throws IOException {
        return readFile(file, 1);
    }
}
