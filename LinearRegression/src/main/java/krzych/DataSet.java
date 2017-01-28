package krzych;

import java.util.List;

/**
 * Created by krzych on 28.01.17.
 */
public interface DataSet {

    ColumnsMapping getColumnsMapping();
    int getHeight();
    int getWidth();
    void polynomialExpand(int exponent);

    List<Double> computePartialDerivatives(final Model theta);
    Double costFunction(final Model theta);
    Double scoreRow(final Model theta,int row);
    Double getY(int row);
}
