package quoridor;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit Test case for AI.
 * To test the different difficulties of the AI
 *
 */
public class AITest {
	Game game;
	/**
	 * Set up normal game for AI Testing
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		game = new Game();
	}

	/**
	 * Dumb AI vs Dumb AI both using MinMax algorithm with depth 1
	 */
	@Test
	public void DumbVSDumb() {
		game.testAI(1, 1);
	}


	/**
	 * Dumb AI vs Smart AI both using MinMax algorithm with depth 1 & 2 respectively
	 */
	@Test
	public void DumbVSSmart() {
		game.testAI(1, 2);
	}


	/**
	 * Smart AI vs Smart AI both using MinMax algorithm with depth 2
	 */
	@Test
	public void SmartVSSmart() {
		game.testAI(2, 2);
	}


	/**
	 * Testing MinMax algorithm with depth 3
	 */
	@Test
	public void TestDepth3() {
		game.testAI(3, 3);
	}
	
	/**
	 * Testing MinMax algorithm with depth 4
	 */
	@Test
	public void TestDepth4() {
		game.testAI(4, 4);
	}

}
