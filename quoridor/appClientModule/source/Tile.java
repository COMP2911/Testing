package quoridor;

/**
 * Tile of board consists of column and row.
 * Checking methods of wall blocking and in board.
 *
 */
public class Tile {
	/**
	 * Column of tile
	 */
	protected int col;	// x
	/**
	 * Row of tile
	 */
	protected int row;	// y

	/**
	 * Constructor to initialise the column and row for tile
	 * @param col
	 * 	Column of tile
	 * @param row
	 * 	Row of tile
	 */
	public Tile ( int col, int row ) {
		this.col = col;
		this.row = row;
	}
	
	/**
	 * @param i
	 */
	public Tile ( Tile i ) {
		this.col = i.getCol();
		this.row = i.getRow();
	}
	
	/**
	 * Debug
	 */
	public void print () {
		System.out.print ("Column(X): "  + this.col);
		System.out.println ("\tRow(Y): "  + this.row);
	}
	
	/**
	 * Get column of tile
	 * @return
	 * 	Column of tile
	 */
	public int getCol () {
		return this.col;
	}
	
	/**
	 * Get row of tile
	 * @return
	 * 	Row of tile
	 */
	public int getRow () {
		return this.row;
	}
	
	/**
	 * Checks if current move was blocked by a wall
	 * @param previousTile
	 * 	Parent tile
	 * @param walls
	 * 	Walls of current board state
	 * @return
	 * 	Whether current tile is a valid move
	 */
	public boolean inBetweenWall ( Tile previousTile, WallTile[][] walls ) {
	// Have moved up/down
		if ( previousTile.getRow() != this.row ) {
			Integer newRow = Math.min ( previousTile.getRow(), row );
			return walls[newRow][col].hasHorizontal();
		}
	// Have moved left/right
		else {
			Integer newCol = Math.min ( previousTile.getCol(), col );
			return walls[row][newCol].hasVertical();
		}
	}
	
	/**
	 * Check if current tile is in board
	 * @return
	 * 	Validity of current tile
	 */
	public boolean inBoard () {
		return (this.row < Globals.MAX_ROW && this.row >= 0 && this.col < Globals.MAX_COL && this.col >= 0);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ) {
		Tile that = (Tile)obj;
		return (this.col == that.col) && (this.row == that.row);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return col*100+row*1000;
	}
	
	/**
	 * Converts tile to move string
	 * @return
	 * 	Move string
	 */
	@Override
	public String toString () {
		char rowC = '1';
		rowC += row;
		char colC = 'a';
		colC += col;
		
		return new String(""+colC+rowC);
	}
}