package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

public class gameSetupTests {
	private static Board board;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		board = Board.getInstance();
		// Sets the config files to our layout and legend files
		board.setConfigFiles("JOMW_ClueLayout.csv", "JOMW_ClueLegend.txt", "MKGCv2_PlayersFile.txt"); 
		board.initialize();
	}

	// Tests for loading the people
	@Test
	public void testLoadingPeople() {
		fail("Not yet implemented");
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
