package sol;

import src.ITreeGenerator;
import src.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * A class that implements the ITreeGenerator interface
 * used to generate a tree
 */
public class TreeGenerator implements ITreeGenerator<Dataset> {

    private ITreeNode tree; // Our main tree that we will construct
    private List<String> unusedList;
    private String targetAtt;
    private String currAtt;

    public TreeGenerator() {
    }

    @Override
    /**
     * Recursive because it will change the targetAttribute
     * The first targetAttribute is given
     */
    public void generateTree(Dataset trainingData, String targetAttribute) {
        if (tree == null) {
            this.unusedList = trainingData.getAttributeList();
            this.targetAtt = targetAttribute;
            this.unusedList.remove(targetAttribute);
        }
        List<Row> dataObjects = trainingData.getDataObjects();
        this.currAtt = this.randomAttribute();
        this.setUpTree(this.currAtt, dataObjects);
    }

    private void setUpTree(String currAtt, List<Row> dataObjects) {
        this.tree = new Node(currAtt, this.getEdges(currAtt, dataObjects), this.mvc(currAtt, this.targetAtt, dataObjects));
    }

    private Node createNode(String currAtt, List<Row> dataObjects, String mvc){
        return new Node(currAtt, this.getEdges(currAtt, dataObjects), mvc);
    }

    /**
     * Used for the NEXT targetAttribute, not the current generateTree
     *
     * @return
     */
    public String randomAttribute() {
        if (this.unusedList == null || this.unusedList.isEmpty()){
            return null;
        }
        else {
            Random rand = new Random();
            int randomNum = Math.abs(rand.nextInt(this.unusedList.size()));
            return this.unusedList.get(randomNum);
        }
    }

    public List<Row> partitionOnAttValue(String attName, String attValue, List<Row> trainingData) {
        List<Row> filtRow = new ArrayList<>();
        for (Row r: trainingData){
            if (r.getAttributeValue(attName).equals(attValue)){
                filtRow.add(r);
            }
        }
        return filtRow;
    }

    private ArrayList<Edge> getEdges(String currAtt, List<Row> trainingData) {
        ArrayList<Edge> edgeArrayList = new ArrayList<>();
        List<String> uniqueValues = new ArrayList<>();
        String edgeValue;
        for (Row trainingDatum : trainingData) {
            edgeValue = trainingDatum.getAttributeValue(currAtt);
            if (!uniqueValues.contains(edgeValue)) {
                uniqueValues.add(edgeValue);
            }
        }
            for (String uniqueValue : uniqueValues) {
                this.unusedList.remove(currAtt);
                String nextAtt = this.randomAttribute();
                List<Row> updatedData = this.partitionOnAttValue(currAtt, uniqueValue, trainingData);


                if (this.canMakeLeaf(updatedData, this.targetAtt)) {
                    edgeArrayList.add(new Edge(uniqueValue, new Leaf(updatedData.get(0).getAttributeValue(this.targetAtt))));
                }
                else {
                    if (nextAtt == null){
                        System.out.print("reaches");
                        break;
                    }
                    System.out.println(nextAtt);
                    edgeArrayList.add(new Edge(uniqueValue, this.createNode(nextAtt, updatedData, this.mvc(nextAtt, this.targetAtt, trainingData))));
                }
            }
        return edgeArrayList;
    }

    private String mvc(String currAtt, String targetAtt, List<Row> trainingData){
        List<String> uniqueValues = new ArrayList<>();
        for (Row trainingDatum : trainingData) {
            String uniqueValue = trainingDatum.getAttributeValue(currAtt);
            if (!uniqueValues.contains(uniqueValue)) {
                uniqueValues.add(uniqueValue);
            }
        }
        int counter = -1;
        String attribute = null;
        for (int i=0; i < uniqueValues.size(); i++) {
            List<Row> partList = this.partitionOnAttValue(targetAtt, uniqueValues.get(i), trainingData);
            if (partList.size() > counter){
                attribute = uniqueValues.get(i);
                counter = partList.size();
            }
        }
        return attribute;
    }

    private boolean canMakeLeaf(List<Row> updatedData, String targetAtt){
        if (updatedData.size() == 1) {
            return true;
        }
        String initialAtt = updatedData.get(0).getAttributeValue(targetAtt);
        for (int i =0; i < updatedData.size(); i++){
            if (!updatedData.get(i).getAttributeValue(targetAtt).equals(initialAtt)){
                return false;
            }
        }
        return true;
    }

    @Override
    public String getDecision(Row datum) {
        return this.tree.getDecision(datum);
    }
    // TODO: Implement methods declared in ITreeGenerator interface!
}
