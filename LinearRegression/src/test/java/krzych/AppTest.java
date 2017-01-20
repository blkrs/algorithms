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
    public static Test suite()  {
        TestSuite allTests =  new TestSuite( AppTest.class );
        allTests.addTest(DatasetNormalizerTest.suite());
        allTests.addTest(DatasetSplitterTest.suite());
        return allTests;
    }

    public void testGradientDescentIris() throws IOException {
        log.info("Iris dataset");
        DataSuite data = DataSuite.readFile("src/test/resources/iris.csv");

        GradientDescent gd = new GradientDescent();
        Model model = gd.solve(data.trainingSet);
        model.setNormalizer(data.normalizer);

        ModelValidator trainingSetValidator = new ModelValidator(data.trainingSet);
        ModelValidator controlSetValidator = new ModelValidator(data.controlSet);
        Double errorCS = trainingSetValidator.validateSet(model);
        Double errorTS = controlSetValidator.validateSet(model);

        log.info("Error CS = " + errorCS);
        log.info("Error TS = " + errorTS);
        model.print();
        double avgError = 0;

        for (int row = 0; row < data.controlSet.getHeight(); ++row) {
            Double calculatedY = model.apply(data.controlSet.getFeaturesX().get(row));
            Double originalY =  model.getNormalizer().invertScaleY(data.controlSet.getDependedVarsY().get(row));
            log.info("Calculated y :" + calculatedY + " originalY: " + originalY);
            avgError += Math.abs(originalY - calculatedY) / originalY ;
            assertTrue(Math.abs(calculatedY
                    - originalY) < 1.2);
        }
        avgError/=data.controlSet.getHeight();
        log.info("AVG error: " + avgError);
        assertTrue(avgError < 0.174);
    }

    // in this test we want to make sure that if we round the answer to integer,
    // then we will have no errors
    public void testGradientDescentIrisRigorious() throws IOException {
        log.info("Iris dataset");
        DataSuite data = DataSuite.readFile("src/test/resources/iris.csv");

        GradientDescent gd = new GradientDescent();
        Model model = gd.solve(data.trainingSet);
        model.setNormalizer(data.normalizer);

        ModelValidator trainingSetValidator = new ModelValidator(data.trainingSet);
        ModelValidator controlSetValidator = new ModelValidator(data.controlSet);
        Double errorCS = trainingSetValidator.validateSet(model);
        Double errorTS = controlSetValidator.validateSet(model);
        log.info("Error CS = " + errorCS);
        log.info("Error TS = " + errorTS);
        model.print();
        for (int row = 0; row < 10; ++row) {
            Double calculatedY = model.apply(data.controlSet.getFeaturesX().get(row));
            Double originalY =  model.getNormalizer().invertScaleY(data.controlSet.getDependedVarsY().get(row));
            log.info("Calculated y :" + calculatedY + " originalY: " + originalY);
            assertTrue(Math.abs(calculatedY
                    - originalY) < 0.5);
        }
    }

    public void testGradientDecentLinear() throws IOException {
        log.info("y = x dataset");
        DataSuite data = DataSuite.readFile("src/test/resources/linearyeqx.csv");

        GradientDescent gd = new GradientDescent();
        Model model = gd.solve(data.trainingSet);
        model.setNormalizer(data.normalizer);
        ModelValidator trainingSetValidator = new ModelValidator(data.trainingSet);
        ModelValidator controlSetValidator = new ModelValidator(data.controlSet);

        Double errorCS = trainingSetValidator.validateSet(model);
        Double errorTS = controlSetValidator.validateSet(model);
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
        DataSuite data = DataSuite.readFile("src/test/resources/linearyeqxminus20.csv");

        GradientDescent gd = new GradientDescent();
        Model model = gd.solve(data.trainingSet);
        model.setNormalizer(data.normalizer);

        ModelValidator trainingSetValidator = new ModelValidator(data.trainingSet);
        ModelValidator controlSetValidator = new ModelValidator(data.controlSet);
        Double errorCS = trainingSetValidator.validateSet(model);
        Double errorTS = controlSetValidator.validateSet(model);

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
        DataSuite data = DataSuite.readFile("src/test/resources/square.csv");

        GradientDescent gd = new GradientDescent();
        Model model = gd.solve(data.trainingSet);
        model.setNormalizer(data.normalizer);

        ModelValidator trainingSetValidator = new ModelValidator(data.trainingSet);
        ModelValidator controlSetValidator = new ModelValidator(data.controlSet);
        Double errorCS = trainingSetValidator.validateSet(model);
        Double errorTS = controlSetValidator.validateSet(model);

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
