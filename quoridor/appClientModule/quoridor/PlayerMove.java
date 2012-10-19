package quoridor;

import java.util.ArrayList;

public class PlayerMove extends AbstractMove {
	
	public PlayerMove ( Board boardState ) {
		super (boardState);
	}

	public boolean validateMove ( Tile move ) {
		if ( currPlayer.hasWon() || otherPlayer.hasWon() )
			return false;
		if ( move instanceof WallTile ) return false;
		ArrayList<Tile> moveList = new ArrayList<Tile>();
		GenerateMoves ( moveList );
		return moveList.contains ( move );
	}
	
	public void GenerateMoves ( ArrayList<Tile> moveList ) {
		for ( Tile move : getNeighbourNodes ( currPlayer, currPlayer.getGoal() ) ) {
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
	
	protected Tile jump () {		
		//int tempRow = otherRow;
		//int tempCol = otherCol;
		
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
	
	protected Tile jumpLeft () {			
		//int tempRow = otherRow;
		//int tempCol = otherCol;
		
		int currRow = currPlayer.getRow();
		int currCol = currPlayer.getCol();
		
		int otherRow = otherPlayer.getRow();
		int otherCol = otherPlayer.getCol();
		
		if ( currRow != otherRow ) otherCol -= 1;
		else if ( currCol != otherCol )	otherRow -= 1;
		
		return new Tile ( otherCol, otherRow );
	}
	
	protected Tile jumpRight () {			
		//int tempRow = otherRow;
		//int tempCol = otherCol;
		
		int currRow = currPlayer.getRow();
		int currCol = currPlayer.getCol();
		
		int otherRow = otherPlayer.getRow();
		int otherCol = otherPlayer.getCol();
		
		if ( currRow != otherRow ) otherCol += 1;
		else if ( currCol != otherCol ) otherRow += 1;
		
		return new Tile ( otherCol, otherRow );
	}

    // Get Neighbours List
	protected Tile[] getNeighbourNodes ( Tile current, int goalRow ) {
		int x = current.getCol();
		int y = current.getRow();
		
		if ( goalRow == 8 ) {
			Tile[] neighbourNodes = { new Tile (x,y+1), new Tile (x-1,y), new Tile (x+1,y), new Tile (x,y-1) };
			return neighbourNodes;
		}
		else {
			Tile[] neighbourNodes = { new Tile (x,y-1), new Tile (x-1,y), new Tile (x+1,y), new Tile (x,y+1) };
			return neighbourNodes;
		}
	}
}