import java.util.*;

public class HashMapTest {
	final static int pX = 4;
	final static int pY = 0;
	final static int opX = 4;
	final static int opY = 8;
	public static void setWalls ( Map<Node, Node> closedNodes ) {
		closedNodes.clear();
		Node i = new Node (4, 1);
		closedNodes.put( i, i );
		i = new Node (3, 1);
		closedNodes.put( i, i );
		i = new Node (5, 1);
		closedNodes.put( i, i );
		i = new Node (2, 1);
		closedNodes.put( i, i );
		i = new Node (6, 1);
		closedNodes.put( i, i );
		i = new Node (7, 1);
		closedNodes.put( i, i );
		i = new Node (8, 1);
		closedNodes.put( i, i );
		i = new Node (1, 1);
		closedNodes.put( i, i );
	}
	public static void setWalls2 ( Map<WallNode, WallNode> closedNodes ) {
		closedNodes.clear();
	// Test move up block
		WallNode i = new WallNode (4, 0, 'h');
		closedNodes.put( i, i );
		i = new WallNode (5, 0, 'h');
		closedNodes.put( i, i );
	}
	public static void setWalls3 ( Map<WallNode, WallNode> closedNodes ) {
		closedNodes.clear();
	// Test Jump Left
		WallNode i = new WallNode (4, 1, 'h');
		closedNodes.put( i, i );
		i = new WallNode (5, 1, 'h');
		closedNodes.put( i, i );
	}
	public static void setWalls4 ( Map<WallNode, WallNode> closedNodes ) {
	// Test Jump Right
		WallNode i = new WallNode (3, 1, 'v');
		closedNodes.put( i, i );
	}
	public static void setWalls5 ( Map<WallNode, WallNode> closedNodes ) {
	// No Jump
		WallNode i = new WallNode (4, 1, 'v');
		closedNodes.put( i, i );
	}
	public static void setWalls6 ( Map<WallNode, WallNode> closedNodes ) {
		closedNodes.clear();
	// Test Djikstra
		for (int a = 1; a < 9; ++a ) {
			WallNode i = new WallNode (a, 0, 'h');
			closedNodes.put( i, i );
			i = new WallNode (a, 2, 'h');
			closedNodes.put( i, i );
			i = new WallNode (a, 4, 'h');
			closedNodes.put( i, i );
			i = new WallNode (a, 6, 'h');
			closedNodes.put( i, i );
			i = new WallNode (a, 8, 'h');
			closedNodes.put( i, i );
		}
		for (int a = 0; a < 8; ++a ) {
			WallNode i = new WallNode (a, 1, 'h');
			closedNodes.put( i, i );
			i = new WallNode (a, 3, 'h');
			closedNodes.put( i, i );
			i = new WallNode (a, 5, 'h');
			closedNodes.put( i, i );
			i = new WallNode (a, 7, 'h');
			closedNodes.put( i, i );
		}
	}
	
	public static void setWalls7 ( Map<WallNode, WallNode> closedNodes ) {
		closedNodes.clear();
	// Test Djikstra
		for (int a = 0; a < 8; ++a ) {
			WallNode i = new WallNode (a, 0, 'v');
			closedNodes.put( i, i );
			i = new WallNode (a, 1, 'v');
			closedNodes.put( i, i );
			i = new WallNode (a, 2, 'v');
			closedNodes.put( i, i );
		}
		for (int a = 0; a < 8; ++a ) {
			WallNode i = new WallNode (a, 1, 'h');
			closedNodes.put( i, i );
			i = new WallNode (a, 3, 'h');
			closedNodes.put( i, i );
			i = new WallNode (a, 5, 'h');
			closedNodes.put( i, i );
			i = new WallNode (a, 7, 'h');
			closedNodes.put( i, i );
		}
	}
	
