package quoridor;

import java.util.ArrayList;

public interface Move {
	/**
	 * Generate all possible moves and stores in ArrayList of moves
	 * @param moveList
	 * 	To store moves
	 */
	public void GenerateMoves ( ArrayList<Tile> moveList );
	/**
	 * Apply move to player on current board state
	 * @param player
	 * 	Player to apply move on
	 * @param move
	 * 	Move to be applied
	 */
	public void applyMove ( PlayerTile player, Tile move  );
	/**
	 * ??Undo move??
	 * @param player
	 * @param move
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