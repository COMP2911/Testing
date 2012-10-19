package quoridor;


import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import java.util.PriorityQueue;

public class AStarPathfinding {
	private ArrayList<AStarNode> closedNodes;
	private Queue<AStarNode> openNodes;
	
	public AStarPathfinding () {
	}
	
	public boolean pathFound ( Tile start, Integer goalRow, WallTile[][] wallNodes ) {
		closedNodes = new ArrayList<AStarNode>();
		openNodes = new PriorityQueue<AStarNode>();

		openNodes.add ( new AStarNode ( start, 0, 8, -1 ) );	
		while ( !( openNodes.isEmpty() ) ) {
			AStarNode current = openNodes.remove();
			
			for ( Tile neighbour : getNeighbourNodes ( current, goalRow ) ) {			
			// Check if neighbour is in between walls or in closed/open nodes
				if ( !neighbour.inBoard() || 
					 neighbour.inBetweenWall ( ((Tile)current), wallNodes ) ){
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
	
	public int movesLength ( Tile start, Integer goalRow, WallTile[][] wallNodes ) {
		closedNodes = new ArrayList<AStarNode>();
		openNodes = new PriorityQueue<AStarNode>();

		openNodes.add ( new AStarNode ( start, 0, 8, -1 ) );	
		int parentPos = 0;
		while ( !( openNodes.isEmpty() ) ) {
			AStarNode current = openNodes.remove();
			
			for ( Tile neighbour : getNeighbourNodes ( current, goalRow ) ) {			
			// Check if neighbour is in between walls or in closed/open nodes
				if ( !neighbour.inBoard() || 
					 neighbour.inBetweenWall ( ((Tile)current), wallNodes ) ){
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
	
	public LinkedList<Tile> getMoves ( Tile start, Integer goalRow, WallTile[][] wallNodes ) {
		closedNodes = new ArrayList<AStarNode>();
		openNodes = new PriorityQueue<AStarNode>();

		openNodes.add ( new AStarNode ( start, 0, 8, -1 ) );	
		int parentPos = 0;
		while ( !( openNodes.isEmpty() ) ) {
			AStarNode current = openNodes.remove();
			
			for ( Tile neighbour : getNeighbourNodes ( current, goalRow ) ) {			
			// Check if neighbour is in between walls or in closed/open nodes
				if ( !neighbour.inBoard() || 
					 neighbour.inBetweenWall ( ((Tile)current), wallNodes ) ){
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
	
// Get Neighbours List
	protected Tile[] getNeighbourNodes ( Tile current, int goalRow ) {
		int x = current.getCol();
		int y = current.getRow();
		
		if ( goalRow == 8 ) {
			Tile[] neighbourNodes = { new Tile (x,y+1), new Tile (x-1,y), new Tile (x+1,y), new Tile (x,y-1) };
			return neighbourNodes;
		}
		else {
			Tile[] neighbourNodes = { new Tile (x,y-1), new Tile (x-1,y), new Tile (x+1,y), new Tile (x,y+1) };
			return neighbourNodes;
		}
	}
}