package tests;

import static org.junit.Assert.*;
import java.awt.Color;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
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
	// Ensures the deck contains the correct total number of cards
	// Ensures the deck contains the correct number of each type of card
	// Ensures deck contains at least one room, one weapon, and one person
	@Test
	public void testLoadingCards() {
		int numberOfRooms = board.numRooms;
		int numberOfWeapons = board.numWeapons;
		int numberOfPlayers = Board.numPlayers;
		// Deck contains correct number of cards
		int expectedNumberOfCards = numberOfRooms + numberOfWeapons + numberOfPlayers;
		assertEquals(expectedNumberOfCards, board.deck.size());
		// Deck contains the correct number of each type of card
		assertEquals(9, numberOfRooms);
		assertEquals(6, numberOfWeapons);
		assertEquals(6, numberOfPlayers);
		// Deck contains at least one room
		Card kitchenTestCard = new Card("Kitchen", CardType.ROOM); 
		for (Card c : board.deck) {
			if (c.equals(kitchenTestCard))
					assertTrue(board.deck.contains(kitchenTestCard));
		}
		// Deck contains at least one player
		Card eeveeTestCard = new Card("Eevee", CardType.PERSON); 
		for (Card c : board.deck) {
			if (c.equals(eeveeTestCard))
					assertTrue(board.deck.contains(eeveeTestCard));
		}
		// Deck contains at least one weapon
		Card ThunderboltTestCard = new Card("Thunderbolt", CardType.WEAPON); 
		for (Card c : board.deck) {
			if (c.equals(ThunderboltTestCard))
					assertTrue(board.deck.contains(ThunderboltTestCard));
		}
		
	}
	
	// Tests for dealing the cards
	// All cards should be dealt
	// All players should have roughly the same number of cards
	// The same card should not be given to >1 player
	@Test
	public void testDealingCards() {
		// All cards should be dealt
		
	}
}
