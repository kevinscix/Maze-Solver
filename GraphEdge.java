/*
 * Author: Kevin Wu
 * UWO ID: KWU347
 * Description: Graph edge is a class that represents an edge of the graph. It stores the two connecting node, its type and its label
 */
public class GraphEdge {
	//initializes the variables used in the class
	private GraphNode u;
    private GraphNode v;
    private int type;
    private String label;

    //constructor that takes in the two endpoints of the edge and its type and label
    public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
        this.u = u;
        this.v = v;
        this.type = type;
        this.label = label;
    }
    
    //returns the first endpoint of the graph edge
    public GraphNode firstEndpoint() {
        return u;
    }
    
    //returns the second endpoint of the graph edge
    public GraphNode secondEndpoint() {
        return v;
    }
    
    //returns the type of the graph edge
    public int getType() {
        return type;
    }
    
    // sets the type of the graph edge
    public void setType(int newType) {
        this.type = newType;
    }
    
    //returns the label of the graph edge
    public String getLabel() {
        return label;
    }
    
    //sets the label of the graph edge
    public void setLabel(String newLabel) {
        this.label = newLabel;
    }
}
