
public class WallNode extends Node {
	public Character orientation;
	
	public WallNode () {
		super();
		orientation = 'v';
	}
	public WallNode ( int col, int row, Character orientation ) {
		super(col, row);
		this.orientation = orientation;
	}
	public WallNode ( Node i, Character orientation ) {
		super(i.col, i.row);
		this.orientation = orientation;
	}
	public void print () {
		System.out.print ("C1(X): "  + this.col);
		System.out.print ("\tR1(Y): "  + this.row);
		System.out.print ("\tC2(X): "  + getOtherPart().col);
		System.out.print ("\tR2(Y): "  + getOtherPart().row);
		System.out.println ("\tOrientation: "  + this.orientation);
	}
	
	public WallNode getOtherPart () {
		switch (orientation) {
			case 'v':
				return new WallNode(this.col, this.row+1, 'v');
			case 'h':
				return new WallNode(this.col+1, this.row, 'h');
		}
		return null;
	}
	
	public Character getOrientation () {
		return orientation;
	}
	
	@Override
	public boolean equals( Object obj ) {
		if ( ! (obj instanceof WallNode) )
			return false;
		WallNode that = (WallNode)obj;
		return (this.col == that.col) && (this.row == that.row) && (this.orientation == that.orientation);
	}
	@Override
	public int hashCode() {
		return super.hashCode()+orientation*10000;
	}
}