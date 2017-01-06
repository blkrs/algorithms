package krzych;

/**
 * Created by krzych on 06.01.17.
 */
public class DatasetNormalizer {
    private Point minVals = new Point();
    private Point maxVals = new Point();
    private double minY;
    private double maxY;
    private int width;
    private int height;

    private CsvData data;

    public DatasetNormalizer(CsvData dataset) {
        data = dataset;
        width = data.getX().get(0).size();
        height = data.getX().size();
        computeMinMax();
    }

    public void featureScaling(Boolean invert) {
        if (!invert) {
            for (int row = 0; row < height; ++row) {
                scaleXRow(row);
                data.getY().set(row,
                        scaleY(
                                data.getY().get(row)
                        )
                );
            }
        } else {
            for (int row = 0; row < height;++row ) {
                revertScaleXRow(row);
                data.getY().set(row,
                        invertScaleY(
                                data.getY().get(row)
                        )
                );
            }
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

    public Double getRangeY() { return maxY - minY; }

    private void computeMinMax() {
        minY = data.getY().get(0);
        maxY = data.getY().get(0);
        for (int column = 0;column < width; ++column) {
            maxVals.add(data.getX().get(0).get(column));
            minVals.add(data.getX().get(0).get(column));
        }
        for (int row = 1; row < height; ++row ) {
            for (int column = 0;column < width; ++column) {
                if (maxVals.get(column) < data.getX().get(row).get(column)) {
                    maxVals.set(column,data.getX().get(row).get(column));
                }
                if (minVals.get(column) > data.getX().get(row).get(column)) {
                    minVals.set(column, data.getX().get(row).get(column));
                }
            }
            if (minY > data.getY().get(row)) {
                minY = data.getY().get(row);
            }
            if (maxY < data.getY().get(row)) {
                maxY = data.getY().get(row);
            }
        }
    }

    public Point scaleXVector(Point p) {
        for (int i = 0; i < width; ++i) {
            p.set(i, scaleADouble(i, p.get(i)));
        }
        return p;
    }

    public double invertScaleY(Double y) {
        return (y * (maxY - minY)) + minY;
    }

    public double scaleY(Double y) {
        return (y - minY) / (maxY - minY);
    }


    private void scaleXRow(int rowIdx) {
        for (int i = 0; i < width; ++i) {
            scaleCell(rowIdx, i);
        }
    }

    private void revertScaleXRow(int rowIdx) {
        for (int i = 0; i < width; ++i) {
            invertScaleCell(rowIdx, i);
        }
    }

    private void scaleCell(int rowIdx, int columnIdx) {
        data.getX().get(rowIdx)
                .set(columnIdx,
                        scaleADouble(columnIdx,
                                     data.getX().get(rowIdx).get(columnIdx)
                        )
                );
    }

    private void invertScaleCell(int rowIdx, int columnIdx) {
        data.getX().get(rowIdx).getVector()
                .set(columnIdx,
                        invertScaleADouble(columnIdx,
                                data.getX().get(rowIdx).getVector().get(columnIdx)
                        )
                );
    }

    private Double scaleADouble(int columnIdx, Double x) {
        return (x - minVals.get(columnIdx)) / (maxVals.get(columnIdx) - minVals.get(columnIdx));
    }

    private Double invertScaleADouble(int columnIdx, Double x) {
        return x * (maxVals.get(columnIdx) - minVals.get(columnIdx)) + minVals.get(columnIdx);
    }

}
