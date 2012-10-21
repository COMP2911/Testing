package quoridor;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Create different type of games such as Human-Human, Computer-Computer, Human-Computer and no display game for testing 
 *
 */
public class Game {
	/**
	 * To state whether player is human/computer
	 */
	private String[] Player;
	/**
	 * To initialise the AI depending on depth given
	 */
	private AIPlayer[] Computer;
	/**
	 * Current board state
	 */
	private Board boardState;
	/**
	 * Undo move list
	 */
	private LinkedList<Tile> undoMoveList;
	/**
	 * Redo move list
	 */
	private LinkedList<Tile> redoMoveList;
	/**
	 * Round of game
	 */
	private int round;
	/**
	 * Turn of each player
	 */
	private int[] Turn;
	
	/**
	 * Constructor to initialise the arrays and linkedlists
	 */
	public Game () {
		Player = new String[2];
		Computer = new AIPlayer[2];
		Turn = new int[2];
		undoMoveList = new LinkedList<Tile>();
		redoMoveList = new LinkedList<Tile>();
	}
	/**
	 * Get string input from bash
	 * @return
	 * 	String input
	 */
	protected String getStringInput () {
		Scanner scan = new Scanner ( System.in );
		return scan.nextLine().toLowerCase();
	}
	
	/**
	 * Get integer input from bash
	 * @return
	 * 	Integer input
	 */
	protected Integer getIntegerInput () {
		Scanner scan = new Scanner ( System.in );
		return scan.nextInt();
	}
	
	/**
	 * Main menu of game
	 */
	protected void mainMenu () {
		System.out.println ( "Welcome to Quoridor 1.0" );
		System.out.println ( "Choose the following game modes:" );
		System.out.println ( "(1) New Game" );
		System.out.println ( "(2) Load Game" );
		//System.out.println ( "(3) Network Game" );
		System.out.println ( "(3) Quit Game" );
		boolean mode = false;
		while (!mode) {
			switch ( getIntegerInput() ) {
			case 1:
				newGame();
				mode = true;
				break;
			case 2:
				if ( !loadGame () )
					continue;
				else
					mode = true;
				break;
			case 3:
				System.out.println ( "Thanks for playing");
				mode = true;
				break;
			/*case 4:
				mode = true;
				break;*/
			default:
				System.out.println ( "Invalid Input!!!" ); 
				break;
			}
		}
	}
	

	/**
	 * Loads game;
	 */
	protected boolean loadGame() {
		undoMoveList.clear();
		redoMoveList.clear();
		boardState = new Board();
		
		try{
		// Create file 
			FileReader fstream = new FileReader("game.txt");
			BufferedReader in = new BufferedReader(fstream);
			String[] data;
		// Human/Computer/CurrentPlayer
			data  = in.readLine().split(" ");
			Player[0] = data[0];			Player[1] = data[1];
			if ( Integer.parseInt(data[2]) == 2 )
				boardState.switchCurrentPlayer();
		// Turn[2]/Round
			data  = in.readLine().split(" ");
			Turn[0] = Integer.parseInt(data[0]);
			Turn[1] = Integer.parseInt(data[1]);
			round = Integer.parseInt(data[2]);
		// Undo Move List
			Move moveGen = boardState.getMoveGen();
			for ( String move : in.readLine().split(" ") ) {
				Tile moveTile = moveGen.moveParser(move);
				undoMoveList.add(moveTile);
				moveGen.applyMove(boardState.getCurrentPlayer(), moveTile);
			}
		// Redo Move List
			for ( String move : in.readLine().split(" ") ) {
				Tile moveTile = moveGen.moveParser(move);
				redoMoveList.add(moveTile);
			}
		// Player 1 & 2 position/Player 1 & 2 wall count
			data  = in.readLine().split(" ");
			moveGen.applyMove( boardState.getBlackPlayer(), moveGen.moveParser(data[0]) );
			moveGen.applyMove( boardState.getWhitePlayer(), moveGen.moveParser(data[1]) );
			boardState.getBlackPlayer().setWallsLeft( Integer.parseInt(data[2]) );
			boardState.getWhitePlayer().setWallsLeft( Integer.parseInt(data[3]) );
		//Close the input stream
			in.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("No file found: " + e.getMessage());
			return false;
		}
		
		runGame();
		return true;
	}
	
