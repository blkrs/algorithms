package krzych;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
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

    /**
     * Rigourous Test :-)
     */
    public void testGradientDescentIris() throws IOException {
        System.out.println("Iris dataset");
        CsvData data = CsvData.readFile("src/test/resources/iris.csv");
        data.shuffle();
        data.printX();
        GradientDescent gd = new GradientDescent();
        Model linearModel = gd.solve(data, 0.1, 0.0001);
        Double errorCS = gd.validateControlSet(linearModel);
        Double errorTS = gd.validateTrainigSet(linearModel);
        System.out.println("Error CS = " + errorCS);
        System.out.println("Error TS = " + errorTS);
    }

    public void testGradientDecentLinear() throws IOException {
        System.out.println("y = x dataset");
        CsvData linearData = CsvData.readFile("src/test/resources/linear.csv");
        linearData.shuffle();
        linearData.printX();
        GradientDescent gd = new GradientDescent();
        Model linearModel = gd.solve(linearData);
        Double errorCS = gd.validateControlSet(linearModel);
        Double errorTS = gd.validateTrainigSet(linearModel);
        System.out.println("Error CS = " + errorCS);
        System.out.println("Error TS = " + errorTS);

    }

    public void testGradientDescentSquare() throws IOException {
        System.out.println("y = x^ 2 dataset");
        CsvData squareData = CsvData.readFile("src/test/resources/square.csv");
        squareData.shuffle();
        squareData.printX();
        GradientDescent gd = new  GradientDescent();
        Model linearModel = gd.solve(squareData);
        Double errorCS = gd.validateControlSet(linearModel);
        Double errorTS = gd.validateTrainigSet(linearModel);
        System.out.println("Error CS = " + errorCS);
        System.out.println("Error TS = " + errorTS);
    }

}
