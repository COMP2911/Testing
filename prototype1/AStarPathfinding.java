
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class AStarPathfinding {
	Map<Node, Node> closedNodes;
	Map<Node, Node> openNodes;
	Map<Node, Node> navigatedNodes;
	Map<Node, Integer> G_Cost;
	Map<Node, Integer> F_Cost;
	Map<Node, Integer> H_Cost;
	
	public AStarPathfinding () {
		closedNodes		= new HashMap<Node, Node>();
		openNodes		= new HashMap<Node, Node>();
		navigatedNodes	= new HashMap<Node, Node>();
		G_Cost = new HashMap<Node, Integer>();
		F_Cost = new HashMap<Node, Integer>();
		H_Cost = new HashMap<Node, Integer>();
	}
	
	public LinkedList<Node> runAStar ( Node start, Node goal, Map<Node, Node> closedNodes ) {
		//closedNodes = new HashMap<Node, Node>();	// The set of nodes already evaluated.
		openNodes = new HashMap<Node, Node>();		// The set of tentative nodes to be evaluated, initialily containing the start node
		openNodes.put ( start, start );	
		navigatedNodes = new HashMap<Node, Node>();	// The map of navigated nodes.
		
		G_Cost = new HashMap<Node, Integer>();	// Cost from start along best known path.
		G_Cost.put ( start, 0 );
		// Estimated total cost from start to goal through y.
		F_Cost = new HashMap<Node, Integer>();
		F_Cost.put ( start, G_Cost.get(start) + heuristic_cost_estimate(start, goal) );
		
		while ( !( openNodes.isEmpty() ) ) {
			// NOT DONE
			Node current = getLowestF_CostNode ( openNodes, F_Cost );
			if ( current.equals( goal ) ) {
				LinkedList<Node> Moves = new LinkedList<Node>();
				reconstruct_path (navigatedNodes, goal, Moves);
				return Moves;
			}
			
			openNodes.remove ( current );
			closedNodes.put ( current, current );
			LinkedList<Node> neighbourNodes = getNeighbourNodes ( current );
			for ( Node neighbour : neighbourNodes ) {
				boolean skipNeighbour = false;
			// Check if neighbour is in closed nodes
			// Skip neighbour node if its in the closed nodes
				if ( closedNodes.containsKey ( neighbour ) ) {
					continue;
				}
			// Temp G Cost NOTE* HCE should be distance between
				int tempG_Cost = G_Cost.get(current) + Math.abs(current.row - neighbour.row);//+ heuristic_cost_estimate ( current, neighbour );
			// Check if neighbour is not in open nodes
			// Check if temp G Cost is smaller than G Cost of neighbour
				if ( !( openNodes.containsKey ( neighbour ) ) || tempG_Cost < G_Cost.get ( neighbour ) ) {
					if ( !( openNodes.containsKey ( neighbour ) ) ) {
						openNodes.put ( neighbour, neighbour );
					}
					navigatedNodes.put ( neighbour, current );
					G_Cost.put ( neighbour, tempG_Cost );
					F_Cost.put ( neighbour, G_Cost.get ( neighbour ) + heuristic_cost_estimate ( neighbour, goal ) );				
				}
			}
		}
		return null;
	}
	
	// Manhattan Distance Formula
	public Integer heuristic_cost_estimate ( Node start, Node goal ) {
	// |p1 - q1| + |p2 - q2|
		Integer result = Math.abs(start.col - goal.col) + Math.abs(start.row - goal.row);
		return result;
	}
	
	// Get Lowest F Cost Node in the open nodes
	public Node getLowestF_CostNode ( Map<Node, Node> openNodes, Map<Node, Integer> F_Cost ) {
	// Initial cost
		int lowestF_Cost = Integer.MAX_VALUE;
	// Transverse the Nodes
		Node resultNode = null;
		for ( Node i : openNodes.values() ) {
			if ( F_Cost.get(i) < lowestF_Cost ) {
				lowestF_Cost = F_Cost.get(i);
				resultNode = i;
			}
		}
		return resultNode;
	}
	
	// Reconstruct Path
	public Node reconstruct_path ( Map<Node, Node> navigatedNodes, Node current, LinkedList<Node> Moves ) {
	// Check if current node is in navigated nodes
		if ( navigatedNodes.containsKey ( current ) ) {
		/*
		p := reconstruct_path(came_from, came_from[current_node])
         return (p + current_node)
		 */
			Moves.addFirst(current);
		// ???
			return reconstruct_path( navigatedNodes, navigatedNodes.get ( current ), Moves );
		}
		else {
			return current;
		}
	}
	
// Get Neighbours List
	public LinkedList<Node> getNeighbourNodes ( Node current ) {
		int x = current.col;
		int y = current.row;
		LinkedList<Node> neighbourNodes = new LinkedList<Node>();
	// Add up of current node
		if ( y < (GlobalConstants.MAX_ROW-1) ) {
			neighbourNodes.add( new Node (x,y+1) );
		}
	// Add left of current node
		if ( x > 0 ) {
			neighbourNodes.add( new Node (x-1,y) );
		}
	// Add right of current node
		if ( x < (GlobalConstants.MAX_COL-1) ) {
			neighbourNodes.add( new Node (x+1,y) );
		}
	// Add down of current node
		if ( y > 0 ) {
			neighbourNodes.add( new Node (x,y-1) );
		}
		return neighbourNodes;
	}
}