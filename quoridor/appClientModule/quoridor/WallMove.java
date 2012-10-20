package quoridor;

import java.util.ArrayList;

/**
 * Generate and Validate wall moves
 *
 */
public class WallMove extends AbstractMove {	

	private AStarPathfinding AStar;
	
	/**
	 * Constructor to initialise A* pathfinding for wall move generation and current board state
	 * @param boardState
	 * 	 Current board state
	 */
	public WallMove ( Board boardState ) {
		super (boardState);
		AStar = new AStarPathfinding();
	}
	
	/**
	 * Generate all possible wall moves of current move
	 * @param moveList
	 * 	All possible wall moves
	 */
	public void GenerateMoves ( ArrayList<Tile> moveList ) {
		for ( int a = 0; a < (Globals.MAX_ROW-1); ++a ) {
		// 8 ways of placing a wall on a col when board is empty
			for ( int b = 0; b < (Globals.MAX_COL-1); ++b ) {
			// Check for vertical wall
				WallTile wallMove = new WallTile ( b, a, 'v'); 
				if ( validateMove ( wallMove ) )
					moveList.add ( wallMove );
			// Check for horizontal wall
				wallMove = new WallTile ( b, a, 'h' ); 
				if ( validateMove ( wallMove ) )
					moveList.add ( wallMove );
			}
		}
	}

	/**
	 * Check if wall move is valid
	 * @param move
	 * 	Wall move to be checked
	 * @return
	 * 	Validity of wall move
	 */
	public boolean validateMove ( Tile move ) {
		if ( currPlayer.hasWon() || otherPlayer.hasWon() || !currPlayer.hasWallLeft() )
			return false;
		WallTile wallMove = (WallTile)move;
		if ( !wallMove.inBoard() )	return false;
		
		Integer newCol = move.getCol();
		Integer newRow = move.getRow();
					
		if ( wallMove.hasHorizontal() ) {
			// If wall exist OR
			// If other part of wall exist
			if ( walls[newRow][newCol].hasHorizontal() || 
					walls[newRow][newCol+1].hasHorizontal() )
				return false;
			// If wall crosses another wall
			if ( walls[newRow][newCol].hasVertical() )
				return false;
			applyMove ( currPlayer, move );
			// Check if wall blocks path, add to moves if it does not
			boolean result = (AStar.pathFound( currPlayer, currPlayer.getGoal(), walls ) &
					AStar.pathFound( otherPlayer, otherPlayer.getGoal(), walls ) );
			undoMove ( currPlayer, move );
			return result;
		}
		else {
			// If wall exist OR
			// If other part of wall exist
			if ( walls[newRow][newCol].hasVertical() || 
					walls[newRow+1][newCol].hasVertical() )
				return false;
			// If wall crosses another wall
			if ( walls[newRow][newCol].hasHorizontal() &&
				 walls[newRow][newCol+1].hasHorizontal() )
				return false;
			applyMove ( currPlayer, move );
			// Check if wall blocks path, add to moves if it does not
			boolean result = (AStar.pathFound( currPlayer, currPlayer.getGoal(), walls ) &
					AStar.pathFound( otherPlayer, otherPlayer.getGoal(), walls ) );
			undoMove ( currPlayer, move );
			return result;
		}
	}
}