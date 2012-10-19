package quoridor;

import java.util.ArrayList;

/**
 * Controls all movement here.
 * Including parsing move string to game move, applying move and undo move
 */
public abstract class AbstractMove implements Move {
	protected PlayerTile currPlayer;
	protected PlayerTile otherPlayer;
	
	protected WallTile[][] walls;
	
	public AbstractMove ( Board boardState ){
		currPlayer = boardState.getCurrentPlayer();
		otherPlayer = boardState.getOtherPlayer();
		
		walls = boardState.getWalls();
	}

	public void applyMove ( PlayerTile player, Tile move ) {
		if ( move instanceof WallTile )
			player.putWall ( (WallTile)move, walls );
		else
			player.setPos( move );
	}

	public void undoMove ( PlayerTile player, Tile move  ) {
		if ( move instanceof WallTile )
			player.removeWall ( (WallTile)move, walls );
		else
			player.setPos( move );
	}
	
	public Tile moveParser ( String moveString ) {
		if ( moveString.length() == 3 ) {
			moveString = moveString.toLowerCase();
			Integer col = (moveString.charAt(0) - 'a');
			Integer row = (moveString.charAt(1) - '1');
			Character orientation = moveString.charAt(2);
			if ( orientation != 'v' && orientation != 'h' )
				return null;
			return new WallTile ( col, row, orientation );
		}
		else if ( moveString.length() == 2 ) {
			moveString = moveString.toLowerCase();
			Integer col = (moveString.charAt(0) - 'a');
			Integer row = (moveString.charAt(1) - '1');
			return new Tile ( col, row );
		}
		return null;
	}
	
	public abstract void GenerateMoves ( ArrayList<Tile> moveList);
	public abstract boolean validateMove ( Tile move );
}