package tests;

import static org.junit.Assert.*;
import java.awt.Color;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;

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
		boolean testBoolean = false;
		for (Card c : board.deck) {
			if (c.equals(kitchenTestCard)) testBoolean = true;
		}
		assertTrue(testBoolean);
		// Deck contains at least one player
		testBoolean = false;
		Card eeveeTestCard = new Card("Eevee", CardType.PERSON); 
		for (Card c : board.deck) {
			if (c.equals(eeveeTestCard)) testBoolean = true;
		}
		assertTrue(testBoolean);
		// Deck contains at least one weapon
		Card ThunderboltTestCard = new Card("Thunderbolt", CardType.WEAPON); 
		testBoolean = false;
		for (Card c : board.deck) {
			if (c.equals(ThunderboltTestCard)) testBoolean = true;
		}
		assertTrue(testBoolean);
		
	}
	
	// Tests for dealing the cards
	// All cards should be dealt
	// All players should have roughly the same number of cards
	// The same card should not be given to >1 player
	@Test
	public void testDealingCards() {
		board.dealDeck();
		// All cards should be dealt
		assertEquals(0, board.deck.size());
		
		// All players should have roughly the same number of cards
		int handMax = 0;
		int handMin = 2000;
		for(int i = 0; i < board.getPlayers().length; i++) {
			if (board.getPlayers()[i].getHand().size() < handMin) {
				handMin = board.getPlayers()[i].getHand().size();
			}
			if (board.getPlayers()[i].getHand().size() > handMax) {
				handMax = board.getPlayers()[i].getHand().size();
			}
		}
		assertTrue((handMax - handMin) <= 1);
		
		// The same card should not be given to > 1 player
		//
		// Deck contains at least one room
		Card darkroomTestCard = new Card("Darkroom", CardType.ROOM); 
		int darkroomTestNum = 0;
		for (int i = 0; i < board.getPlayers().length; i++) {
			for (Card c : board.getPlayers()[i].getHand()) {
				if (c.equals(darkroomTestCard)) {
					darkroomTestNum++;
				}
			}
		}
		assertEquals(1, darkroomTestNum);
		
		// TESTS SOLUTION
		//
		// Tests that solution contains a person
		assertTrue(board.getSolution().getPerson().getCardType() == CardType.PERSON);
		// Tests that solution contains a room
		assertTrue(board.getSolution().getRoom().getCardType() == CardType.ROOM);
		// Tests that solution contains a weapon
		assertTrue(board.getSolution().getWeapon().getCardType() == CardType.WEAPON);
	}
}
