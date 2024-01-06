/*
 * Author: Kevin Wu
 * UWO ID: KWU347
 * Description: Graph class represents an undirected graph. It uses an adjacency matrix to store the graph
 */
import java.util.*;

class Graph {
	//Initializes the arraylist and the matrix used in storing the graph
    private List<GraphNode> nodes;
    private GraphEdge[][] edges;

    //Constructor of the class that sets up the matrix based on the size of the maze and creates all the nodes for the maze
    public Graph(int n) {
        nodes = new ArrayList<>();
        edges = new GraphEdge[n][n];

        // Create nodes
        for (int i = 0; i < n; i++) {
            nodes.add(new GraphNode(i));
        }
    }
    
    //This function takes the information provided by the user and stores it into an graph edge
    public void insertEdge(GraphNode u, GraphNode v, int type, String label) throws GraphException {
        //Checks to see if the node being inserted is valid or not
    	if (!nodes.contains(u) || !nodes.contains(v)) {
            throw new GraphException("Invalid node: " + u.getName() + " and " + v.getName());
        }
    	
    	//adds the edges to an adjacency matrix
        edges[u.getName()][v.getName()] = new GraphEdge(u, v, type, label);
        edges[v.getName()][u.getName()] = new GraphEdge(v, u, type, label);

    }
    
    //returns the node given the name of the requested node
    public GraphNode getNode(int u) throws GraphException {
        //iterates through the list of nodes to find the one that's name matches
    	for (GraphNode node : nodes) {
            if (node.getName() == u) {
                return node;
            }
        }
        throw new GraphException("Node not found: " + u);
    }
    
    //Retrieves an iterator over the incident edges of the specified node
    public Iterator incidentEdges(GraphNode u) throws GraphException {
        // Check if the node exists in the graph 
    	if (!nodes.contains(u)) {
             throw new GraphException("Invalid node: " + u.getName());
         }
        // Create a list to store incident edges
         List<GraphEdge> incidentEdges = new ArrayList<>();
         //loops through the matrix to find incident edges and adds them to the list
         for (int i = 0; i < edges[u.getName()].length; i++) {
             if (edges[u.getName()][i] != null) {
                 incidentEdges.add(edges[u.getName()][i]);
             }
         }
         return incidentEdges.iterator();
    }
    
    //returns the graph edge based on the two endpoints provided
    public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
    	//checks if the two nodes exist
    	if (!nodes.contains(u) || !nodes.contains(v)) {
            throw new GraphException("Invalid nodes: " + u.getName() + ", " + v.getName());
        }
    	//Checks to make sure the edge exists in the adjacency matrix
        if (edges[u.getName()][v.getName()] != null) {
            return edges[u.getName()][v.getName()];
        } 
        //Checks the matrix going in the other direction 
        else if (edges[v.getName()][u.getName()] != null) {
            return edges[v.getName()][u.getName()];
        }
        throw new GraphException("Edge not found between nodes: " + u.getName() + ", " + v.getName());
    }
    
    
    //checks if two nodes are adjacent
    public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
        return getEdge(u, v) != null;
    }
}

