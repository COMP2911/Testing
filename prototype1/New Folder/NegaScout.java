import java.util.LinkedList;
import java.util.Map;

public class NegaScout {
	private Map<Node, Node> walls;
	private Node otherPlayer;
	
	public NegaScout (Map<Node, Node> walls, Node otherPlayer) {
		this.walls = walls;
		this.otherPlayer = otherPlayer;
	}
	
	public void changeOtherPlayer (Node otherPlayer) {
		this.otherPlayer = otherPlayer;
	}
	
	public Double runNegaScout ( LinkedList<Node> parent, Integer depth, double alpha, double beta, int colour ) {
		if ( parent.size() == 1 || depth == 0 ) {
			Integer d = (colour * parent.size());
			return d.doubleValue();
		}
		double b = beta;
		Node otherPlayer = colour < 0 ? player2 : player1; 
		for ( Node child : parent ) {
			MoveGeneration m = new PlayerMoveGeneration();
			LinkedList<Node> newMoves = m.GenerateMoves ( child, otherPlayer, walls );
			m = new WallMoveGeneration();
			newMoves.addAll(m.GenerateMoves ( child, otherPlayer, walls ));
			double score = -runNegaScout ( newMoves, depth-1, -b, -alpha, -colour );
			if ( alpha < score && score < beta && child != parent.getFirst() ) {
			}
			alpha = Math.max ( alpha, score );
			if ( alpha >= beta ) {
				return alpha;
			}
			b = alpha + 1;			
		}
		return alpha;
	}
	
	public Double runNegaMax ( LinkedList<Node> currPlayerMoves, Integer depth, double alpha, double beta, int colour ) {
		if ( parent.size() == 1 || depth == 0 ) {
			Integer d = (colour * parent.size());
			return d.doubleValue();
		}
		double b = beta;
		//Node otherPlayer = colour < 0 ? player2 : player1; 
		for ( Node move : currPlayerMoves ) {
			applyMove ( move, walls );
			MoveGeneration m = new PlayerMoveGeneration();
			LinkedList<Node> newMoves = m.GenerateMoves ( move, otherPlayer, walls );
			m = new WallMoveGeneration();
			newMoves.addAll(m.GenerateMoves ( move, otherPlayer, walls ));
			double score = -runNegaMax ( newMoves, depth-1, -b, -alpha, -colour );
			undoMove ( move, walls );
			if ( alpha < score && score < beta && child != parent.getFirst() ) {
			}
			alpha = Math.max ( alpha, score );
			if ( val >= beta ) {
				return val;
			}
			if ( val >= alpha ) {
				alpha = val;
			}		
		}
		return alpha;
	}
	
	
	public void doBestMove ( LinkedList<Node> currPlayerMoves, 
	Map<Node, Node> walls,
	Node currPlayer,
	Node otherPlayer ) {
		Node bestMove = null;
		double alpha = Double.POSITIVE_INFINITY;
		for ( Node move : currPlayerMoves ) {
			applyMove ( move, walls );
			MoveGeneration m = new PlayerMoveGeneration();
			LinkedList<Node> newMoves = m.GenerateMoves ( move, otherPlayer, walls );
			m = new WallMoveGeneration();
			newMoves.addAll(m.GenerateMoves ( move, otherPlayer, walls ));
			double score = -runNegaMax ( newMoves, depth-1, -b, -alpha, -colour );
			undoMove ( move, walls );
			if ( score <= alpha ) {
				bestMove = move;
				alpha = score;
			}
		}
		permApplyMove( bestMove, walls, currPlayer );
		// need to deduct wall
	}
	
	public void applyMove ( Node move, Map<WallNode, WallNode> walls ) {
		if ( move instanceof WallNode ) {
			walls.put ( (WallNode)move, (WallNode)move );
			walls.put ( (WallNode)move.getOther(), (WallNode)move.getOtherPart() );
		}
	}
	
	public void permApplyMove ( Node move, Map<WallNode, WallNode> walls, Node currPlayer ) {
		if ( move instanceof WallNode ) {
			applyMove ( move, walls );
		}
		else {
			currPlayer = move;
		}
	}
	
	public void undoMove ( Node move, Map<WallNode, WallNode> walls, Node currPlayer ){
		if ( move instanceof WallNode ) {
			walls.remove ( (WallNode)move );
			walls.remove ( (WallNode)move.getOtherPart() );
		}
	}
	
	public void getCost ( Map<WallNode, WallNode> walls, Node currPlayer, Node otherPlayer ){
		DjikstraPathfinding DPF = new  DjikstraPathfinding();
		int currPlayerLength  = DPF.runDjikstra ( currPlayer,  ((PlayerNode)otherPlayer).getGoal() );
		int otherPlayerLength = DPF.runDjikstra ( otherPlayer, ((PlayerNode)currPlayer).getGoal() );
	}
}