import java.util.Map;
import java.util.LinkedList;

public class PlayerMoveGeneration implements MoveGeneration {

	public LinkedList<Node> GenerateMoves(Node currPlayer, Node otherPlayer, Map<WallNode, WallNode> walls) {
		LinkedList<Node> playerMoves = new LinkedList<Node>();
		LinkedList<Node> possibleMoves = getNeighbourNodes(currPlayer);
		for ( Node i : possibleMoves ) {
		// If other player is in the spot jump over
			if ( !(i.inBetweenWall ( currPlayer, walls )) ) {
				if ( otherPlayer.equals(i) ) {
				// Change move to jump move
					Node jump = i.jump(currPlayer);
				// Check if wall is blocking
					//if ( walls.containsKey(jump) ) {
					if ( jump.inBetweenWall(otherPlayer, walls) ) {
					// Jump left
						jump = i.jumpLeft(currPlayer);
					// If wall is blocking left of other player jump right instead
						//if ( walls.containsKey(jump) ) {
						if ( jump.inBetweenWall(otherPlayer, walls) ) {
						// Jump right
							jump = i.jumpRight(currPlayer);
							//if ( walls.containsKey(jump) ) {
							if ( jump.inBetweenWall(otherPlayer, walls) ) {
								continue;
							}
						}
					}
				// Check if jump is in the board
					if ( jump.inBoard() ) {
						playerMoves.add(jump);
					}
				}
			// else its a possible move
				else {
				// Check if wall is not blocking
					//if ( !(walls.containsKey(i)) )
					if ( !(i.inBetweenWall ( currPlayer, walls )) )
						playerMoves.add(i);
				}
			}
		}
		return playerMoves;
	}
	
// Get Neighbours List
	public LinkedList<Node> getNeighbourNodes ( Node current ) {
		int x = current.col;
		int y = current.row;
		LinkedList<Node> neighbourNodes = new LinkedList<Node>();
	// Add up of current node
		if ( y > 0 ) {
			neighbourNodes.add( new Node (x,y-1) );
		}
	// Add down of current node
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
		return neighbourNodes;
	}
}