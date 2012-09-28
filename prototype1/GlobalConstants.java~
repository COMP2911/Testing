import java.util.Map;
import java.util.Map.Entry;
public class GlobalConstants {
	public static final Integer MAX_ROW = 9;
	public static final Integer MAX_COL = 9;
	
	// Get Key by Value
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	// Convert to move String
	public static String getMoveInString( Node i ) {
		char row = 'a';
		row += i.row;
		char col = '0';
		col += i.col;
		char wall = ' ';
		
		if ( i instanceof WallNode ) {
			wall = ((WallNode)i).orientation;
		}

		return new String(""+row+col+wall);
	}
}