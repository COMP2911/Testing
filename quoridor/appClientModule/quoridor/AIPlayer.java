package quoridor;

import java.util.ArrayList;

/**
 * AI player class has MinMax algorithm and handles AI movement
 */
public class AIPlayer {
	private Integer minMaxDepth;
	private Tile bestMove;
	private Board boardState;
	
	/**
	 * Constructor of AI Player to initialise the depth for MinMax algorithm 
	 * @param depth
	 */
	public AIPlayer ( Integer depth ) {
		this.minMaxDepth = depth;
	}
	
	/**
	 * MinMax algorithm with alpha beta pruning
	 * @param depth
	 * 	Current depth of the MinMax
	 * @param alpha
	 * 	Lower bound
	 * @param beta
	 * 	Upper bound
	 * @return
	 * 	Cost of current state
	 */
	protected Double runAlphaBeta ( int depth, double alpha, double beta ) {
		if ( depth == this.minMaxDepth || boardState.gameOver() ) {
			return getCost( boardState );
		}
		PlayerTile currPlayer = boardState.getCurrentPlayer();
		// Max Player
		if ( currPlayer.getPlayerNum() == 1 ) {
			for ( Tile move : generateAllMoves( boardState ) ) {
				Move moveGen = boardState.getMoveGen();

				Tile oldPos = new Tile ( currPlayer.getCol(), currPlayer.getRow() );
				moveGen.applyMove ( currPlayer, move);

				boardState.switchCurrentPlayer();
				Double score = runAlphaBeta ( depth+1, alpha, beta );
				boardState.switchCurrentPlayer();
				
				if ( move instanceof WallTile )
					moveGen.undoMove( currPlayer, move);
				else
					moveGen.undoMove( currPlayer, oldPos);
				
				if (score > alpha) {
					alpha = score;
					if ( depth == 0 )
						bestMove = move;
				}
				if (beta <= alpha)
					break;	
			}
			return alpha;
		}
		// Min Player
		else {
			for ( Tile move : generateAllMoves( boardState ) ) {
				Move moveGen = boardState.getMoveGen();

				Tile oldPos = new Tile ( currPlayer.getCol(), currPlayer.getRow() );
				moveGen.applyMove ( currPlayer, move);

				boardState.switchCurrentPlayer();
				Double score = runAlphaBeta ( depth+1, alpha, beta );
				boardState.switchCurrentPlayer();
				
				if ( move instanceof WallTile )
					moveGen.undoMove( currPlayer, move);
				else
					moveGen.undoMove( currPlayer, oldPos);
				if (score < beta) {
					beta = score;
					if ( depth == 0 )
						bestMove = move;
				}
				if (beta <= alpha)
					break;	
			}
			return beta;
		}
	}

	/**
	 * NegaMax algorithm a variant of MinMax algorithm with Alpha Beta Pruning
	 * @param depth
	 * 	Current depth of the MinMax
	 * @param alpha
	 * 	Lower bound
	 * @param beta
	 * 	Upper bound
	 * @return
	 * 	Cost of current state
	 */
	protected Double runNegaMax ( int depth, double alpha, double beta ) {
		PlayerTile currPlayer = boardState.getCurrentPlayer();
		
		if ( depth == this.minMaxDepth || currPlayer.hasWon() ) {			
			return -getCost( boardState );
		}
		
		for ( Tile move : generateAllMoves( boardState ) ) {
			Move moveGen = boardState.getMoveGen();
			Tile oldPos = new Tile ( currPlayer.getCol(), currPlayer.getRow() );
			
			moveGen.applyMove ( currPlayer, move );
			boardState.switchCurrentPlayer();
			double score = -runNegaMax ( depth+1, -beta, -alpha );
			boardState.switchCurrentPlayer();
			
			if ( move instanceof WallTile )
				moveGen.undoMove ( currPlayer, move );
			else
				moveGen.undoMove ( currPlayer, oldPos );

            if (score > alpha) {
                alpha = score;
                if ( depth == 0 )
                	bestMove = move;
            }
            if (alpha >= beta)
                    break;
		}
		return alpha;
	}
	
	
	/**
	 * Get the best move for AI (NegaMax)
	 * @param boardState
	 * 	Current board state
	 * @return
	 * 	Best move
	 */
	public Tile getBestMove ( Board boardState ) {
		double alpha = Double.NEGATIVE_INFINITY;
		double beta = Double.POSITIVE_INFINITY;
		this.boardState = boardState;
		runNegaMax ( 0, -beta, -alpha );
		return bestMove;
	}

	/**
	 * Get the best move for AI (AlphaBeta)
	 * @param boardState
	 * 	Current board state
	 * @return
	 * 	Best move
	 */
	public Tile getBestMove2 ( Board boardState ) {
		double alpha = Double.NEGATIVE_INFINITY;
		double beta = Double.POSITIVE_INFINITY;
		this.boardState = boardState;
		runAlphaBeta ( 0, alpha, beta );
		return bestMove;
	}
	
	/**
	 * Generate all possible player and wall moves for the current player.
	 * Wall moves are only generated if the player has walls left to use and the other player has reached the 3rd row.
	 * @param boardState
	 * 	Current board state
	 * @return
	 * 	All possible moves
	 */
	protected ArrayList<Tile> generateAllMoves ( Board boardState ) {
		ArrayList<Tile> allMoves = new ArrayList<Tile>();
	// Generate Player Moves	
		Move pMoveGen = new PlayerMove (boardState);
		pMoveGen.GenerateMoves ( allMoves );
	// Generate Wall Moves
		if ( boardState.getCurrentPlayer().hasWallLeft() && boardState.getOtherPlayer().getDiff() < 7 ) {
			Move wMoveGen = new WallMove (boardState);
			wMoveGen.GenerateMoves ( allMoves );
		}
		return allMoves;
	}
	
	/**
	 * Calculate the heuristic cost of the current state.
	 * The difference between current and other players' length to goal. 
	 * The difference between current and other players' walls left.
	 * @param boardState
	 * 	Current board state
	 * @return
	 * 	Heuristic cost of current state
	 */
	protected Double getCost ( Board boardState ) {
		AStarPathfinding AStar = new  AStarPathfinding();
		int currPlayerLength  = AStar.movesLength ( boardState.getCurrentPlayer(),  boardState.getCurrentPlayer().getGoal(), boardState.getWalls() );
		int otherPlayerLength = AStar.movesLength ( boardState.getOtherPlayer(), boardState.getOtherPlayer().getGoal(), boardState.getWalls() );
		int wallDifference = boardState.getCurrentPlayer().getWallsLeft() - boardState.getOtherPlayer().getWallsLeft();
		int distance = (currPlayerLength - otherPlayerLength);
		Integer result = (distance + wallDifference);
		return result.doubleValue();
	}
}