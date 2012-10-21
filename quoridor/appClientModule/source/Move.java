package quoridor;

import java.util.ArrayList;

/**
 * Move interface for wall and player movements
 *
 */
public interface Move {
	/**
	 * Generate all possible moves and stores in ArrayList of moves
	 * @param moveList
	 * 	To store moves
	 */
	public void GenerateMoves ( ArrayList<Tile> moveList );
	/**
	 * Apply wall or player move
	 * @param player
	 * 	Player to apply move
	 * @param move
	 * 	Move to be applied
	 */
	public void applyMove ( PlayerTile player, Tile move  );
	/**
	 * Undo wall or player move
	 * @param player
	 * 	Player to undo move
	 * @param move
	 * 	Move to undo
	 */
	public void undoMove ( PlayerTile player, Tile move  );
	/**
	 * Checks if move is valid
	 * @param move
	 * 	Move to be checked
	 * @return
	 * 	Validity of move
	 */
	public boolean validateMove ( Tile move );
	/**
	 * To convert move in String format to Tile format 
	 * @param move
	 * 	Move in string
	 * @return
	 * 	Move as Tile
	 */
	public Tile moveParser ( String move );
}