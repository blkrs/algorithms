package krzych;

import org.apache.log4j.Logger;

/**
 * Created by krzych on 06.01.17.
 */
public class DatasetNormalizer {
    private static final Logger log = Logger.getLogger(DatasetNormalizer.class);
    private Point minVals = new Point();
    private Point maxVals = new Point();
    private double minY;
    private double maxY;
    private int width;
    private int height;

    private CsvData data;

    public DatasetNormalizer(CsvData dataset) {
        data = dataset;
        width = data.getFeaturesX().get(0).size();
        height = data.getFeaturesX().size();
        computeBounds();
    }

    public void scalingFeatures() {
        for (int row = 0; row < height; ++row) {
            scaleXRow(row);
            data.getDependedVarsY().set(row,
                    scaleY(
                            data.getDependedVarsY().get(row)
                    )
            );
        }
    }

    public void descaleFeatures() {
        for (int row = 0; row < height; ++row) {
            revertScaleXRow(row);
            data.getDependedVarsY().set(row,
                    invertScaleY(
                            data.getDependedVarsY().get(row)
                    )
            );
        }

    }


    public Double numberScale(int columnIdx, double val) {
        return scaleADouble(columnIdx, val);
    }

    public Double invertNumberScale(int columnIdx, double val) {
        return invertScaleADouble(columnIdx, val);
    }

    private void computeBounds() {
        initBoundsFromFirstRow();
        findMinMax();
        log.info("Min x values: " + minVals.toString());
        log.info("Max x values: " + maxVals.toString());
        log.info("Min Y:" + minY);
        log.info("Max Y:" + maxY);
    }

    private void findMinMax() {
        for (int row = 1; row < height; ++row) {
            for (int column = 0; column < width; ++column) {
                if (maxVals.get(column) < data.getFeaturesX().get(row).get(column)) {
                    maxVals.set(column, data.getFeaturesX().get(row).get(column));
                }
                if (minVals.get(column) > data.getFeaturesX().get(row).get(column)) {
                    minVals.set(column, data.getFeaturesX().get(row).get(column));
                }
            }
            if (minY > data.getDependedVarsY().get(row)) {
                minY = data.getDependedVarsY().get(row);
            }
            if (maxY < data.getDependedVarsY().get(row)) {
                maxY = data.getDependedVarsY().get(row);
            }
        }
    }

    private void initBoundsFromFirstRow() {
        minY = data.getDependedVarsY().get(0);
        maxY = data.getDependedVarsY().get(0);
        for (int column = 0; column < width; ++column) {
            maxVals.add(data.getFeaturesX().get(0).get(column));
            minVals.add(data.getFeaturesX().get(0).get(column));
        }
    }

    Point scaleXVector(Point p) {
        for (int i = 0; i < width; ++i) {
            p.set(i, scaleADouble(i, p.get(i)));
        }
        return p;
    }

    double invertScaleY(Double y) {
        return (y * (maxY - minY)) + minY;
    }

    private double scaleY(Double y) {
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
        data.getFeaturesX().get(rowIdx)
                .set(columnIdx,
                        scaleADouble(columnIdx,
                                data.getFeaturesX().get(rowIdx).get(columnIdx)
                        )
                );
    }

    private void invertScaleCell(int rowIdx, int columnIdx) {
        data.getFeaturesX().get(rowIdx)
                .set(columnIdx,
                        invertScaleADouble(columnIdx,
                                data.getFeaturesX().get(rowIdx).get(columnIdx)
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
