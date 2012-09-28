import java.util.Map;

public class Node {
	public int col;	// x
	public int row;	// y
	public Node () {
		col = row = 0;
	}
	public Node ( int col, int row ) {
		this.col = col;
		this.row = row;
	}
	public void print () {
		System.out.print ("Column(X): "  + this.col);
		System.out.println ("\tRow(Y): "  + this.row);
	}
	
	public boolean inBoard () {
		return (this.row < GlobalConstants.MAX_ROW && this.row > 0 && this.col < GlobalConstants.MAX_COL && this.col > 0);
	}
	
// 1 step no jumping
	public boolean inBetweenWall ( Node previousNode, Map<WallNode, WallNode> walls ) {
		WallNode temp = new WallNode();
		// Have moved up/down
		if ( previousNode.row != this.row ) {
			temp.orientation = 'h';
			temp.row = Math.min ( previousNode.row, this.row );
			temp.col = this.col;
		}
		// Have moved left/right
		else if ( previousNode.col != this.col ) {
			temp.orientation = 'v';
			temp.col = Math.min ( previousNode.col, this.col );
			temp.row = this.row;
		}
		return walls.containsKey ( temp );
	}
	public Node jump (Node oldPosition) {
		int oldRow = oldPosition.row;
		int oldCol = oldPosition.col;
		Node result = new Node ( this.col, this.row );
		if ( oldRow < this.row ) result.row += 1;
		else if ( oldRow > this.row ) result.row -= 1;
		else if ( oldCol < this.col ) result.col += 1;
		else if ( oldCol > this.col ) result.col -= 1;
		return result;
	}
	
	public Node jumpLeft (Node oldPosition) {
		int oldRow = oldPosition.row;
		int oldCol = oldPosition.col;
		Node result = new Node ( this.col, this.row );
		if ( oldRow != this.row ) result.col -= 1;
		else if ( oldCol != this.col ) result.row -= 1;
		return result;
	}
	
	public Node jumpRight (Node oldPosition) {
		int oldRow = oldPosition.row;
		int oldCol = oldPosition.col;
		Node result = new Node ( this.col, this.row );
		if ( oldRow != this.row ) result.col += 1;
		else if ( oldCol != this.col ) result.row += 1;
		return result;
	}
	@Override
	public boolean equals( Object obj ) {
		Node that = (Node)obj;
		return (this.col == that.col) && (this.row == that.row);
	}
	@Override
	public int hashCode() {
		return col*100+row*1000;
	}
}