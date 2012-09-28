import java.util.LinkedList;
import java.util.Map;

public class PlayerNode extends Node {
	int playerNum;
	int wallCount;
	LinkedList<Node> prevPos;
	
	
	public PlayerNode ( int col, int row, int playerNum ) {
		super ( col, row );
		this.playerNum = playerNum;
		prevPos = new LinkedList<Node>();
		wallCount = 10;
	}
	
	public PlayerNode ( Node n ) {
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
	
	public void putWall ( WallNode wall, Map<WallNode, WallNode> walls ) {
		walls.put ( wall, wall );
		walls.put ( wall.getOtherPart(), wall.getOtherPart() );
		--wallCount;
	}
	
	public void putTempWall ( WallNode wall, Map<WallNode, WallNode> walls ) {
		walls.put ( wall, wall );
		walls.put ( wall.getOtherPart(), wall.getOtherPart() );
	}
	
	public void removeTempWall ( WallNode wall, Map<WallNode, WallNode> walls ) {
		walls.remove ( wall );
		walls.remove ( wall.getOtherPart() );
	}
	
	public void setNewPos2 ( Node newPos ) {
		this.row = newPos.row;
		this.col = newPos.col;
	}
	
	public boolean setNewPos ( Node newPos ) {
	// Checks if newPos exist do not retravel
		prevPos.push ( new Node (this.col, this.row) );
		this.row = newPos.row;
		this.col = newPos.col;
		return true;
	}
	
	public void setOldPos ( ) {
		Node previousPos = prevPos.pop ();
		this.row = previousPos.row;
		this.col = previousPos.col;
	}
	
	public boolean hasWon () {
		return this.row == getGoal ().row;
	}
	
	@Override
	public void print () {
		System.out.print ( "\tColumn: " + col );
		System.out.print ( "\tRow: " + row );
		System.out.println ( "\tPlayer Number: " + playerNum );
	}
	
	public Node getGoal () {
		return (playerNum == 1) ? new Node ( 0, 8) : new Node ( 0, 0);
	}
	/*
	public boolean inBetweenWall_Jump ( Node previousNode, Map<WallNode, WallNode> walls ) {
		WallNode temp = new WallNode();
		// Have moved up/down
		if ( previousNode.row != this.row ) {
			temp.orientation = 'h';
			temp.row = Math.max ( previousNode.row, this.row ) - 1;
			temp.col = this.col;
		}
		// Have moved left/right
		else if ( previousNode.col != this.col ) {
			temp.orientation = 'v';
			temp.col = Math.max ( previousNode.col, this.col ) - 1;
			temp.row = this.row;
		}
		return walls.containsKey ( temp );
	}
	
	@Override
	public boolean equals( Object obj ) {
		if ( ! (obj instanceof PlayerNode) )
			return super.equals(obj);
		PlayerNode that = (PlayerNode)obj;
		return (this.col == that.col) && (this.row == that.row) && (this.playerNum == that.playerNum);
	}
	@Override
	public int hashCode() {
		return super.hashCode()*playerNum;
	}
	*/
}