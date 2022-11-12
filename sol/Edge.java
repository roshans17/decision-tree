package sol;

public class Edge {

    private String edgeValue;
    private ITreeNode next;

    /**
     *
     * @param edgeValue: Value of attribute associated with edge
     * @param next: next ITreeNode Object (Leaf or Node)
     */
    public Edge(String edgeValue, ITreeNode next){
        this.edgeValue = edgeValue;
        this.next = next;
    }

    public String getEdgeValue(){
        return this.edgeValue;
    }

    public ITreeNode getNext() {
        return this.next;
    }

}
