package quoridor;


/**
 * Board class where both players and walls are stored.
 * Includes tracking of current player, game over and winner of game.
 *
 */
public class Board {
	/**
	 * Black player starting column
	 */
	private final int bpX = 4;
	/**
	 * Black player starting row
	 */
	private final int bpY = 8;
	/**
	 * White player starting column
	 */
	private final int wpX = 4;
	/**
	 * White player starting row
	 */
	private final int wpY = 0;
	/**
	 * Black Player
	 */
	private PlayerTile blackPlayer;
	/**
	 * White Player
	 */
	private PlayerTile whitePlayer;
	/**
	 * Walls
	 */
	private WallTile[][] walls;
	/**
	 * Current player number
	 */
	private Integer currentPlayer;
	
	/**
	 * Constructor to initialise the starting positions of black and white players, walls and current player
	 */
	public Board () {
		blackPlayer = new PlayerTile ( bpX, bpY, 1 );
		whitePlayer = new PlayerTile ( wpX, wpY, 2 );
		walls = new WallTile[Globals.MAX_ROW][Globals.MAX_COL];
		initWalls ();
		currentPlayer = 1;
	}
	
	/**
	 * Check if the game is over
	 * @return
	 * 	Whether game is over
	 */
	public boolean gameOver () {
		return (blackPlayer.hasWon() || whitePlayer.hasWon());
	}
	
	/**
	 * Winner of game
	 * @return
	 * 	Game winner in string
	 */
	public String getWinner () {
		if ( blackPlayer.hasWon() )
			return blackPlayer.toString();
		else if ( whitePlayer.hasWon() )
			return whitePlayer.toString();
		return null;
	}
	
	/**
	 * Initialise the walls
	 */
	protected void initWalls () {
		for ( int a = 0; a < Globals.MAX_ROW; ++a ) {
			for ( int b = 0; b < Globals.MAX_COL; ++b ) {
				walls[a][b] = new WallTile ( b, a, ' ' );
			}
		}
	}
	
	/**
	 * Clear all walls
	 */
	protected void clearWalls () {
		for ( int a = 0; a < Globals.MAX_ROW; ++a ) {
			for ( int b = 0; b < Globals.MAX_COL; ++b ) {
				walls[a][b].setVertical ( false );
				walls[a][b].setHorizontal ( false );
			}
		}
	}
	
	/**
	 * Get walls
	 * @return
	 * 	Walls
	 */
	public WallTile[][] getWalls() {
		return walls;
	}
	
	/**
	 * Get black player
	 * @return
	 * 	Black player
	 */
	public PlayerTile getBlackPlayer() {
		return blackPlayer;
	}
	
	/**
	 * Get white player
	 * @return
	 * 	White player
	 */
	public PlayerTile getWhitePlayer() {
		return whitePlayer;
	}
	
	/**
	 * Get current player
	 * @return
	 * 	Current player
	 */
	public PlayerTile getCurrentPlayer() {
		return (blackPlayer.getPlayerNum() == currentPlayer) ? blackPlayer : whitePlayer;
	}
	
	/**
	 * Get other player
	 * @return
	 * 	Other player
	 */
	public PlayerTile getOtherPlayer() {
		return (blackPlayer.getPlayerNum() == currentPlayer) ? whitePlayer : blackPlayer;
	}
	
	/**
	 * Switch current player 
	 */
	public void switchCurrentPlayer () {
		currentPlayer = ((currentPlayer == 1) ? 2 : 1);
	}
	
	/**
	 * Get move generation
	 * @return
	 * 	Move generation
	 */
	public Move getMoveGen () {
		return new AllMoves(this);
	}
}
