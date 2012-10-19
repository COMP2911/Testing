package quoridor;

import java.util.ArrayList;

public class AllMoves extends AbstractMove {
	
	private Move pMoveGen;
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
