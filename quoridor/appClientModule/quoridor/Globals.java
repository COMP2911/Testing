package quoridor;

import java.util.Map;
import java.util.Map.Entry;

/**
 * Has the max row and max column of the board
 *
 */
public class Globals {
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
}