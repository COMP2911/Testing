package quoridor;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator class provided by LIC to test a given list of moves
 *
 */
public class Validator {
	/**
	 * Current board state
	 */
	Board boardState;
	// TODO complete this class using your project code
	// you must implement the no-arg constructor and the check method
	
	// you may add extra fields and methods to this class
	// but the ProvidedTests code only calls the specified methods
	
	public Validator() {
		// TODO
		boardState = new Board();
		
	}

	/**
	 * Check the validity of a given list of moves.
	 * Each move is represented by a string.
	 * The list is valid if and only if each move in the list is valid,
	 * after applying the preceding moves in the list, assuming they are valid,
	 * starting from the initial position of the game.
	 * When the game has been won, no further moves are valid.
	 * @param moves a list of successive moves
	 * @return validity of the list of moves
	 */
	public boolean check(List<String> moves) {
		// TODO
		List<Tile> moveList = new ArrayList<Tile>();
		for ( String str : moves ) {
			Tile i  = boardState.getMoveGen().moveParser(str);
			if ( i == null )
				return false;
			moveList.add ( i );
		}
		
		for ( Tile move : moveList ) {
			if ( boardState.getMoveGen().validateMove(move) ) {
				boardState.getMoveGen().applyMove ( boardState.getCurrentPlayer(), move );
			}
			else
				return false;
		// Switch turn
			boardState.switchCurrentPlayer();
		}
		return true;
	}

}
