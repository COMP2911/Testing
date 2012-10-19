package quoridor;

import java.util.ArrayList;

public interface Move {
	public void GenerateMoves ( ArrayList<Tile> moveList );
	public void applyMove ( PlayerTile player, Tile move  );
	public void undoMove ( PlayerTile player, Tile move  );
	public boolean validateMove ( Tile move );
	public Tile moveParser ( String move );
}