package quoridor;

/**
 * Player tile consists of player number, wall count of player and goal row of player
 *
 */
public class PlayerTile extends Tile {
	private int playerNum;
	private int wallCount;
	private int goal;	
	
	/**
	 * Constructor to initialise column and row of tile, player number, wall count and goal row of player
	 * @param col
	 * 	Column of tile
	 * @param row
	 * 	Row of tile
	 * @param playerNum
	 * 	Player number
	 */
	public PlayerTile ( int col, int row, int playerNum ) {
		super ( col, row );
		this.playerNum = playerNum;
		wallCount = 10;
		goal = (playerNum == 1) ? 0 : 8;
	}
	
	/**
	 * Get the player number
	 * @return
	 * 	Player number
	 */
	public Integer getPlayerNum () {
		return playerNum;
	}
	
	/**
	 * Checks if player has walls left
	 * @return
	 * 	Whether player has walls left
	 */
	public boolean hasWallLeft () {
		return wallCount > 0;
	}
	
	/**
	 * Gets the number of walls that the player has
	 * @return
	 * 	Walls that player has left
	 */
	public int getWallsLeft () {
		return wallCount;
	}
	
	/**
	 * Add wall to board and reduce wall count
	 * @param wall
	 * 	Wall to be added
	 * @param walls
	 * 	Walls on the board
	 */
	public void addWall ( WallTile wall, WallTile[][] walls ) {
		if ( wall.hasVertical() ) {
			walls[wall.getRow()][wall.getCol()].setVertical(true);
			walls[wall.getRow()+1][wall.getCol()].setVertical(true);
		}
		else {
			walls[wall.getRow()][wall.getCol()].setHorizontal(true);
			walls[wall.getRow()][wall.getCol()+1].setHorizontal(true);
		}
		--wallCount;
	}
	
	/**
	 * Remove wall from board and increase back wall count
	 * @param wall
	 * 	Wall to be removed
	 * @param walls
	 * 	Walls on the board
	 */
	public void removeWall ( WallTile wall, WallTile[][] walls ) {
		if ( wall.hasVertical() ) {
			walls[wall.getRow()][wall.getCol()].setVertical(false);
			walls[wall.getRow()+1][wall.getCol()].setVertical(false);
		}
		else {
			walls[wall.getRow()][wall.getCol()].setHorizontal(false);
			walls[wall.getRow()][wall.getCol()+1].setHorizontal(false);
		}
		++wallCount;
	}
	
	/**
	 * Sets new position for player
	 * @param newPos
	 * 	New position
	 */
	public void setPos ( Tile newPos ) {
		this.row = newPos.getRow();
		this.col = newPos.getCol();
	}
	
	/**
	 * Check if player has won the game
	 * @return
	 * 	Has player won the game
	 */
	public boolean hasWon () {
		return this.row == goal;
	}
	
	/* (non-Javadoc)
	 * @see quoridor.Tile#print()
	 */
	@Override
	public void print () {
		System.out.print ( "\tColumn: " + col );
		System.out.print ( "\tRow: " + row );
		System.out.println ( "\tPlayer Number: " + playerNum );
	}
	
	/**
	 * Get the goal row of player
	 * @return
	 * 	Goal row of player
	 */
	public Integer getGoal () {
		return goal;
	}
	
	/**
	 * Get estimated distance to goal
	 * @return
	 * 	Estimated distance to goal
	 */
	public Integer getDiff () {
		return Math.abs(goal-row);
	}
	
	/* (non-Javadoc)
	 * @see quoridor.Tile#toString()
	 */
	@Override
	public String toString () {
		return ((playerNum == 1) ? "Black Player" : "White Player");
	}
}