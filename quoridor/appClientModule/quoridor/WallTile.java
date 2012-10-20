package quoridor;


/**
 * Wall tile consists of top and right wall flags
 *
 */
public class WallTile extends Tile {
	/**
	 * Top Wall of Tile
	 */
	private boolean horizontal;
	/**
	 * Right Wall of Tile
	 */
	private boolean vertical;
	
	/**
	 * Constructor to set position of tile and orientation of tile
	 * @param col
	 * 	Column of tile
	 * @param row
	 * 	Row of tile
	 * @param orientation
	 * 	Horizontal or Vertical
	 */
	public WallTile ( int col, int row, Character orientation ) {
		super(col, row);
		if (orientation == 'v' || orientation == 'V' )
			vertical = true;
		else if (orientation == 'h' || orientation == 'H' )
			horizontal = true;
		else {
			vertical = false;
			horizontal = false;
		}
	}
	/**
	 * Variant constructor to set position with Tile instead
	 * @param i
	 * 	Tile's position
	 * @param orientation
	 * 	Horizontal or Vertical
	 */
	public WallTile ( Tile i, Character orientation ) {
		this (i.getCol(), i.getRow(), orientation);
	}
	
	/**
	 * Variant constructor to duplicate an instance of Wall Tile
	 * @param i
	 * 	Wall Tile to be duplicated
	 */
	public WallTile ( Tile i ) {
		super(i.getCol(), i.getRow());
		if ( ((WallTile)i).hasHorizontal() )
			horizontal = true;
		else
			vertical = true;
	}
	
	/* (non-Javadoc)
	 * @see quoridor.Tile#print()
	 */
	public void print () {
		System.out.print ("C1(X): "  + this.col);
		System.out.print ("\tR1(Y): "  + this.row);
		//System.out.print ("\tC2(X): "  + getOtherPart().col);
		//System.out.print ("\tR2(Y): "  + getOtherPart().row);
		System.out.print ("\tVertical: "  + this.vertical);
		System.out.println ("\tHorizontal: "  + this.horizontal);
	}
	
	/**
	 * Check if tile has a vertical wall
	 * @return
	 * 	Whether there's a vertical wall
	 */
	public boolean hasVertical () {
		return vertical;
	}
	
	/**
	 * Check if tile has a horizontal wall
	 * @return
	 * 	Whether there's a horizontal wall
	 */
	public boolean hasHorizontal () {
		return horizontal;
	}
	
	/**
	 * Add/Remove vertical wall
	 * @param value
	 * 	Add is true while Remove is false
	 */
	public void setVertical ( boolean value ) {
		vertical = value;
	}
	
	/**
	 * Add/Remove horizontal wall
	 * @param value
	 * 	Add is true while Remove is false
	 */
	public void setHorizontal ( boolean value ) {
		horizontal = value;
	}

	/* (non-Javadoc)
	 * @see quoridor.Tile#inBoard()
	 */
	@Override
	public boolean inBoard () {
		return (this.row < (Globals.MAX_ROW-1) && this.row >= 0 && this.col < (Globals.MAX_COL-1) && this.col >= 0);
	}
	
	/* (non-Javadoc)
	 * @see quoridor.Tile#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ) {
		if ( obj instanceof WallTile ) {
			WallTile that = (WallTile)obj;
			return (this.col == that.col) && (this.row == that.row) && 
					(this.horizontal == that.horizontal) && (this.vertical == that.vertical);
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see quoridor.Tile#toString()
	 */
	@Override
	public String toString () {
		char rowC = '1';
		rowC += row;
		char colC = 'a';
		colC += col;
		char wall = ((WallTile)this).hasVertical() ? 'v' : 'h';
		
		return new String(""+colC+rowC+wall);
	}
}