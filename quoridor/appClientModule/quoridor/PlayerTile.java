package quoridor;

public class PlayerTile extends Tile {
	protected int playerNum;
	protected int wallCount;
	protected int goal;
	//ArrayList<Tile> prevPos;
	
	
	public PlayerTile ( int col, int row, int playerNum ) {
		super ( col, row );
		this.playerNum = playerNum;
		wallCount = 10;
		goal = (playerNum == 1) ? 0 : 8;
	}
	
	public PlayerTile ( Tile n ) {
		super ( n.col, n.row );
	}
	
	public Integer getPlayerNum () {
		return playerNum;
	}
	
	public boolean hasWallLeft () {
		return wallCount > 0;
	}
	
	public int getWallsLeft () {
		return wallCount;
	}
	
	public void putWall ( WallTile wall, WallTile[][] walls ) {
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
	
	public void setPos ( Tile newPos ) {
		this.row = newPos.getRow();
		this.col = newPos.getCol();
	}
	
	public boolean hasWon () {
		return this.row == goal;
	}
	
	@Override
	public void print () {
		System.out.print ( "\tColumn: " + col );
		System.out.print ( "\tRow: " + row );
		System.out.println ( "\tPlayer Number: " + playerNum );
	}
	
	public Integer getGoal () {
		return goal;
	}
	
	public Integer getDiff () {
		return Math.abs(goal-row);
	}
	
	@Override
	public String toString () {
		return ((playerNum == 1) ? "Black Player" : "White Player");
	}
}