	/**
	 * Saves current game state
	 */
	protected void saveGame() {
		try{
		// Create file 
			FileWriter fstream = new FileWriter("game.txt");
			BufferedWriter out = new BufferedWriter(fstream);
		// Human/Computer/CurrentPlayer
			out.write( Player[0] + " " + Player[1] + " " + boardState.getCurrentPlayer().getPlayerNum() );
			out.newLine();
		// Turn[2]/Round
			out.write( Turn[0] + " " + Turn[1] + " " + round );
			out.newLine();
		// Undo Move List
			StringBuilder sb = new StringBuilder();
			for ( Tile move : undoMoveList ) {
				sb.append(move.toString() + " ");
			}
			if (sb.length() > 1)
				sb.deleteCharAt(sb.length()-1);
			out.write(sb.toString());
			out.newLine();
		// Redo Move List
			sb = new StringBuilder();
			for ( Tile move : redoMoveList ) {
				sb.append(move.toString() + " ");
			}
			if (sb.length() > 1)
				sb.deleteCharAt(sb.length()-1);
			out.write(sb.toString());
			out.newLine();
		// Player 1 & 2 position/Player 1 & 2 wall count
			out.write( (new Tile(boardState.getBlackPlayer())).toString() + " " + (new Tile(boardState.getWhitePlayer())).toString() + " " + 
						boardState.getBlackPlayer().getWallsLeft() + " " + boardState.getWhitePlayer().getWallsLeft() );
			out.newLine();
		//Close the output stream
			out.close();
			System.out.println ( "Game saved" );
		}catch (IOException e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Initialise new game
	 */
	protected void newGame () {
		choosePlayers();
		resetGame();
	}
	
	/**
	 * Reset game
	 */
	protected void resetGame () {
		undoMoveList.clear();
		redoMoveList.clear();
		boardState = new Board();
		//Computer[0] = Computer[1] = null;
		round = 1;
		Turn[0] = Turn[1] = 1;
		runGame();
	}
	
	/**
	 * Undo move
	 * @param moveGen
	 * 	Move generator
	 */
	protected void undoMove ( Move moveGen ) {
		PlayerTile currPlayer = boardState.getCurrentPlayer();
		PlayerTile otherPlayer = boardState.getOtherPlayer();
		
	// Undo other player's move
		Tile undoMove = undoMoveList.pop();
		if ( undoMove instanceof WallTile )
			redoMoveList.push ( undoMove );
		else
			redoMoveList.push ( new Tile ( otherPlayer ) );
		moveGen.undoMove ( otherPlayer, undoMove );
		
	// Undo current player's move
		undoMove = undoMoveList.pop();
		if ( undoMove instanceof WallTile )
			redoMoveList.push( undoMove );
		else
			redoMoveList.push( new Tile ( currPlayer ) );
		moveGen.undoMove ( currPlayer, undoMove );	
		
		--Turn[0];	
		--Turn[1];
		--round;
	}
	
	/**
	 * Redo move
	 * @param moveGen
	 * 	Move generator
	 */
	protected void redoMove ( Move moveGen ) {
		PlayerTile currPlayer = boardState.getCurrentPlayer();
		PlayerTile otherPlayer = boardState.getOtherPlayer();
		
	// Redo current player's move
		Tile redoMove = redoMoveList.pop();
		if ( redoMove instanceof WallTile )
			undoMoveList.push( redoMove );
		else
			undoMoveList.push( new Tile ( currPlayer ) );
		moveGen.applyMove ( currPlayer, redoMove );
		
	// Redo other player's move
		redoMove = redoMoveList.pop();
		if ( redoMove instanceof WallTile )
			undoMoveList.push( redoMove );
		else
			undoMoveList.push( new Tile ( otherPlayer ) );
		moveGen.applyMove ( otherPlayer, redoMove );	
		
		++Turn[0];	
		++Turn[1];
		++round;
	}
	
	
	/**
	 * Get Player Input
	 * @param moveGen
	 * 	Move generator
	 * @return
	 * 	Player input
	 */
	protected String playerInput ( Move moveGen ) {
		System.out.println ( "Commands: <col><row>, <col><row><h/v>, (Q)uit, (U)ndo, (R)edo, (E)nd, (S)ave" );
		System.out.print ( "Input Move: " );
		while (true) {
			String input = getStringInput();
			if ( input.equals("quit") || input.equals("q") )
				return "quit";
			else if ( input.equals("end") || input.equals("e") )
				return "end";
			else if ( input.equals("save") || input.equals("s") )
				saveGame();
			else if ( (input.equals("undo") || input.equals("u")) && undoMoveList.size() >= 2 )
				return "undo";
			else if ( (input.equals("redo") || input.equals("r")) && redoMoveList.size() >= 2 )
				return "redo";
			else {
				Tile bestMove = moveGen.moveParser( input );
				if ( bestMove != null ) {				
					if ( !moveGen.validateMove(bestMove) )
						bestMove = null;
				}

				if ( bestMove == null )
					System.out.println ( "Invalid Move!!!" );
				else
					return input;
			}
		}
	}
	
	/**
	 * Choosing of players
	 * Choose either Human or Computer for Black/White Player
	 * If Computer is chosen, difficulty has to be chosen.
	 */
	protected void choosePlayers () {
		String[] PlayerString = { "Black Player", "White Player" };
		int playerNumber = 0;
		while (playerNumber < 2) {
			System.out.println ( "Is the " + PlayerString[playerNumber] + " Human(H) or Computer(C)?");
			String input = getStringInput();
			if ( input.equals("h") || input.equals("human") ) {
				Player[playerNumber] = "H";
				++playerNumber;
			}
			else if ( input.equals("c") || input.equals("computer") ) {
				Player[playerNumber] = "C";
				chooseAI(playerNumber);
				++playerNumber;
			}	
			else
				System.out.println ( "Invalid Input!!!" ); 
		}
	}
	
	/**
	 * Choose difficulty of AI by depth level
	 * Dumb is 1 or Smart is 2
	 * @param playerNumber
	 * 	Player number of AI
	 */
	protected void chooseAI( int playerNumber ) {
		System.out.println ( "Choose a difficulty for the Computer");
		System.out.println ( "Dumb(D) or Smart(S)?" );
		while (true) {
			String input = getStringInput();
			if ( input.equals("d") || input.equals("dumb") ) {
				Computer[playerNumber] = new AIPlayer(1);
				break;
			}
			else if ( input.equals("s") || input.equals("smart") ) {
				Computer[playerNumber] = new AIPlayer(2);
				break;
			}					
			else
				System.out.println ( "Invalid Input!!!" ); 
		}
	}
	
	/**
	 * Run Game
	 */
	protected void runGame (  ) {	
		while ( !boardState.gameOver() ) {
			Move moveGen = boardState.getMoveGen();
			PlayerTile currPlayer = boardState.getCurrentPlayer();

			if ( currPlayer.getPlayerNum() == 1 )
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
			else {	
				String input = playerInput ( moveGen );
				if ( input.equals("quit") ) {
					System.out.println ( "Thanks for playing");
					return;
				}
				else if ( input.equals("end") )
					break;
				else if ( input.equals("undo") ) {
					undoMove(moveGen);
					continue;	
				}
				else if ( input.equals("redo") ) {
					redoMove(moveGen);
					continue;	
				}
				else {
					bestMove = moveGen.moveParser(input);
					redoMoveList.clear();
				}
			}
			if ( bestMove instanceof WallTile )
				undoMoveList.push( new WallTile( bestMove ) );
			else
				undoMoveList.push( new Tile ( currPlayer ) );
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
		System.out.print ( "Return to (M)enu, (R)eset Game or (Q)uit Game?" );
		while (true) {
			String input = getStringInput();
			if ( input.equals("m") || input.equals("menu") ) {
				mainMenu();
				break;
			}
			else if ( input.equals("r") || input.equals("reset") ) {
				resetGame();
				break;
			}
			else if ( input.equals("q") || input.equals("quit") ) {
				System.out.println ( "Thanks for playing");
				break;
			}					
			else
				System.out.println ( "Invalid Input!!!" ); 
		}
	}
	
	/**
	 * Test Computer vs Computer
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
			//Display.DisplayBoard(boardState);
		}
		
		Display.DisplayBoard(boardState);
		System.out.println( boardState.getWinner() + " has won the game!!!");
	}
	
	/**
	 * Play Quoridor main function where Player, Computer and Board are initialised
	 */
	public void play () {
		mainMenu();
	}
}
