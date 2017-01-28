package krzych;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class MemoryDatasetSplitterTest
    extends TestCase
{
    final static Logger log = Logger.getLogger(MemoryDatasetSplitterTest.class);
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MemoryDatasetSplitterTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        TestSuite allTests =  new TestSuite( MemoryDatasetSplitterTest.class );
        return allTests;
    }

    /**
     * Rigourous Test :-)
     */
    public void testSplitInHalf() throws IOException {
        InMemoryListDataSet originalData = DataSetFactory.readFile("src/test/resources/linearyeqx.csv");
        log.info("Original dataset size before split: "  + originalData.getFeaturesX().size());
        InMemoryListDataSet secondHalf = MemoryDatasetSplitter.splitWithRatio(originalData,0.5);
        log.info("Original dataset size after split: "  + originalData.getFeaturesX().size());
        log.info("Created dataset size after split: "  + secondHalf.getFeaturesX().size());
        assertTrue(originalData.getFeaturesX().size() - secondHalf.getFeaturesX().size() <= 1);
    }

    public void testSplitInRightProportion() throws IOException {
        InMemoryListDataSet originalData = DataSetFactory.readFile("src/test/resources/iris.csv");
        log.info("Original dataset size before split: "  +originalData.getFeaturesX().size());
        double ratio = 0.2;
        InMemoryListDataSet secondPart = MemoryDatasetSplitter.splitWithRatio(originalData, ratio);
        log.info("Original dataset size after split: "  +originalData.getFeaturesX().size());
        log.info("Created dataset size after split: "  +secondPart.getFeaturesX().size());
        double howManyTimesIsFirstPartBigger = (1 - ratio) / ratio;
        assertEquals((double)originalData.getHeight(), secondPart.getHeight() * howManyTimesIsFirstPartBigger);
        assertTrue(originalData.getFeaturesX().size() - secondPart.getFeaturesX().size() * howManyTimesIsFirstPartBigger <= 1);
    }

    public void testSplitHasEqualWidth() throws IOException {
        InMemoryListDataSet originalData = DataSetFactory.readFile("src/test/resources/iris.csv");
        log.info("Original dataset size before split: "  + originalData.getFeaturesX().size());
        double ratio = 0.2;
        InMemoryListDataSet secondPart = MemoryDatasetSplitter.splitWithRatio(originalData, ratio);
        log.info("Original dataset size after split: "  + originalData.getFeaturesX().size());
        log.info("Created dataset size after split: "  + secondPart.getFeaturesX().size());
        assertEquals(originalData.getFeaturesX().get(0).size(), secondPart.getFeaturesX().get(0).size());
    }


}
