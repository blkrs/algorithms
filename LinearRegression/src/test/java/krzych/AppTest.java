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
public class AppTest
    extends TestCase
{
    final static Logger log = Logger.getLogger(AppTest.class);
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        TestSuite allTests =  new TestSuite( AppTest.class );
        allTests.addTest(DatasetNormalizerTest.suite());
        return allTests;
    }

    public void testGradientDescentIris() throws IOException {
        log.info("Iris dataset");
        CsvData data = CsvData.readFile("src/test/resources/iris.csv");
        data.shuffle();
        data.printX();
        GradientDescent gd = new GradientDescent();
        Model model = gd.solve(data);
        ModelValidator validator = new ModelValidator(data);
        Double errorCS = validator.validateControlSet(model);
        Double errorTS = validator.validateTrainigSet(model);
        log.info("Error CS = " + errorCS);
        log.info("Error TS = " + errorTS);
        model.print();
        double avgError = 0;
        int count = 50;
        for (int row = 0; row < count; ++row) {
            Double calculatedY = model.apply(data.getFeaturesX().get(row));
            Double originalY =  model.getNormalizer().invertScaleY(data.getDependedVarsY().get(row));
            log.info("Calculated y :" + calculatedY + " originalY: " + originalY);
            avgError += Math.abs(originalY - calculatedY) / originalY ;
            assertTrue(Math.abs(calculatedY
                    - originalY) < 1.2);
        }
        avgError/=count;
        log.info("AVG error: " + avgError);
        assertTrue(avgError < 0.174);
    }

    // in this test we want to make sure that if we round the answer to integer,
    // then we will have no errors
    public void testGradientDescentIrisRigorious() throws IOException {
        log.info("Iris dataset");
        CsvData data = CsvData.readFile("src/test/resources/iris.csv");
        data.shuffle();
        data.printX();
        GradientDescent gd = new GradientDescent();
        Model model = gd.solve(data);
        ModelValidator validator = new ModelValidator(data);
        Double errorCS = validator.validateControlSet(model);
        Double errorTS = validator.validateTrainigSet(model);
        log.info("Error CS = " + errorCS);
        log.info("Error TS = " + errorTS);
        model.print();
        for (int row = 0; row < 10; ++row) {
            Double calculatedY = model.apply(data.getFeaturesX().get(row));
            Double originalY =  model.getNormalizer().invertScaleY(data.getDependedVarsY().get(row));
            log.info("Calculated y :" + calculatedY + " originalY: " + originalY);
            assertTrue(Math.abs(calculatedY
                    - originalY) < 0.5);
        }
    }

    public void testGradientDecentLinear() throws IOException {
        log.info("y = x dataset");
        CsvData linearData = CsvData.readFile("src/test/resources/linearyeqx.csv");
        linearData.shuffle();
        GradientDescent gd = new GradientDescent();
        Model model = gd.solve(linearData);
        ModelValidator validator = new ModelValidator(linearData);
        Double errorCS = validator.validateControlSet(model);
        Double errorTS = validator.validateTrainigSet(model);
        log.info("Error CS = " + errorCS);
        log.info("Error TS = " + errorTS);
        for (Double d : Arrays.asList(-100.0, -3.0, 3.0, 10.0)) {
            Point p = new Point();
            p.add(d);
            log.info("Scoring: " + d + ", result: " + model.apply(p));
        }
        model.print();
        assertTrue(errorTS < 1.0/100);
        assertTrue(errorCS < 1.0/100);

    }
    public void testGradientDecentLinearYeqXMinus20() throws IOException {
        log.info("y = x dataset");
        CsvData linearData = CsvData.readFile("src/test/resources/linearyeqxminus20.csv");
        linearData.shuffle();
        GradientDescent gd = new GradientDescent();
        Model model = gd.solve(linearData);
        ModelValidator validator = new ModelValidator(linearData);
        Double errorCS = validator.validateControlSet(model);
        Double errorTS = validator.validateTrainigSet(model);
        log.info("Error CS = " + errorCS);
        log.info("Error TS = " + errorTS);
        for (Double d : Arrays.asList(-100.0, -3.0, 3.0, 10.0)) {
            Point p = new Point();
            p.add(d);
            log.info("Scoring: " + d + ", result: " + model.apply(p));
        }
        model.print();
        assertTrue(errorTS < 1.0/100);
        assertTrue(errorCS < 1.0/100);
    }

    public void testGradientDescentSquare() throws IOException {
        log.info("y = x^ 2 dataset");
        CsvData squareData = CsvData.readFile("src/test/resources/square.csv");
        squareData.shuffle();
        GradientDescent gd = new  GradientDescent();
        Model model = gd.solve(squareData);
        ModelValidator validator = new ModelValidator(squareData);
        Double errorCS = validator.validateControlSet(model);
        Double errorTS = validator.validateTrainigSet(model);
        log.info("Error CS = " + errorCS);
        log.info("Error TS = " + errorTS);
        for (Double d : Arrays.asList(-100.0, -3.0, 3.0, 10.0)) {
            Point p = new Point();
            p.add(d);
            log.info("Scoring: " + d + ", result: " + model.apply(p));
        }
        model.print();
    }

}
