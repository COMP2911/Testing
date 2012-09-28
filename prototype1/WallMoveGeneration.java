import java.util.Map;
import java.util.LinkedList;

public class WallMoveGeneration implements MoveGeneration {

	public LinkedList<Node> GenerateMoves(Node currPlayer, Node otherPlayer, Map<WallNode, WallNode> walls) {
		LinkedList<Node> wallMoves = new LinkedList<Node>();
	// 8 ways of placing a wall on a row when board is empty
		for ( int a = 0; a < 8; ++a ) {
		// 8 ways of placing a wall on a col when board is empty
			for ( int b = 0; b < 8; ++b ) {
				WallNode i = new WallNode(b, a, 'v');
				if ( isValidWall ( currPlayer, otherPlayer, i, walls ) ) {
					wallMoves.add( i );
				}
				i = new WallNode(b, a, 'h');
				if ( isValidWall ( currPlayer, otherPlayer, i, walls ) ) {
					wallMoves.add( i );
				}
			}
		}
		
		return wallMoves;
	}
	
	public boolean isValidWall( Node currPlayer, Node otherPlayer, WallNode i, Map<WallNode, WallNode> walls ) {
		DijkstraPathfinding Dijkstra = new DijkstraPathfinding();
		if ( !walls.containsKey(i) && !walls.containsKey(i.getOtherPart()) ) {
			walls.put(i,i);
			walls.put(i.getOtherPart(),i.getOtherPart());
		// Check if wall blocks path
			if ( Dijkstra.runDijkstra( currPlayer, ((PlayerNode)currPlayer).getGoal(), walls ) != null &&
				 Dijkstra.runDijkstra( otherPlayer, ((PlayerNode)otherPlayer).getGoal(), walls ) != null ) {
					walls.remove(i);
					walls.remove( i.getOtherPart() );
					return true;
			}
			walls.remove(i);
			walls.remove( i.getOtherPart() );
		}
		return false;
	}
	/*
	public LinkedList<Node> GenerateMoves(Node currPlayer, Node otherPlayer, Map<WallNode, WallNode> walls) {
		LinkedList<Node> wallMoves = new LinkedList<Node>();
		LinkedList<WallNode> possibleWallMoves = new LinkedList<WallNode>();
	// 8 ways of placing a wall on a row when board is empty
		for ( int a = 0; a < 8; ++a ) {
		// 8 ways of placing a wall on a col when board is empty
			for ( int b = 0; b < 8; ++b ) {
				possibleWallMoves.add( new WallNode(b, a, 'v') );
				possibleWallMoves.add( new WallNode(b, a, 'h') );
			}
		}
		
		DijkstraPathfinding AStar = new DijkstraPathfinding();
	// Check if any wall overlaps
		for ( WallNode i : possibleWallMoves ) {
			if ( !walls.containsKey(i) && !walls.containsKey(i.getOtherPart()) ) {
				walls.put(i,i);
				walls.put(i.getOtherPart(),i.getOtherPart());
			// Check if wall blocks path
				if ( AStar.runDijkstra( currPlayer, new Node(0,8), walls ) != null &&
					 AStar.runDijkstra( otherPlayer, new Node(0,0), walls ) != null ) {
						wallMoves.add(i);
				}
				walls.remove(i);
				walls.remove( i.getOtherPart() );
			}
		}
		
		return wallMoves;
	}*/
}