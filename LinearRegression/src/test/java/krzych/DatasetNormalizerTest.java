package krzych;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class DatasetNormalizerTest
    extends TestCase
{
    final static Logger log = Logger.getLogger(DatasetNormalizerTest.class);
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DatasetNormalizerTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        TestSuite allTests =  new TestSuite( DatasetNormalizerTest.class );
        return allTests;
    }

    /**
     * Rigourous Test :-)
     */
    public void testIfNormalizedDatainRange01() throws IOException {
        InMemoryListDataSet squareData = DataSetFactory.readFile("src/test/resources/square.csv");
        squareData.print();
        DatasetNormalizer normalizer = new DatasetNormalizer(squareData);
        normalizer.scalingFeatures();
        for ( Point p : squareData.getFeaturesX() ) {
            for (int i = 0;i < p.size(); ++i) {
                Double v = p.get(i);
                assertTrue(v >= 0);
                assertTrue(v <= 1);
            }
        }
    }

    public void testScaleAndScaleBack() throws IOException {
        InMemoryListDataSet squareData = DataSetFactory.readFile("src/test/resources/square.csv");
        DatasetNormalizer normalizer = new DatasetNormalizer(squareData);


        for ( Double v : Arrays.asList(0.1, 5.0, 10.0, -10. -20, 100000.0, 10000001.0) ){
            int column = squareData.getWidth() - 1;
            Double scaledNumber = normalizer.numberScale(column, v);
            assertTrue(almostEqual(v, normalizer.invertNumberScale( column, scaledNumber )));
        }
    }

    public void testAlmostEqual() {
        assertTrue(almostEqual(0.1, 0.1));
        assertTrue(almostEqual(threshold/10, threshold/5));
        assertFalse(almostEqual(0.1, 1.0));
        assertFalse(almostEqual(-0.1, 1.0));
        assertFalse(almostEqual(-10.0, -30.0));
        assertFalse(almostEqual(-1.0,1.0));
    }

    private Double threshold = 0.00000001;

    private Boolean almostEqual(Double a, Double b) {
        return (a  - b < threshold && a - b > (-1 * threshold));
    }

}
