package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class gameActionTests {

	private static Board board;

	//Setup everything in the board with the config files
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		board = Board.getInstance();
		// Sets the config files to our layout and legend files
		board.setConfigFiles("JOMW_ClueLayout.csv", "JOMW_ClueLegend.txt", "MKGCv2_CardFile.txt"); 
		board.initialize();
	}
	
	// TESTS FOR SELECTING A TARGET
	@Test
	public void selectTargetTest() {
		// Test if no rooms in list, select randomly
		Set<BoardCell> temp = new HashSet();
		board.calcTargets(7,13, 2);
		temp = board.getTargets();
		ComputerPlayer computer = new ComputerPlayer("computer", 7, 13, Color.red);
		Set<BoardCell> soln = new HashSet();
		// Runs pickLocation a thousand times to ensure different random results
		for (int i = 0; i < 1000; i++) {
			BoardCell testBoardCell = new BoardCell(0, 0, 'c');
			testBoardCell = computer.pickLocation(temp);
			soln.add(board.getCellAt(testBoardCell.getRow(), testBoardCell.getCol()));
		}
		// Tests that all five possible targets get chosen at least once
		assertEquals(5, soln.size());
		// Tests that a specific target at (5,13) is in the solution
		assertTrue(soln.contains(board.getCellAt(5, 13)));
		
		
		// Test if room in list that was not just visited, must select it
		board.calcTargets(13, 6, 2);
		temp = board.getTargets();
		assertTrue(computer.pickLocation(temp).isDoorway());
		
		// Test if room just visited is in list, each target (including room) selected randomly
		computer.setVisited(14, 5);
		soln.clear();
		
		for (int i = 0; i < 1000; i++) {
			soln.add(computer.pickLocation(temp));
		}
		assertEquals(6, soln.size());
		// Tests that the just visited room at (14, 5) is in the solution
		assertTrue(soln.contains(board.getCellAt(14, 5)));
		// Tests that a specific target at (11, 6) is in the solution
		assertTrue(soln.contains(board.getCellAt(11, 6)));

	}
	@Test
	public void makeAccusationTest(){
		//Creating cards for the solution tests and creating a test Solution
		Card p = new Card ("Test Person Card", CardType.PERSON);
		Card r = new Card ("Test Room Card", CardType.ROOM);
		Card w = new Card ("Test Weapon Card", CardType.WEAPON);
		Card o = new Card("Other", CardType.PERSON);
		Solution sol = new Solution(p, r, w);
		
		
		board.setSolution(p, r, w);
		
		//Testing that test Solution returns true with all correct cards
		assertTrue(sol.equals(board.getSolution()));
		
		//Testing that test Solution returns false with wrong person card
		sol.setSolution(o, r, w);
		assertFalse(sol.equals(board.getSolution()));
		
		//Testing that test Solution returns false with wrong room card
		sol.setSolution(p, o, w);
		assertFalse(sol.equals(board.getSolution()));
		
		//Testing that test Solution returns false with wrong weapon card
		sol.setSolution(p, r, o);
		assertFalse(sol.equals(board.getSolution()));
		
	}
	
	@Test
	public void createSuggestionTest(){
		//Creating cards for the suggestion tests and creating a test Solution
		Card p = new Card ("Pikachu", CardType.PERSON);
		Card r = new Card ("Kitchen", CardType.ROOM);
		Card w = new Card ("Tail-Whip", CardType.WEAPON);
		Solution suggestion = new Solution(p, r, w);
		ComputerPlayer testPlayer = new ComputerPlayer("Billy", 20, 22, Color.cyan);
		board.setSuggestion(p, r, w);
		
		Set<Card> theUnseenWeapon = new HashSet<Card>();
		Set<Card> theUnseenPersons = new HashSet<Card>();
		theUnseenWeapon.add(w);
		theUnseenPersons.add(p);
		testPlayer.setUnseenWeapon(theUnseenWeapon);
		testPlayer.setUnseenPersons(theUnseenPersons);
		
		//Test that the suggestion is in the same room as the player
		assertEquals(testPlayer.makeSuggestion(board).getRoom().getCardName(), board.getLegend().get(board.getCellAt(testPlayer.getRow(), testPlayer.getCol()).getInitial()));
		
		
		//Test only one weapon possibility
		theUnseenWeapon.add(w);
		testPlayer.setUnseenWeapon(theUnseenWeapon);
		assertEquals(w, testPlayer.makeSuggestion(board).getWeapon());
		
		//Test multiple weapons that expect a random suggestion
		Set<Card> soln = new HashSet();
		Card weapon = new Card("Flamethrower", CardType.WEAPON);
		theUnseenWeapon.add(weapon);
		
		for(int i = 0; i < 100; i++){
			soln.add(testPlayer.makeSuggestion(board).getWeapon());
		}
		assertEquals(soln.size(), 2);
		assertTrue(soln.contains(w));
		assertTrue(soln.contains(weapon));

		
		//Test only one person possibility
		assertEquals(p, testPlayer.makeSuggestion(board).getPerson());
		
		//Test multiple weapons that expect a random suggestion
		soln.clear();
		Card person = new Card("Eevee", CardType.PERSON);
		theUnseenPersons.add(person);
		
		for(int i = 0; i < 100; i++){
			soln.add(testPlayer.makeSuggestion(board).getPerson());
		}
		assertEquals(soln.size(), 2);
		assertTrue(soln.contains(p));
		assertTrue(soln.contains(person));
	}
	
	@Test
	public void disproveSuggestionTest(){
		//Creating cards for the disprove suggestion tests and creating a test Solution
		ComputerPlayer testPlayer = new ComputerPlayer("Billy", 20, 22, Color.cyan);
		Card p = new Card ("Pikachu", CardType.PERSON);
		Card r = new Card ("Kitchen", CardType.ROOM);
		Card w = new Card ("Tail-Whip", CardType.WEAPON);
		Solution suggestion = new Solution(p, r, w);
		// Adds matching card to player's hand
		testPlayer.addCard(p);
		// If player has only one matching card it should be returned
		assertEquals(p, testPlayer.disproveSuggestion(suggestion));
		// Adds another matching card to player's hand
		testPlayer.addCard(w);
		//Test multiple times to ensure that disproveSuggestion behavior is random
		Set<Card> soln = new HashSet();
		for(int i = 0; i < 100; i++){
			soln.add(testPlayer.disproveSuggestion(suggestion));
		}
		assertEquals(soln.size(), 2);
		assertTrue(soln.contains(p));
		assertTrue(soln.contains(w));
	
		ComputerPlayer testPlayer2 = new ComputerPlayer("Mandy", 20, 22, Color.red);
		Card q = new Card ("Eevee", CardType.PERSON);
		// Adds non-matching card to player's hand
		testPlayer.addCard(q);
		// If player has no matching cards, null should be returned
		assertNull(testPlayer2.disproveSuggestion(suggestion));
	}
	
	@Test
	public void handleSuggestionTest(){
		for (int i = 0; i < board.getPlayers().length; i++) {
			System.out.println(board.getPlayers()[i]);
		}
		// Setup solution
		Card p = new Card ("Pikachu", CardType.PERSON);
		Card r = new Card ("Kitchen", CardType.ROOM);
		Card w = new Card ("Tail-Whip", CardType.WEAPON);
		Solution suggestion = new Solution(p, r, w);
		// Setup player1 to have no matching cards
		Card a = new Card ("Bulbasaur", CardType.PERSON);
		board.setPlayerCard(0, a);
		// Setup player2 to have no matching cards
		Card b = new Card ("Oddish", CardType.PERSON);
		board.setPlayerCard(1, b);
		
		// Tests if suggestion no one can disprove returns null
		board.handleSuggestion(suggestion, "Pikachu");
		
		// Changes solution so accuser player has matching card
		suggestion.setSolution(b, r, w);
		// Tests if suggestion only accusing player can disprove returns null
		board.handleSuggestion(suggestion, "Eevee");
		
		// 
		
	}
	
}
