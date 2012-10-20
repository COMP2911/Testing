package quoridor;


import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;

/**
 * A* Pathfinding gets shortest path in either number of moves or moves.
 * Includes finding a valid path.
 *
 */
public class AStarPathfinding {
	/**
	 * Closed nodes for A* pathfinding to prevent re-visiting of nodes
	 */
	private ArrayList<AStarNode> closedNodes;
	/**
	 * Open nodes for A* pathfinding to visit accordingly to the cost of nodes
	 */
	private Queue<AStarNode> openNodes;
	
	/**
	 * Checks if there is a path to goal
	 * @param start
	 * 	Starting position of current player
	 * @param goalRow
	 * 	Goal of current player
	 * @param walls
	 * 	Walls currently on the board
	 * @return
	 * 	Whether there's a path to goal
	 */
	public boolean pathFound ( Tile start, Integer goalRow, WallTile[][] walls ) {
		closedNodes = new ArrayList<AStarNode>();
		openNodes = new PriorityQueue<AStarNode>();

		openNodes.add ( new AStarNode ( start, 0, 8, -1 ) );	
		while ( !( openNodes.isEmpty() ) ) {
			AStarNode current = openNodes.remove();
			
			for ( Tile neighbour : getNeighbourTiles ( current, goalRow ) ) {			
			// Check if neighbour is in between walls or in closed/open nodes
				if ( !neighbour.inBoard() || 
					 neighbour.inBetweenWall ( ((Tile)current), walls ) ){
					continue;
				}
				int neighbourRow = neighbour.getRow();
				if ( neighbourRow == goalRow ) {
					return true;
				}
				if ( openNodes.contains ( neighbour ) || 
					 closedNodes.contains ( neighbour ) ) {
					 continue;
				}
				
				int tempGCost = current.getGCost()+1;
				int tempFCost = tempGCost + Math.abs ( goalRow - neighbourRow );

				openNodes.add ( new AStarNode ( neighbour, tempGCost, tempFCost, 0 ) );
			}
			closedNodes.add ( current );
		}
		return false;
	}

	/**
	 * Get the number of moves to reach the goal
	 * @param start
	 * 	Starting position of current player
	 * @param goalRow
	 * 	Goal of current player
	 * @param walls
	 * 	Walls currently on the board
	 * @return
	 * 	Number of moves to reach the goal
	 */
	public int movesLength ( Tile start, Integer goalRow, WallTile[][] walls ) {
		closedNodes = new ArrayList<AStarNode>();
		openNodes = new PriorityQueue<AStarNode>();

		openNodes.add ( new AStarNode ( start, 0, 8, -1 ) );	
		int parentPos = 0;
		while ( !( openNodes.isEmpty() ) ) {
			AStarNode current = openNodes.remove();
			
			for ( Tile neighbour : getNeighbourTiles ( current, goalRow ) ) {			
			// Check if neighbour is in between walls or in closed/open nodes
				if ( !neighbour.inBoard() || 
					 neighbour.inBetweenWall ( ((Tile)current), walls ) ){
					continue;
				}
				
				int neighbourRow = neighbour.getRow();
				if ( neighbourRow == goalRow ) {
					int length = 1;
					while ( current.getParentPosition() != -1 ) {
						++length;
						current = closedNodes.get ( current.getParentPosition() );
					}
					return length;
				}
				
				if ( openNodes.contains ( neighbour ) || 
					 closedNodes.contains ( neighbour ) ) {
					 continue;
				}
				
				int tempGCost = current.getGCost()+1;
				int tempFCost = tempGCost + Math.abs ( goalRow - neighbourRow );

				openNodes.add ( new AStarNode ( neighbour, tempGCost, tempFCost, parentPos ) );
			}
			closedNodes.add ( current );
			++parentPos;
		}
		return Integer.MAX_VALUE;
	}
	

	/**
	 * Get the moves to reach the goal
	 * @param start
	 * 	Starting position of current player
	 * @param goalRow
	 * 	Goal of current player
	 * @param walls
	 * 	Walls currently on the board
	 * @return
	 * 	Move list from start to goal
	 */
	public LinkedList<Tile> getMoves ( Tile start, Integer goalRow, WallTile[][] walls ) {
		closedNodes = new ArrayList<AStarNode>();
		openNodes = new PriorityQueue<AStarNode>();

		openNodes.add ( new AStarNode ( start, 0, 8, -1 ) );	
		int parentPos = 0;
		while ( !( openNodes.isEmpty() ) ) {
			AStarNode current = openNodes.remove();
			
			for ( Tile neighbour : getNeighbourTiles ( current, goalRow ) ) {			
			// Check if neighbour is in between walls or in closed/open nodes
				if ( !neighbour.inBoard() || 
					 neighbour.inBetweenWall ( ((Tile)current), walls ) ){
					continue;
				}
				
				int neighbourRow = neighbour.getRow();
				if ( neighbourRow == goalRow ) {
					LinkedList<Tile> Moves = new LinkedList<Tile>();
					Moves.push ( neighbour );
					while ( current.getParentPosition() != -1 ) {
						Moves.push ( current );
						current = closedNodes.get ( current.getParentPosition() );
					}
					return Moves;
				}
				
				if ( openNodes.contains ( neighbour ) || 
					 closedNodes.contains ( neighbour ) ) {
					 continue;
				}
				
				int tempGCost = current.getGCost()+1;
				int tempFCost = tempGCost + Math.abs ( goalRow - neighbourRow );

				openNodes.add ( new AStarNode ( neighbour, tempGCost, tempFCost, parentPos ) );
			}
			closedNodes.add ( current );
			++parentPos;
		}
		return null;
	}
	
	/**
	 * Get the neighbours of the current tile, priority given to goal direction
	 * @param current
	 * 	Current tile
	 * @param goalRow
	 * 	Goal
	 * @return
	 * 	Neighbour tiles
	 */
	protected Tile[] getNeighbourTiles ( Tile current, int goalRow ) {
		int x = current.getCol();
		int y = current.getRow();
		
		if ( goalRow == 8 ) {
			Tile[] neighbourTiles = { new Tile (x,y+1), new Tile (x-1,y), new Tile (x+1,y), new Tile (x,y-1) };
			return neighbourTiles;
		}
		else {
			Tile[] neighbourTiles = { new Tile (x,y-1), new Tile (x-1,y), new Tile (x+1,y), new Tile (x,y+1) };
			return neighbourTiles;
		}
	}
}