package quoridor;

import java.util.ArrayList;

/**
 * Handles wall and player movements
 */
public class AllMoves extends AbstractMove {
	
	/**
	 * To generate player moves
	 */
	private Move pMoveGen;
	/**
	 * To generate wall moves 
	 */
	private Move wMoveGen;
	
	/**
	 * Constructor for All Moves to generate 
	 * @param boardState
	 */
	public AllMoves(Board boardState) {
		super(boardState);
		pMoveGen = new PlayerMove(boardState);
		wMoveGen = new WallMove(boardState);
	}

	/* (non-Javadoc)
	 * @see quoridor.AbstractMove#GenerateMoves(java.util.ArrayList)
	 */
	public void GenerateMoves(ArrayList<Tile> moveList) {
	}
	
	/* 
	 * Checks if its a wall move or just normal move.
	 * Validate accordingly to move passed in.
	 * (non-Javadoc)
	 * @see quoridor.AbstractMove#validateMove(quoridor.Tile)
	 */
	public boolean validateMove(Tile move) {
		if ( move instanceof WallTile)
			return wMoveGen.validateMove ( move );
		else
			return pMoveGen.validateMove ( move );
	}

}
