package quoridor;


public class Board {
	private final int bpX = 4;
	private final int bpY = 8;
	private final int wpX = 4;
	private final int wpY = 0;
	private PlayerTile blackPlayer;
	private PlayerTile whitePlayer;
	private WallTile[][] walls;
	private Integer currentPlayer;
	
	public Board () {
		blackPlayer = new PlayerTile ( bpX, bpY, 1 );
		whitePlayer = new PlayerTile ( wpX, wpY, 2 );
		walls = new WallTile[Globals.MAX_ROW][Globals.MAX_COL];
		initWalls ();
		currentPlayer = 1;
	}
	
	public boolean gameOver () {
		return (blackPlayer.hasWon() || whitePlayer.hasWon());
	}
	
	public String getWinner () {
		if ( blackPlayer.hasWon() )
			return blackPlayer.toString();
		else if ( whitePlayer.hasWon() )
			return whitePlayer.toString();
		return null;
	}
	
	protected void initWalls () {
		for ( int a = 0; a < Globals.MAX_ROW; ++a ) {
			for ( int b = 0; b < Globals.MAX_COL; ++b ) {
				walls[a][b] = new WallTile ( b, a, ' ' );
			}
		}
	}
	
	protected void clearWalls () {
		for ( int a = 0; a < Globals.MAX_ROW; ++a ) {
			for ( int b = 0; b < Globals.MAX_COL; ++b ) {
				walls[a][b].setVertical ( false );
				walls[a][b].setHorizontal ( false );
			}
		}
	}
	
	public WallTile[][] getWalls() {
		return walls;
	}
	
	public PlayerTile getBlackPlayer() {
		return blackPlayer;
	}
	
	public PlayerTile getWhitePlayer() {
		return whitePlayer;
	}
	
	public PlayerTile getCurrentPlayer() {
		return (blackPlayer.getPlayerNum() == currentPlayer) ? blackPlayer : whitePlayer;
	}
	
	public PlayerTile getOtherPlayer() {
		return (blackPlayer.getPlayerNum() == currentPlayer) ? whitePlayer : blackPlayer;
	}
	
	public void switchCurrentPlayer () {
		currentPlayer = ((currentPlayer == 1) ? 2 : 1);
	}
	
	public Move getMoveGen () {
		return new AllMoves(this);
	}
}
