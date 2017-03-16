package tests;

import static org.junit.Assert.*;
import java.awt.Color;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;

public class gameSetupTests {
	private static Board board;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		board = Board.getInstance();
		// Sets the config files to our layout and legend files
		board.setConfigFiles("JOMW_ClueLayout.csv", "JOMW_ClueLegend.txt", "MKGCv2_CardFile.txt"); 
		board.initialize();
	}

	// Tests for loading the people
	// Checks for correct name, correct starting location, and correct color
	// Tests human player (Pikachu) and two computer players (Oddish and Eevee)
	@Test
	public void testPeople() {
		assertEquals(6, board.getPlayers().length);
		Player testPlayer = new Player("Oddish", 0, 6, Color.magenta);
		assertTrue(board.getPlayers()[5].equals(testPlayer));
		testPlayer.setPlayer("Pikachu", 21, 8, Color.yellow);
		assertTrue(board.getPlayers()[0].equals(testPlayer));
		testPlayer.setPlayer("Eevee", 10, 12, Color.orange);
		assertTrue(board.getPlayers()[1].equals(testPlayer));

}

	
	// Tests for loading/creating the deck of cards
	@Test
	public void testLoadingCards() {
		fail("Not yet implemented");
	}
	
	// Tests for dealing the cards
	@Test
	public void testDealingCards() {
		fail("Not yet implemented");
	}
}
