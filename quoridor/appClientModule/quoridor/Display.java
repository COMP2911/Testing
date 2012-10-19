package quoridor;


public class Display {
	public Display(){}
	
	/**
	 * Displays board
	 * @param boardState
	 * 	Current board state
	 */
	public static void DisplayBoard ( Board boardState ) {
		final WallTile[][] walls = boardState.getWalls();
		final PlayerTile p1 = boardState.getBlackPlayer();
		final PlayerTile p2 = boardState.getWhitePlayer();
		
		final String evenEmpty = "   ";
		final String evenBlack = " B ";
		final String evenWhite = " W ";
		final String evenNoWall = "|";
		final String evenWall = "=";
		
		final String oddNoWall = "---";
		final String oddWall = "===";
		final String oddBetween = "+";
		
		final String border  = "    *************************************\n";
		
		String[][] boardArray = new String[17][9];
		for ( int a = 0; a < 17; ++a ) {
			for ( int b = 0; b < 9; ++b ) {
				if ( a%2 == 0 ) {
					boardArray[a][b] = evenEmpty;
					if ( b < 8 ) {
						boardArray[a][b] += evenNoWall;
					}
				}
				else {
					boardArray[a][b] = oddNoWall;
					if ( b < 8 ) {
						boardArray[a][b] += oddBetween;
					}
				}
			}
		}
		int rowPos;
		int colPos;
		for ( int a = 0; a < 9; ++a ) {
			for ( int b = 0; b < 9; ++b ) {
				if ( walls[a][b].hasVertical() ) {
					// +0(v)/1(h) - row
					// +4(v)/0(h)
					int row = Math.abs (a - 8);
					rowPos = row*2;
					boardArray[rowPos][b] = boardArray[rowPos][b].replace( evenNoWall, evenWall );
				}
				if ( walls[a][b].hasHorizontal() ) {
					int row = Math.abs (a - 7);
					rowPos = row*2+1;
					boardArray[rowPos][b] = boardArray[rowPos][b].replace( oddNoWall, oddWall );
				}
			}
		}
	// Display Player 1
		rowPos = Math.abs ( p1.row - 8 )*2;
		colPos = p1.col;
		boardArray[rowPos][colPos] = boardArray[rowPos][colPos].replace( evenEmpty, evenBlack );
	// Display Player 2
		rowPos = Math.abs ( p2.row - 8 )*2;
		colPos = p2.col;
		boardArray[rowPos][colPos] = boardArray[rowPos][colPos].replace( evenEmpty, evenWhite );
		final String colGrid = "      a   b   c   d   e   f   g   h   i\n";
	// Display Board with grid borders and help
		System.out.print (colGrid+border);
		int rowNum = 9;
		for ( int a = 0; a < 17; ++a ) {
			if ( a%2 == 0 ) { 
				System.out.print (rowNum + "   *");
			}
			else {
				System.out.print ("    *");
			}
			for ( int b = 0; b < 9; ++b ) {
				System.out.print( boardArray[a][b] );
			}
			if ( a%2 == 0 ) { 
				System.out.print ("*   " + rowNum + "\n");
				rowNum -= 1;
			}
			else {
				System.out.print("*\n");
			}
		}
		System.out.print (border+colGrid);
	}

}
