package quoridor;

import java.util.ArrayList;

public class PlayerMove extends AbstractMove {
	
	/**
	 * Constructor to initialise current board state
	 * @param boardState
	 */
	public PlayerMove ( Board boardState ) {
		super (boardState);
	}

	/**
	 * Check if player move is valid
	 * @param move
	 * 	Move to be checked
	 * @return
	 * 	Validity of move
	 */
	public boolean validateMove ( Tile move ) {
		if ( currPlayer.hasWon() || otherPlayer.hasWon() )
			return false;
		ArrayList<Tile> moveList = new ArrayList<Tile>();
		GenerateMoves ( moveList );
		return moveList.contains ( move );
	}

	/**
	 * Generate all player moves
	 * @param moveList
	 * 	All possible player moves
	 */
	public void GenerateMoves ( ArrayList<Tile> moveList ) {
		for ( Tile move : getNeighbourTiles ( currPlayer, currPlayer.getGoal() ) ) {
		// Is move in board and wall not blocking???
			if ( move.inBoard() && !move.inBetweenWall ( currPlayer, walls ) ) {
			// If other player is in the spot
				if ( move.equals ( otherPlayer ) ) {	
				// Jump Over
					Tile jump = jump ();
					if ( jump.inBoard() && !jump.inBetweenWall ( otherPlayer, walls ) ) {
						moveList.add ( jump );
						continue;
					}
				// Jump Left
					jump = jumpLeft ();
					if ( jump.inBoard() && !jump.inBetweenWall ( otherPlayer, walls )  )
						moveList.add ( jump );
				// Jump Right
					jump = jumpRight ();
					if ( jump.inBoard() && !jump.inBetweenWall ( otherPlayer, walls ) )
						moveList.add ( jump );
					continue;
				}
				
				moveList.add ( move );
			}
		}
	}
	
	/**
	 * Does normal jump move
	 * @return
	 * 	Jump move
	 */
	protected Tile jump () {				
		int currRow = currPlayer.getRow();
		int currCol = currPlayer.getCol();
		
		int otherRow = otherPlayer.getRow();
		int otherCol = otherPlayer.getCol();
		
		if ( currRow < otherRow ) otherRow += 1;
		else if ( currRow > otherRow ) otherRow -= 1;
		else if ( currCol < otherCol ) otherCol += 1;
		else if ( currCol > otherCol ) otherCol -= 1;
		
		return new Tile ( otherCol, otherRow );
	}
	
	/**
	 * Does jump left move
	 * @return
	 * 	Jump left move
	 */
	protected Tile jumpLeft () {			
		int currRow = currPlayer.getRow();
		int currCol = currPlayer.getCol();
		
		int otherRow = otherPlayer.getRow();
		int otherCol = otherPlayer.getCol();
		
		if ( currRow != otherRow ) otherCol -= 1;
		else if ( currCol != otherCol )	otherRow -= 1;
		
		return new Tile ( otherCol, otherRow );
	}

	/**
	 * Does jump right move
	 * @return
	 * 	Jump right move
	 */
	protected Tile jumpRight () {			
		int currRow = currPlayer.getRow();
		int currCol = currPlayer.getCol();
		
		int otherRow = otherPlayer.getRow();
		int otherCol = otherPlayer.getCol();
		
		if ( currRow != otherRow ) otherCol += 1;
		else if ( currCol != otherCol ) otherRow += 1;
		
		return new Tile ( otherCol, otherRow );
	}

	/**
	 * Get the neighbours of the current tile, priority given to goal direction
	 * @param current
	 * 	Current tile
	 * @param goalRow
	 * 	Goal
	 * @return
	 * 	Neighbour tiles
	 */
	protected Tile[] getNeighbourTiles ( Tile current, int goalRow ) {
		int x = current.getCol();
		int y = current.getRow();
		
		if ( goalRow == 8 ) {
			Tile[] neighbourTiles = { new Tile (x,y+1), new Tile (x-1,y), new Tile (x+1,y), new Tile (x,y-1) };
			return neighbourTiles;
		}
		else {
			Tile[] neighbourTiles = { new Tile (x,y-1), new Tile (x-1,y), new Tile (x+1,y), new Tile (x,y+1) };
			return neighbourTiles;
		}
	}
}