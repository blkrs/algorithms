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
        int startSplit = (int) ((double) original.getHeight() * (1 - ratio));
        log.info("Start split from row: " + startSplit);
        copyDataToNewDataset(original, created, startSplit, original.getHeight());
        removeCopiedDataFromOriginalDataset(original, startSplit, original.getHeight());
        return created;
    }

    private static void removeCopiedDataFromOriginalDataset(CsvData original, int startSplit, int endSplit) {
        for (int row = endSplit-1; row >= startSplit;--row) {
            original.getFeaturesX().remove(row);
            original.getDependedVarsY().remove(row);
        }
    }

    private static void copyDataToNewDataset(CsvData original, CsvData created, int startSplit, int endSplit) {
        for (int row = startSplit; row < endSplit;++row) {
         created.getFeaturesX().add(original.getFeaturesX().get(row));
         created.getDependedVarsY().add(original.getDependedVarsY().get(row));
        }
    }
}
