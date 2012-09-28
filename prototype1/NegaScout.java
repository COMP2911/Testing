import java.util.LinkedList;
import java.util.Map;

public class NegaScout {
	public NegaScout () {
	}
	/*
	public Double runNegaScout ( LinkedList<Node> currPlayerMoves, Integer depth, double alpha, double beta, int colour ) {
		if ( parent.size() == 1 || depth == 0 ) {
			Integer d = (colour * parent.size());
			return d.doubleValue();
		}
		double b = beta;
		Node otherPlayer = colour < 0 ? player2 : player1; 
		for ( Node move : currPlayerMoves ) {
			MoveGeneration m = new PlayerMoveGeneration();
			LinkedList<Node> newMoves = m.GenerateMoves ( child, otherPlayer, walls );
			m = new WallMoveGeneration();
			newMoves.addAll(m.GenerateMoves ( child, otherPlayer, walls ));
			double score = -runNegaScout ( newMoves, depth-1, -b, -alpha, -colour );
			if ( alpha < score && score < beta && move != currPlayerMoves.getFirst() ) {
			}
			alpha = Math.max ( alpha, score );
			if ( alpha >= beta ) {
				return alpha;
			}
			b = alpha + 1;			
		}
		return alpha;
	}
	*/
	
	public Double runMinMax ( Map<WallNode, WallNode> walls, Node currPlayer, Node otherPlayer, int depth ) {
		if ( depth <= 0 ) {
		// positive values are good for the maximizing player
		// negative values are good for the minimizing player
			Integer iResult = getCostOfOne ( walls, currPlayer );
			Double result = iResult.doubleValue();
			return ( ((PlayerNode)currPlayer).getPlayerNum() == 1 ) ? result : -result;
		}
		
		// maximizing player is (+1)
		// minimizing player is (-1)
		double alpha = ( ((PlayerNode)currPlayer).getPlayerNum() == 1 ) ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
		
		LinkedList<Node> allSubMoves = generateAllMoves( currPlayer, otherPlayer, walls );
		for ( Node move : allSubMoves ) {
			if ( !applyMove ( move, (PlayerNode)currPlayer, walls ) )
				continue;
			double score = runMinMax ( walls, otherPlayer, currPlayer, depth-1 );
			undoMove ( move, (PlayerNode)currPlayer, walls );
			alpha = ( ((PlayerNode)currPlayer).getPlayerNum() == 1 ) ? Math.max( alpha, score ) : Math.min( alpha, score );
		}
		
		return alpha;
	}
	
	public Double runAlphaBeta ( Map<WallNode, WallNode> walls, Node currPlayer, Node otherPlayer, int depth, double alpha, double beta ) {
		LinkedList<Node> allSubMoves = generateAllMoves( currPlayer, otherPlayer, walls );
		
		if ( allSubMoves.isEmpty() || depth == 0 || ((PlayerNode)currPlayer).hasWon() ) {
			Integer iResult = getCostOfOne ( walls, currPlayer );
			Double result = iResult.doubleValue();
			return ( ((PlayerNode)currPlayer).getPlayerNum() == 1 ) ? result : -result;
		}
		
		// Max Player
		if ( ((PlayerNode)currPlayer).getPlayerNum() == 1 ) {
			for ( Node move : allSubMoves ) {
				if ( !applyMove ( move, (PlayerNode)currPlayer, walls ) )
					continue;
					
				alpha = Math.max ( alpha, runAlphaBeta( walls, otherPlayer, currPlayer, depth-1, beta, alpha ) );
				
				undoMove ( move, (PlayerNode)currPlayer, walls );
				if ( beta <= alpha ) {
					break;
				}		
			}
			return alpha;
		}
		// Min Player
		else {
			for ( Node move : allSubMoves ) {
				if ( !applyMove ( move, (PlayerNode)currPlayer, walls ) )
					continue;
					
				alpha = Math.min ( beta, runAlphaBeta( walls, otherPlayer, currPlayer, depth-1, beta, alpha ) );
				
				undoMove ( move, (PlayerNode)currPlayer, walls );
				if ( beta <= alpha ) {
					break;
				}		
			}
			return beta;
		}
	}
	
