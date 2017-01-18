package krzych;

import org.apache.log4j.Logger;

/**
 * Created by krzych on 18.01.17.
 */
public class DatasetSplitter {
    private static final Logger log = Logger.getLogger(DatasetSplitter.class);

    public static CsvData splitWithRatio(CsvData original, double ratio) {
        CsvData created = new CsvData();
        created.setColumnsMapping(original.getColumnsMapping());
        int height = original.getHeight();
        int startSplit = (int) ((double) height * ratio);
        log.info("Start split = " + startSplit);
        int endSplit = height;

        for (int row = startSplit; row < endSplit;++row) {
         created.getFeaturesX().add(original.getFeaturesX().get(row));
         created.getDependedVarsY().add(original.getDependedVarsY().get(row));
        }
        for (int row = endSplit-1; row >= startSplit;--row) {
            original.getFeaturesX().remove(row);
            original.getDependedVarsY().remove(row);
        }
        return created;
    }
}
