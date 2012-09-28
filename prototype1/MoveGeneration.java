import java.util.Map;
import java.util.LinkedList;

public interface MoveGeneration {
	public LinkedList<Node> GenerateMoves(Node player1, Node player2, Map<WallNode, WallNode> walls);
}