	public Double runNegaMax ( Map<WallNode, WallNode> walls, Node currPlayer, Node otherPlayer, int depth, double alpha, double beta ) {
		if ( depth == 0 || ((PlayerNode)currPlayer).hasWon() ) {
		    Integer result = -getCost( walls, currPlayer, otherPlayer );
			return result.doubleValue();
		}
		LinkedList<Node> allSubMoves = generateAllMoves( currPlayer, otherPlayer, walls );
		if ( allSubMoves.isEmpty() ) {
		    Integer result = -getCost( walls, currPlayer, otherPlayer );
			return result.doubleValue();
		}
		Node tempNode = null;
		//Node otherPlayer = colour < 0 ? player2 : player1; 
		for ( Node move : allSubMoves ) {
			if ( !applyMove ( move, (PlayerNode)currPlayer, walls ) )
				continue;
			double val = -runNegaMax ( walls, otherPlayer, currPlayer, depth-1, -beta, -alpha );
			undoMove ( move, (PlayerNode)currPlayer, walls );

			if ( val >= beta ) {
				return val;
			}
			if ( val >= alpha ) {
				alpha = val;
				tempNode = move;
			}		
		}
		return alpha;
	}
	
	
	public Node doBestMove ( Map<WallNode, WallNode> walls, Node currPlayer, Node otherPlayer ) {
		Node bestMove = null;
		double alpha = Double.NEGATIVE_INFINITY;
		double beta = Double.POSITIVE_INFINITY;
		LinkedList<Node> allMoves = generateAllMoves( currPlayer, otherPlayer, walls );
		long time = System.currentTimeMillis() ;
		getCost( walls, currPlayer, otherPlayer );
		for ( Node move : allMoves ) {
			if ( !applyMove ( move, (PlayerNode)currPlayer, walls ) )
				continue;
			double val = -runNegaMax ( walls, otherPlayer, currPlayer, 3, -beta, -alpha );
			//double val = runAlphaBeta ( walls, otherPlayer, currPlayer, 1, alpha, beta );
			undoMove ( move, (PlayerNode)currPlayer, walls );
			if (val > alpha || alpha == Double.NEGATIVE_INFINITY) {
				bestMove = move;
				alpha = val;
			}
			/*
			if ( val <= alpha ) {
				bestMove = move;
				alpha = val;
			}*/
		}
		System.out.println("Time spent: " + ((System.currentTimeMillis()-time)/1000) + " seconds");
		applyBestMove( bestMove, (PlayerNode)currPlayer, walls  );
		// need to deduct wall
		return bestMove;
	}
	
	public LinkedList<Node> generateAllMoves ( Node currPlayer, Node otherPlayer, Map<WallNode, WallNode> walls ) {
		long time = System.currentTimeMillis() ;
		MoveGeneration m;
		LinkedList<Node> allMoves = new LinkedList<Node>();
		if ( ((PlayerNode)currPlayer).hasWallLeft() ) {
			m = new WallMoveGeneration();
			allMoves.addAll( m.GenerateMoves ( currPlayer, otherPlayer, walls ) );
		}
		m = new PlayerMoveGeneration();
		allMoves.addAll( m.GenerateMoves ( currPlayer, otherPlayer, walls ) );
		System.out.println("Time spent: " + ((System.currentTimeMillis()-time)) + " miliseconds");
		return allMoves;
	}
	
	public boolean applyMove ( Node move, PlayerNode currPlayer, Map<WallNode, WallNode> walls ) {
		if ( move instanceof WallNode ) {
			currPlayer.putTempWall ( (WallNode)move, walls );
		}
		else {
			return currPlayer.setNewPos ( move );
		}
		return true;
	}
	
	public void applyBestMove ( Node move, PlayerNode currPlayer, Map<WallNode, WallNode> walls ) {
		if ( move instanceof WallNode ) {
			currPlayer.putWall ( (WallNode)move, walls );
		}
		else {
			currPlayer.setNewPos2 ( move );
		}
	}
	
	public void undoMove ( Node move, PlayerNode currPlayer, Map<WallNode, WallNode> walls ){
		if ( move instanceof WallNode ) {
			currPlayer.removeTempWall ( (WallNode)move, walls );
		}
		else {
			currPlayer.setOldPos ();
		}
	}
	
	public Integer getCost ( Map<WallNode, WallNode> walls, Node currPlayer, Node otherPlayer ){
		DijkstraPathfinding DPF = new  DijkstraPathfinding();
		int currPlayerLength  = (DPF.runDijkstra ( currPlayer,  ((PlayerNode)currPlayer).getGoal(), walls )).size();
		int otherPlayerLength = (DPF.runDijkstra ( otherPlayer, ((PlayerNode)otherPlayer).getGoal(), walls )).size();
		return currPlayerLength - otherPlayerLength;
	}
	
	public Integer getCostOfOne ( Map<WallNode, WallNode> walls, Node currPlayer ){
		DijkstraPathfinding DPF = new  DijkstraPathfinding();
		int currPlayerLength  = (DPF.runDijkstra ( currPlayer,  ((PlayerNode)currPlayer).getGoal(), walls )).size();
		return currPlayerLength;
	}
}