package quoridor;

import java.util.Scanner;

public class Game {
	private String[] Player;
	private AIPlayer[] Computer;
	private Board boardState;
	
	/**
	 * 
	 */
	public Game() {
	}
	
	/**
	 * Get human player move
	 * @param moveGen
	 * 	Current board state move generator
	 * @return
	 * 	Move of current human player
	 */
	protected Tile playerInput ( Move moveGen ) {
		Scanner in = new Scanner(System.in);
		Tile move = null;
		
		System.out.print ( "Input Move: " );
		do {
			String moveString = in.nextLine();
			move = moveGen.moveParser( moveString );
			if ( move != null ) {				
				if ( !moveGen.validateMove(move) )
					move = null;
			}
			
			if ( move == null )
				System.out.println ( "Invalid Move!!! Type again..." );
		}while ( move == null );
		
		return move;
	}
	
	/**
	 * Main menu of game
	 * Choose either Human or Computer for Black/White Player
	 * If Computer is chosen, difficulty has to be chosen.
	 */
	protected void mainMenu () {
		String[] PlayerString = { "Black Player", "White Player" };
		Scanner in = new Scanner(System.in);
		for ( int turn = 0; turn < 2; ++turn ) {
			System.out.println ( "Is the " + PlayerString[turn] + " Human(H) or Computer(C)?");
			System.out.println ( "Please insert H/C accordingly." );
			String input = null;
			do {
				input = in.nextLine();
				if ( input.length() != 1 ) {
					continue; 
				}
				input = input.toUpperCase();
				
			}while ( !input.equals("H") && !input.equals("C") );
			Player[turn] = input;
			if ( Player[turn].equals("C") ) {
				input = chooseAI();
				if ( input.equals("D") )
					Computer[turn] = new AIPlayer(1);
				else if ( input.equals("S") )
					Computer[turn] = new AIPlayer(3);
				else
					Computer[turn] = new AIPlayer( Integer.parseInt (input) );
			}
		}
	}
	
	/**
	 * Choose difficulty of AI by depth level
	 * Dumb is 1, Smart is 3 or manual depth to be inserted
	 * @return
	 * 	Difficulty of AI
	 */
	protected String chooseAI() {
		Scanner in = new Scanner(System.in);
		System.out.println ( "Choose a difficulty for the Computer");
		System.out.println ( "Dumb(D) or Smart(S) or Manual Depth(1-5)?" );
		String input = null;
		Integer depth = 0;
		do {
			input = in.nextLine();
			if ( input.length() == 1 ) {
				try {
					depth = Integer.parseInt ( input );
					if ( depth < 1 || depth > 5 )
						depth = 0;
				}catch ( NumberFormatException e ) {
					input = input.toUpperCase();
				}
			}
		}while ( !input.equals("D") && !input.equals("S") && depth == 0 );
		
		return input;
	}
	
	/**
	 * Run Game
	 */
	protected void runGame (  ) {
		mainMenu();
		int round = 1;
		int[] Turn = new int[2];
		Turn[0] = Turn[1] = 1;
	
		
		while ( !boardState.gameOver() ) {
			Move moveGen = boardState.getMoveGen();
			PlayerTile currPlayer = boardState.getCurrentPlayer();
			
			System.out.println("########## Round " + round + " ##########");
			Display.DisplayBoard ( boardState );
			System.out.println ( currPlayer.toString() + " #" + Turn[currPlayer.getPlayerNum()-1] );
			System.out.println ( "Walls Left: " + currPlayer.getWallsLeft() );
			System.out.println ( "Thinking of move..." );
			Tile bestMove = null;
			if ( Player[currPlayer.getPlayerNum()-1].equals("C") ) {
				bestMove = Computer[currPlayer.getPlayerNum()-1].getBestMove(boardState);
				System.out.println( "Move: " + bestMove.toString() );
			}
			else
				bestMove = playerInput ( moveGen );
			moveGen.applyMove ( currPlayer, bestMove );
			
			++Turn[currPlayer.getPlayerNum()-1];
			boardState.switchCurrentPlayer();
			
			if ( currPlayer.getPlayerNum() == 2 ) {
				System.out.println ( "########## Round " + round + " End ##########" );
				++round;
			}
		}
		Display.DisplayBoard(boardState);
		System.out.println( boardState.getWinner() + " has won the game!!!");
		
		ResetQuitGame();
	}
	
	/**
	 * Reset or Quit Game prompt
	 */
	protected void ResetQuitGame () {
		Scanner in = new Scanner(System.in);
		String input = null;
		
		System.out.print ( "Reset Game(R)? or Quit Game(Q)?" );
		do {
			input = in.nextLine();
			input = input.toUpperCase();
			if ( input.equals("R") )
				play();
			else if ( input.equals("Q") )
				System.out.println ( "Thanks for playing");
				
		}while ( !input.equals("Q") && !input.equals("R") );
	}
	
	/**
	 * Test Computer vs Computer (NegaMax)
	 * for JUnit Tests (only display winner and final board state)
	 * @param blackDifficulty
	 * 	Black computer difficulty
	 * @param whiteDifficulty
	 * 	White computer difficulty
	 */
	public void testAI ( Integer blackDifficulty, Integer whiteDifficulty ) {
		Computer = new AIPlayer[2];
		Computer[0] = new AIPlayer (blackDifficulty); 
		Computer[1] = new AIPlayer (whiteDifficulty); 
		boardState = new Board();	
		
		while ( !boardState.gameOver() ) {
			Move moveGen = boardState.getMoveGen();
			PlayerTile currPlayer = boardState.getCurrentPlayer();
			
			Tile bestMove = null;
			bestMove = Computer[currPlayer.getPlayerNum()-1].getBestMove(boardState);
			moveGen.applyMove ( currPlayer, bestMove );
			boardState.switchCurrentPlayer();
		}
		
		Display.DisplayBoard(boardState);
		System.out.println( boardState.getWinner() + " has won the game!!!");
	}

	/**
	 * Test Computer vs Computer (AlphaBeta)
	 * for JUnit Tests (only display winner and final board state)
	 * @param blackDifficulty
	 * 	Black computer difficulty
	 * @param whiteDifficulty
	 * 	White computer difficulty
	 */
	public void testAI2 ( Integer blackDifficulty, Integer whiteDifficulty ) {
		Computer = new AIPlayer[2];
		Computer[0] = new AIPlayer (blackDifficulty); 
		Computer[1] = new AIPlayer (whiteDifficulty); 
		boardState = new Board();	
		
		while ( !boardState.gameOver() ) {
			Move moveGen = boardState.getMoveGen();
			PlayerTile currPlayer = boardState.getCurrentPlayer();
			
			Tile bestMove = null;
			bestMove = Computer[currPlayer.getPlayerNum()-1].getBestMove2(boardState);
			moveGen.applyMove ( currPlayer, bestMove );
			boardState.switchCurrentPlayer();
		}
		
		Display.DisplayBoard(boardState);
		System.out.println( boardState.getWinner() + " has won the game!!!");
	}
	
	/**
	 * Play Quoridor main function where Player, Computer and Board are initialised
	 */
	public void play () {
		Player = new String[2];
		Player[0] = Player[1] = "H";
		Computer = new AIPlayer[2];
		Computer[0] = Computer[1] = null;
		boardState = new Board();
		
		runGame();
	}
}
