package quoridor;

import java.util.ArrayList;

public class AllMoves extends AbstractMove {
	
	private Move pMoveGen;
	private Move wMoveGen;
	
	public AllMoves(Board boardState) {
		super(boardState);
		pMoveGen = new PlayerMove(boardState);
		wMoveGen = new WallMove(boardState);
	}

	public void GenerateMoves(ArrayList<Tile> moveList) {
	}

	public boolean validateMove(Tile move) {
		if ( move instanceof WallTile)
			return wMoveGen.validateMove ( move );
		else
			return pMoveGen.validateMove ( move );
	}

}
