package sol;

import src.Row;

public class Leaf implements  ITreeNode{

    private String attribute;

    public Leaf(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getDecision(Row forDatum) {
        return this.attribute;
    }
}
