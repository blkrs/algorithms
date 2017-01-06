package krzych;

import java.util.List;

/**
 * Created by krzych on 06.01.17.
 */
public class DatasetNormalizer {
    private Point minVals = new Point();
    private Point maxVals = new Point();
    private int width;
    private int height;
    private int yIndex;
    private int xCount;

    private List<Point> dataPoints;

    public DatasetNormalizer(CsvData dataset) {
        dataPoints = dataset.getDataPoints();
        width = dataPoints.get(0).getVector().size();
        height = dataPoints.size();
        readMinMax();
        yIndex = width - 1;
        xCount = width - 1;

    }

    public void featureScaling(Boolean invert) {
        if (!invert) {
            for (int row = 0; row < height; ++row) {
                scaleRow(row);
            }
        } else {
            for (int row = 0; row < height;++row )
                revertScaleRow(row);
        }
    }

    public Double numberScale(int columnIdx, double val) {
        return scaleADouble(columnIdx, val);
    }

    public Double invertNumberScale(int columnIdx, double val) {
        return invertScaleADouble(columnIdx, val);
    }

    public Double getMaxValue(int column) {
        return maxVals.get(column);
    }
    public Double getMinValue(int column) {
        return minVals.get(column);
    }

    public Double getRange(int column) {
        return getMaxValue(column) - getMinValue(column);
    }

    private void readMinMax() {
        for (int i = 0;i < width; ++i) {
            maxVals.getVector()
                    .add(dataPoints.get(0).getVector().get(i));
            minVals.getVector()
                    .add(dataPoints.get(0).getVector().get(i));
        }

        for (int j = 1; j < height; ++j ) {
            for (int i = 0;i < width; ++i) {
                if (maxVals.getVector().get(i) < dataPoints.get(j).getVector().get(i)) {
                    maxVals.getVector().set(i,dataPoints.get(j).getVector().get(i));
                }
                if (minVals.getVector().get(i) > dataPoints.get(j).getVector().get(i)) {
                    minVals.getVector().set(i, dataPoints.get(j).getVector().get(i));
                }
            }
        }
    }

    public Point scaleXVector(Point p) {
        for (int i = 0; i < xCount; ++i) {
            p.set(i, scaleADouble(i, p.get(i)));
        }
        return p;
    }

    public double invertScaleY(Double d) {
        return invertScaleADouble(yIndex, d);
    }


    private void scaleRow(int rowIdx) {
        for (int i = 0; i < width; ++i) {
            scaleCell(rowIdx, i);
        }
    }

    private void revertScaleRow(int rowIdx) {
        for (int i = 0; i < width; ++i) {
            invertScaleCell(rowIdx, i);
        }
    }

    private void scaleCell(int rowIdx, int columnIdx) {
        dataPoints.get(rowIdx).getVector()
                .set(columnIdx,
                        scaleADouble(columnIdx,
                                     dataPoints.get(rowIdx).getVector().get(columnIdx)
                        )
                );
    }

    private void invertScaleCell(int rowIdx, int columnIdx) {
        dataPoints.get(rowIdx).getVector()
                .set(columnIdx,
                        invertScaleADouble(columnIdx,
                                dataPoints.get(rowIdx).getVector().get(columnIdx)
                        )
                );
    }

    private Double scaleADouble(int columnIdx, Double x) {
        return (x - minVals.getVector().get(columnIdx)) / (maxVals.getVector().get(columnIdx) - minVals.getVector().get(columnIdx));
    }

    private Double invertScaleADouble(int columnIdx, Double x) {
        return x * (maxVals.getVector().get(columnIdx) - minVals.getVector().get(columnIdx)) + minVals.getVector().get(columnIdx);
    }

}
