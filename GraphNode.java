/*
 * Author: Kevin Wu
 * UWO ID: KWU347
 * Description: Graph node class represents a node of the graph, it stores its name and whether or not it has been marked
 */
public class GraphNode {
	//initialize the variables for GraphNode
	private int name;
    private boolean marked;
    
    //Constructor that sets up the node as false and saves its name
    public GraphNode(int name) {
        this.name = name;
        this.marked = false;
    }
    
    //Marks the node as true or false
    public void mark(boolean mark) {
        this.marked = mark;
    }
    
    //checks if the node is marked true or false
    public boolean isMarked() {
        return marked;
    }
    
    //returns the name of the node
    public int getName() {
        return name;
    }
}
