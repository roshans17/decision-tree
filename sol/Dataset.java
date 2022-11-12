package sol;

import src.DecisionTreeCSVParser;
import src.IDataset;
import src.Row;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that implements the IDataset interface,
 * representing a training.csv data set.
 */
public class Dataset implements IDataset {

    private List<Row> rowList;
//    private List<String> unusedList;
    private List<String> attList;

    public Dataset(List<String> attList, List<Row> rowList) {
        this.attList = attList;
        this.rowList = rowList;
    }

    public List<Row> partitionOnAtt(String attName, String attValue) {
        List<Row> filtRow = new ArrayList<>();
        for (Row r: this.rowList){
            if (r.getAttributeValue(attName).equals(attValue)){
                filtRow.add(r);
            }
        }
        return filtRow;
    }

    @Override
    public List<String> getAttributeList() {
        return this.attList;
    }

    @Override
    public List<Row> getDataObjects() {
        return this.rowList;
    }

    @Override
    public int size() {
        return this.rowList.size();
    }


    // TODO: Implement methods declared in IDataset interface!
}
