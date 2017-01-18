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
public class DatasetSplitterTest
    extends TestCase
{
    final static Logger log = Logger.getLogger(DatasetSplitterTest.class);
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DatasetSplitterTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        TestSuite allTests =  new TestSuite( DatasetSplitterTest.class );
        return allTests;
    }

    /**
     * Rigourous Test :-)
     */
    public void testSplitInHalf() throws IOException {
        CsvData originalData = CsvData.readFile("src/test/resources/linearyeqx.csv");
        log.info("Original dataset size before split: "  + originalData.getFeaturesX().size());
        CsvData secondHalf = DatasetSplitter.splitWithRatio(originalData,0.5);
        log.info("Original dataset size after split: "  + originalData.getFeaturesX().size());
        log.info("Created dataset size after split: "  + secondHalf.getFeaturesX().size());
        assertTrue(originalData.getFeaturesX().size() - secondHalf.getFeaturesX().size() <= 1);
    }

    public void testSplitLast20Percent() throws IOException {
        CsvData originalData = CsvData.readFile("src/test/resources/iris.csv");
        log.info("Original dataset size before split: "  +originalData.getFeaturesX().size());
        double ratio = 0.2;
        CsvData secondHalf = DatasetSplitter.splitWithRatio(originalData, ratio);
        log.info("Original dataset size after split: "  +originalData.getFeaturesX().size());
        log.info("Created dataset size after split: "  +secondHalf.getFeaturesX().size());
        double howManyTimesIsFirstPartBigger = (1 - ratio) / ratio;
        assertTrue(originalData.getFeaturesX().size() - secondHalf.getFeaturesX().size() * howManyTimesIsFirstPartBigger <= 1);
    }


}
