package quoridor;

public class Tile {
	protected int col;	// x
	protected int row;	// y
	
	public Tile () {
		col = row = 0;
	}
	public Tile ( int col, int row ) {
		this.col = col;
		this.row = row;
	}
	
	public void print () {
		System.out.print ("Column(X): "  + this.col);
		System.out.println ("\tRow(Y): "  + this.row);
	}
	
	public int getCol () {
		return this.col;
	}
	
	public int getRow () {
		return this.row;
	}
	
// 1 step no jumping
	public boolean inBetweenWall ( Tile previousNode, WallTile[][] walls ) {
		// Have moved up/down
		if ( previousNode.getRow() != this.row ) {
			Integer newRow = Math.min ( previousNode.getRow(), row );
			return walls[newRow][col].hasHorizontal();
		}
		// Have moved left/right
		else {
			Integer newCol = Math.min ( previousNode.getCol(), col );
			return walls[row][newCol].hasVertical();
		}
	}
	
	public boolean inBoard () {
		return (this.row < Globals.MAX_ROW && this.row >= 0 && this.col < Globals.MAX_COL && this.col >= 0);
	}
	
	@Override
	public boolean equals( Object obj ) {
		Tile that = (Tile)obj;
		return (this.col == that.col) && (this.row == that.row);
	}
	@Override
	public int hashCode() {
		return col*100+row*1000;
	}
	
	@Override
	public String toString () {
		char rowC = '1';
		rowC += row;
		char colC = 'a';
		colC += col;
		char wall = ' ';
		
		if ( this instanceof WallTile ) {
			wall = ((WallTile)this).hasVertical() ? 'v' : 'h';
		}

		return new String(""+colC+rowC+wall);
	}
}