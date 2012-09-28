import java.util.Map;

public class MTDf {
	public Double runMTDF ( Map<Node, Node> root, Double f, Double d ) {
		double g = f;
		double upperBound = Double.MAX_VALUE;
		double lowerBound = Double.MIN_VALUE;
		
		double beta = 0;
		while ( lowerBound < upperBound ) {
			if ( g == lowerBound ) {
				beta = g+1;
			}
			else {
				beta = g;
			}
		//	g = AlphaBetaWithMemory (root, beta-1, beta, d);
			if ( g < beta ) {
				upperBound = g;
			}
			else {
				lowerBound = g;
			}
		}
		return g;
	}
}