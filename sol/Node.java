package sol;

import src.Row;

import java.util.ArrayList;

public class Node implements ITreeNode{

    private String defaultDecision;
    private ArrayList<Edge> edges;
    private String attribute;
    public Node(String attribute, ArrayList<Edge> edges, String defaultDecision) {
        this.attribute = attribute;
        this.edges = edges;
        this.defaultDecision = defaultDecision;
    }


    @Override
    // After the tree is done, find the decision for an object
    public String getDecision(Row forDatum) {
//        if (this.edges == null){
//            return this.defaultDecision;
//        }
        for (int i = 0; i< this.edges.size(); i++) {
            String dataVal = forDatum.getAttributeValue(this.attribute);
            if (this.edges.get(i).getEdgeValue().equals(dataVal)) {
                return this.edges.get(i).getNext().getDecision(forDatum);
            }
        }
        return this.defaultDecision;
        }
    }