	public static void main ( String[] args ) {
	/*	System.out.println("\nA* Pathfinding");
		AStarPathfinding ASPF = new AStarPathfinding();
		Map<Node, Node> closedNodes2 = new HashMap<Node, Node>();
		//setWalls(closedNodes2);
		Node start = new Node ( pX, pY );
		Node goal = new Node ( opX, opY );
		for ( Node i : ASPF.runAStar(start, goal, closedNodes2) ) {
			i.print();
		}
		
		System.out.println("\nDijkstra Pathfinding");
		DijkstraPathfinding DPF = new DijkstraPathfinding();
		Map<WallNode, WallNode> closedNodes = new HashMap<WallNode, WallNode>();
		start = new Node ( pX, pY );
		goal = new Node ( opX, opY );
		setWalls6(closedNodes);
		System.out.println("Player 1");
		for ( Node i : DPF.runDijkstra(start, goal, closedNodes) ) {
			i.print();
		}
	
		System.out.println("\nPlayer 2");
		for ( Node i : DPF.runDijkstra(goal, start, closedNodes) ) {
			i.print();
		}
		
		System.out.println("\nPlayer Move Generation");
		Node player = new PlayerNode(pX, pY, 1);
		Node otherPlayer = new PlayerNode(opX, opY, 2);
		closedNodes = new HashMap<WallNode, WallNode>();
		System.out.println("\nTest");
		MoveGeneration moves = new PlayerMoveGeneration();
		for ( Node i : moves.GenerateMoves(player, otherPlayer, closedNodes) ) {
			i.print();
		}
		System.out.println("\nTest block up");
		setWalls2(closedNodes);
		for ( Node i : moves.GenerateMoves(player, otherPlayer, closedNodes) ) {
			i.print();
		}
		System.out.println("\nTest jump left");
		setWalls3(closedNodes);
		for ( Node i : moves.GenerateMoves(player, otherPlayer, closedNodes) ) {
			i.print();
		}
		System.out.println("\nTest jump right");
		setWalls4(closedNodes);
		for ( Node i : moves.GenerateMoves(player, otherPlayer, closedNodes) ) {
			i.print();
		}
		System.out.println("\nTest no jump");
		setWalls5(closedNodes);
		for ( Node i : moves.GenerateMoves(player, otherPlayer, closedNodes) ) {
			i.print();
		}
		
		System.out.println("\nWall Move Generation");
		moves = new WallMoveGeneration();
		setWalls6(closedNodes);
		for ( Node i : moves.GenerateMoves(player, otherPlayer, closedNodes) ) {
			WallNode i2 = (WallNode)i;
			i2.print();
		}
		
		System.out.println("\nAll Possible Moves\ncurrPlayer");
		moves = new WallMoveGeneration();
		setWalls6(closedNodes);
		for ( Node i : moves.GenerateMoves(player, otherPlayer, closedNodes) ) {
			WallNode i2 = (WallNode)i;
			i2.print();
		}
		moves = new PlayerMoveGeneration();
		for ( Node i : moves.GenerateMoves(player, otherPlayer, closedNodes) ) {
			i.print();
		}
		System.out.println("otherPlayer");
		moves = new WallMoveGeneration();
		setWalls6(closedNodes);
		for ( Node i : moves.GenerateMoves(player, otherPlayer, closedNodes) ) {
			WallNode i2 = (WallNode)i;
			i2.print();
		}
		moves = new PlayerMoveGeneration();
		for ( Node i : moves.GenerateMoves(otherPlayer, player, closedNodes) ) {
			i.print();
		}
		*/
		System.out.println("\nTest AI");
		PlayerNode p1 = new PlayerNode (pX, pY, 1);
		PlayerNode p2 = new PlayerNode (opX, opY, 2);
		Map<WallNode, WallNode> walls = new HashMap<WallNode, WallNode>();
		NegaScout AINS = new NegaScout();
		Node bestMove;
		int blackTurn = 1, whiteTurn = 1, round = 1;
		while ( !p1.hasWon() && !p2.hasWon() ) {
			System.out.println("########## Round " + round + " ##########");
			System.out.println("Black Player " + blackTurn);
			System.out.println("Walls Left: " + ((PlayerNode)p1).getWallsLeft());
			System.out.println("Thinking of move...");
			bestMove = AINS.doBestMove ( walls, p1, p2 );
			System.out.println( "Move: " + GlobalConstants.getMoveInString(bestMove) );
			Display( walls, p1, p2 );
			++blackTurn;
			System.out.println("White Player " + whiteTurn);
			System.out.println("Walls Left: " + ((PlayerNode)p2).getWallsLeft());
			System.out.println("Thinking of move...");
			bestMove = AINS.doBestMove ( walls, p2, p1 );
			System.out.println( "Move: " + GlobalConstants.getMoveInString(bestMove) );
			Display( walls, p1, p2 );
			++whiteTurn;
			System.out.println("########## Round " + round + " End ##########");
			++round;
		}
		if ( p1.hasWon() )
			System.out.println("Black Player has won the game!!!");
		else if ( p2.hasWon() )
			System.out.println("White Player has won the game!!!");
			
	}
	
