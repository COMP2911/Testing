package quoridor;


public class WallTile extends Tile {
	protected boolean horizontal;
	protected boolean vertical;
	
	public WallTile () {
		super();
		//orientation = 'v';
	}
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
	public WallTile ( Tile i, Character orientation ) {
		this (i.getCol(), i.getRow(), orientation);
	}
	
	public void print () {
		System.out.print ("C1(X): "  + this.col);
		System.out.print ("\tR1(Y): "  + this.row);
		//System.out.print ("\tC2(X): "  + getOtherPart().col);
		//System.out.print ("\tR2(Y): "  + getOtherPart().row);
		System.out.print ("\tVertical: "  + this.vertical);
		System.out.println ("\tHorizontal: "  + this.horizontal);
	}
	
	public boolean hasVertical () {
		return vertical;
	}
	
	public boolean hasHorizontal () {
		return horizontal;
	}
	
	public void setVertical ( boolean value ) {
		vertical = value;
	}
	
	public void setHorizontal ( boolean value ) {
		horizontal = value;
	}

	@Override
	public boolean inBoard () {
		return (this.row < (Globals.MAX_ROW-1) && this.row >= 0 && this.col < (Globals.MAX_COL-1) && this.col >= 0);
	}
	
	@Override
	public boolean equals( Object obj ) {
		if ( obj instanceof WallTile ) {
			WallTile that = (WallTile)obj;
			return (this.col == that.col) && (this.row == that.row) && 
					(this.horizontal == that.horizontal) && (this.vertical == that.vertical);
		}
		return false;
	}
}