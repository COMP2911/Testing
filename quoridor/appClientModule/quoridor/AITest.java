package quoridor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AITest {
	Game game;
	@Before
	public void setUp() throws Exception {
		game = new Game();
	}

	@Test
	public void DumbVSDumbNM() {
		game.testAI(1, 1);
	}

	@Test
	public void DumbVSDumbAB() {
		game.testAI2(1, 1);
	}

	@Test
	public void DumbVSSmartNM() {
		game.testAI(1, 3);
	}

	@Test
	public void DumbVSSmartAB() {
		game.testAI2(1, 3);
	}

	@Test
	public void SmartVSSmartNM() {
		game.testAI(3, 3);
	}
	
	@Test
	public void SmartVSSmartAB() {
		game.testAI2(3, 3);
	}

}