	public static void Display (Map<WallNode, WallNode> walls, PlayerNode p1, PlayerNode p2) {
		final String evenEmpty = "   ";
		final String evenBlack = " B ";
		final String evenWhite = " W ";
		final String evenNoWall = "|";
		final String evenWall = "=";
		
		final String oddNoWall = "---";
		final String oddWall = "===";
		final String oddBetween = "+";
		
		final String colGrid = "      0   1   2   3   4   5   6   7   8\n";
		final String border  = "    *************************************\n";
		
		String[][] boardArray = new String[17][9];
		for ( int a = 0; a < 17; ++a ) {
			for ( int b = 0; b < 9; ++b ) {
				if ( a%2 == 0 ) {
					boardArray[a][b] = evenEmpty;
					if ( b < 8 ) {
						boardArray[a][b] += evenNoWall;
					}
				}
				else {
					boardArray[a][b] = oddNoWall;
					if ( b < 8 ) {
						boardArray[a][b] += oddBetween;
					}
				}
			}
		}
		int rowPos;
		int colPos;
		for ( WallNode i : walls.values() ) {
			int col = i.col;
			if ( i.getOrientation() == 'v' ) {
				// +0(v)/1(h) - row
				// +4(v)/0(h)
				int row = Math.abs (i.row - 8);
				rowPos = row*2;
				colPos = col;
				boardArray[rowPos][colPos] = boardArray[rowPos][colPos].replace( evenNoWall, evenWall );
			}
			else {
				int row = Math.abs (i.row - 7);
				rowPos = row*2+1;
				colPos = col;
				boardArray[rowPos][colPos] = boardArray[rowPos][colPos].replace( oddNoWall, oddWall );
			}
		}
		rowPos = Math.abs ( p1.row - 8 )*2;
		colPos = p1.col;
		boardArray[rowPos][colPos] = boardArray[rowPos][colPos].replace( evenEmpty, evenBlack );
		rowPos = Math.abs ( p2.row - 8 )*2;
		colPos = p2.col;
		boardArray[rowPos][colPos] = boardArray[rowPos][colPos].replace( evenEmpty, evenWhite );
		
		System.out.print (colGrid+border);
		char letter = 'i';
		for ( int a = 0; a < 17; ++a ) {
			if ( a%2 == 0 ) { 
				System.out.print (letter + "   *");
			}
			else {
				System.out.print ("    *");
			}
			for ( int b = 0; b < 9; ++b ) {
				System.out.print( boardArray[a][b] );
			}
			if ( a%2 == 0 ) { 
				System.out.print ("*   " + letter + "\n");
				letter -= 1;
			}
			else {
				System.out.print("*\n");
			}
		}
		System.out.print (border+colGrid);
	}
}