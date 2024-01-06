/*
 * Author: Kevin Wu
 * UWO ID: KWU347
 * Description: This class is the maze class that stores a maze in a graph object and finds the solution to the maze
 */
import java.io.*;
import java.util.*;

public class Maze {
	//initializes the variables used in the function
	private Graph graph;
	private int start, exit, coins;
	private Stack<GraphNode> stack;
	
	
	//initializes the maze based on the input file using tis width, length, number of available coins and the layout of the maze
	public Maze(String inputFile) throws MazeException, GraphException, NumberFormatException, IOException {
		try {
			BufferedReader input = new BufferedReader(new FileReader(inputFile));
			//takes in the parameters of the maze, excluding the scale since it is not used
			Integer.parseInt(input.readLine());
			int width = Integer.parseInt(input.readLine());
			int length = Integer.parseInt(input.readLine());
			coins = Integer.parseInt(input.readLine());

			//creates stack for storing the path
			stack = new Stack<GraphNode>();
			//creates the graph for the maze
			graph = new Graph(width * length);

			//loops through the maze and sets up the edges for the matrix
			int row = 0;
			int hEdge = 0;
			int vEdge = 0;
			String line;
			while ((line = input.readLine()) != null) {
				row++;
				//loops through each line and adds all edges to the matrix
				for (int i = 0; i < line.length(); i++) {
					char character = line.charAt(i);
					//looks through the even numbered lines
					if (row % 2 == 0 && i % 2 == 0 ){
						//checks if it is a wall
						if (character == 'w') { 
							vEdge++;
						} 
						//checks if it is a corridor
						else if (character == 'c') { 
							graph.insertEdge(graph.getNode(vEdge), graph.getNode(vEdge + width), 0, "corridor");
							vEdge++;
						} 
						//checks if it is a door and how many coins needed to pass through it
						else if (character >= 48 && character <= 57){  
							int neededCoins = Character.getNumericValue(character);
							graph.insertEdge(graph.getNode(vEdge), graph.getNode(vEdge + width), neededCoins , "door");
							vEdge++;
						}
					}
					// looks through the odd numbered lines
					else if(row % 2 == 1) {
						// handles the even index of each line
						if (i % 2 == 0) {
							//finds the start of the maze
							if (character == 's') { 
								start = hEdge;
							} 
							//finds the exit of the maze
							else if (character == 'x') { 
								exit = hEdge;
							}
						} 
						// handles the odd index of each line
						if (i % 2 == 1) {
							//checks if it is a wall
							if (character == 'w') { 
								hEdge++;
							} 
							//checks if it is a corridor
							else if (character == 'c') { 
								graph.insertEdge(graph.getNode(hEdge), graph.getNode(hEdge + 1), 0, "corridor");
								hEdge++;
							} 
							//checks if it is a door and how many coins needed to pass through it
							else if (character >= 48 && character <= 57) {  
								int neededCoins = Character.getNumericValue(character);
								graph.insertEdge(graph.getNode(hEdge), graph.getNode(hEdge + 1), neededCoins, "door");
								hEdge++;
							}
						}
					} 
				}
	            // fixes the horizontal edges and vertical edges based on the number of nodes
				if (row % 2 == 1) {
					hEdge = ((row / 2) + 1) * width;
				}
				if (row % 2 == 0) {
					vEdge = (row / 2) * width;
				}
			}
			//closes the input
			input.close();
			//catches any exceptions
		}  catch (FileNotFoundException e) {
			throw new MazeException("Invalid input file");
		}
	}

	//function that returns the graph of the maze
	public Graph getGraph() throws MazeException {
		if (graph ==  null) throw new MazeException("Error: invalid maze");

		return graph;
	} 
	//This function takes the maze entrance and exit and uses a helper function to find the path of the maze and returns the stack iterator with the path
	public Iterator<GraphNode> solve() throws GraphException {
		GraphNode mazeEntrance = graph.getNode(start);
		GraphNode mazeExit = graph.getNode(exit);
		//checks with helper function to see if there is a path or not
		boolean path = pathFinder(mazeEntrance, mazeExit, coins);
		// if there is a path it returns the stack with the path
		if (path == true) {
			return stack.iterator();
		} else {
			return null;
		}
	}

	//this function takes in the entrance and the exit of the maze and the number of available coins and recursively searches for an exit
	private boolean pathFinder(GraphNode entrance, GraphNode exit, int coins) throws GraphException{
		stack.push(entrance);
		entrance.mark(true);
		//If the node matches then it will return true showing the path is found
		if (entrance.getName()==(exit.getName())) {
			return true;
		}
		//grabs the edge nodes of the current node in the graph
		Iterator<GraphEdge> edgeNodes = graph.incidentEdges(entrance);
		while (edgeNodes.hasNext()) {
			//grabs the next edge and its connected nodes and checks both directions of the connected nodes
			GraphEdge edge = edgeNodes.next();
			GraphNode u = edge.secondEndpoint();
			//checks nodes to see if node has been visited or not and has enough coins to visit 
			if (!u.isMarked() && edge.getType() <= coins) {
				int updatedCoinsAvailable = coins - edge.getType(); // Update coins if can pass through
				// checks other nodes connected to this edge to find paths recursively
				if (pathFinder(u, exit, updatedCoinsAvailable)) {
					//returns true when path is found
					return true; 
				}
			}
		}	
		// removes the mark from the nodes that are the path to the exit
		entrance.mark(false); 
		// removes the node that doesn't lead to exit from stack
		stack.pop(); 
		//returns false and goes in the previous calling of the function to search for a different path
		return false; 	
	}
}