
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class DijkstraPathfinding {
	Map<Node, Integer> distance;
	Map<Node, Node> previous;
	Map<Node, Node> all;
	
	public DijkstraPathfinding () {
		distance = new HashMap<Node, Integer>();
		previous = new HashMap<Node, Node>();
		all = new HashMap<Node, Node>();
	}
	
	public LinkedList<Node> runDijkstra ( Node start, Node goal, Map<WallNode, WallNode> walls ) {
		distance.clear();
		previous.clear();
		all.clear();
		for ( int a = 0; a < GlobalConstants.MAX_ROW; ++a ) {
			for ( int b = 0; b < GlobalConstants.MAX_COL; ++b ) {
				Node i = new Node ( a, b );
				distance.put ( i, Integer.MAX_VALUE );
				all.put ( i, i );
			}
		}
		distance.put ( start, 0 );
		while ( !all.isEmpty() ) {
		// Not Done
			Node current = getSmallestDistance();
			if ( current.row == goal.row ) {
				LinkedList<Node> result = new LinkedList<Node>();
				while ( previous.containsKey ( current ) ) {
					result.addFirst ( current );
					current = previous.get ( current );
				}
				return result;
			}
			all.remove(current);
			if ( distance.get(current) == Integer.MAX_VALUE ) {
				break;
			}
			
			LinkedList<Node> neighbourNodes = getNeighbourNodes ( current );
			for ( Node neighbour : neighbourNodes ) {
			// Changed from closedNodes.containsKey ( neighbour )
				if ( neighbour.inBetweenWall ( current, walls ) )
					continue;
				int altDist = distance.get ( current ) + getDistance ( current, neighbour );
				if ( altDist < distance.get( neighbour ) ) {
					distance.put( neighbour, altDist );
					previous.put( neighbour, current );
				// decrease-key v in Q;                           // Reorder v in the Queue
				// ???
				}
			}
		}
		LinkedList<Integer> noResult = new LinkedList<Integer>();
		for ( Integer i : distance.values() ) {
			noResult.add ( i );
		}
		return null;
	}
	
	// Get Smallest Distance
	public Node getSmallestDistance () {
		int smallestDistance = Integer.MAX_VALUE;
		Node result = null;
		for ( Node i : all.values() ) {
			int d  = distance.get(i);
			if (d <= smallestDistance) {
				smallestDistance = d;
				result = i;
			}
		}
		return result;
	}
	
	// Get Distance - Manhattan Distance
	public Integer getDistance (Node start, Node goal) {
	// |p1 - q1| + |p2 - q2|
		Integer result = Math.abs(start.col - goal.col) + Math.abs(start.row - goal.row);
		return result;
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
	// Add down of current node
		if ( y > 0 ) {
			neighbourNodes.add( new Node (x,y-1) );
		}
	// Add left of current node
		if ( x > 0 ) {
			neighbourNodes.add( new Node (x-1,y) );
		}
	// Add right of current node
		if ( x < (GlobalConstants.MAX_COL-1) ) {
			neighbourNodes.add( new Node (x+1,y) );
		}
		return neighbourNodes;
	}
}