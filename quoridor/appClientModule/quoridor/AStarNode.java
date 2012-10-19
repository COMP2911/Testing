package quoridor;

public class AStarNode extends Tile implements Comparable<AStarNode> {
	private int gCost;
	private int fCost;
	private int parentPosition;
	
	public AStarNode ( Tile i, int gCost, int fCost, int parentPosition ) {
		super ( i.getCol(), i.getRow() );
		this.gCost = gCost;
		this.fCost = fCost;
		this.parentPosition = parentPosition;
	}
	
	public AStarNode ( int col, int row, int gCost, int fCost, int parentPosition ) {
		super ( col, row );
		this.gCost = gCost;
		this.fCost = fCost;
		this.parentPosition = parentPosition;
	}

	public int getParentPosition () {
		return parentPosition;
	}

	public int getGCost () {
		return gCost;
	}

	public int getFCost () {
		return fCost;
	}

	@Override
	public int compareTo(AStarNode obj) {
		// TODO Auto-generated method stub
		return (this.fCost-obj.getFCost());
	}
}