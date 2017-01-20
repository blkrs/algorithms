package krzych;

import java.io.IOException;

/**
 * Created by krzych on 20.01.17.
 */
public class DataSuite {
    public CsvData trainingSet;
    public CsvData controlSet;
    public DatasetNormalizer normalizer;

    public static DataSuite readFile(String file) throws IOException {
        DataSuite suite = new DataSuite();
        suite.trainingSet = CsvData.readFile(file);
        suite.normalizer = new DatasetNormalizer(suite.trainingSet);
        suite.normalizer.scalingFeatures();
        suite.trainingSet.shuffle();
        suite.trainingSet.addOnes();
        suite.controlSet = DatasetSplitter.splitWithRatio(suite.trainingSet, 0.2);
        GradientDescent gd = new GradientDescent();
        Model model = gd.solve(suite.trainingSet);
        model.setNormalizer(suite.normalizer);
        return suite;
    }
}
