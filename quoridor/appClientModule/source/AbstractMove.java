package quoridor;

import java.util.ArrayList;

/**
 * Template class of Move
 * Controls all movement here.
 * Including parsing move string to game move, applying move and undo move
 */
public abstract class AbstractMove implements Move {
	/**
	 * Current Player of current board state
	 */
	protected PlayerTile currPlayer;
	/**
	 * Other Player of current board state
	 */
	protected PlayerTile otherPlayer;
	/**
	 * Walls of current board state
	 */
	protected WallTile[][] walls;
	
	/**
	 * Sets the current player, other player and walls for internal functions to use.
	 * @param boardState
	 * 	Current board state
	 */
	public AbstractMove ( Board boardState ){
		currPlayer = boardState.getCurrentPlayer();
		otherPlayer = boardState.getOtherPlayer();
		
		walls = boardState.getWalls();
	}

	/* (non-Javadoc)
	 * @see quoridor.Move#applyMove(quoridor.PlayerTile, quoridor.Tile)
	 */
	public void applyMove ( PlayerTile player, Tile move ) {
		if ( move instanceof WallTile )
			player.addWall ( (WallTile)move, walls );
		else
			player.setPos( move );
	}

	/* (non-Javadoc)
	 * @see quoridor.Move#undoMove(quoridor.PlayerTile, quoridor.Tile)
	 */
	public void undoMove ( PlayerTile player, Tile move  ) {
		if ( move instanceof WallTile )
			player.removeWall ( (WallTile)move, walls );
		else
			player.setPos( move );
	}
	
	/* (non-Javadoc)
	 * @see quoridor.Move#moveParser(java.lang.String)
	 */
	public Tile moveParser ( String moveString ) {
		if ( moveString.length() == 3 ) {
			Integer col = (moveString.charAt(0) - 'a');
			Integer row = (moveString.charAt(1) - '1');
			Character orientation = moveString.charAt(2);
			if ( orientation != 'v' && orientation != 'h' )
				return null;
			return new WallTile ( col, row, orientation );
		}
		else if ( moveString.length() == 2 ) {
			Integer col = (moveString.charAt(0) - 'a');
			Integer row = (moveString.charAt(1) - '1');
			return new Tile ( col, row );
		}
		return null;
	}
	
	public abstract void GenerateMoves ( ArrayList<Tile> moveList);
	public abstract boolean validateMove ( Tile move );
}