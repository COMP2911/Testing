package quoridor;

/**
 * Node with G & F costs and parentPosition for A* Pathfinding
 */
public class AStarNode extends Tile implements Comparable<AStarNode> {
	private int gCost;
	private int fCost;
	private int parentPosition;
	
	/**
	 * Constructor to initialise position of tile, g and f costs and parent position
	 * @param i
	 * 	Tile position
	 * @param gCost
	 * 	G Cost
	 * @param fCost
	 * 	F Cost
	 * @param parentPosition
	 * 	Parent Position
	 */
	public AStarNode ( Tile i, int gCost, int fCost, int parentPosition ) {
		super ( i.getCol(), i.getRow() );
		this.gCost = gCost;
		this.fCost = fCost;
		this.parentPosition = parentPosition;
	}

	/**
	 * Constructor to initialise position of tile, g and f costs and parent position
	 * @param col
	 * 	Column position of tile
	 * @param row
	 * 	Row position of tile
	 * @param gCost
	 * 	G Cost
	 * @param fCost
	 * 	F Cost
	 * @param parentPosition
	 * 	Parent Position
	 */
	public AStarNode ( int col, int row, int gCost, int fCost, int parentPosition ) {
		super ( col, row );
		this.gCost = gCost;
		this.fCost = fCost;
		this.parentPosition = parentPosition;
	}

	/**
	 * Get parent position of tile
	 * @return
	 * 	Parent position of tile
	 */
	public int getParentPosition () {
		return parentPosition;
	}

	/**
	 * Get G Cost of tile
	 * @return
	 * 	G Cost
	 */
	public int getGCost () {
		return gCost;
	}

	/**
	 * Get F Cost of tile
	 * @return
	 * 	F Cost
	 */
	public int getFCost () {
		return fCost;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AStarNode obj) {
		// TODO Auto-generated method stub
		return (this.fCost-obj.getFCost());
	}
}