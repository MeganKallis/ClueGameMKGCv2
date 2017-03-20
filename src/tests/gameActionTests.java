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
			soln.add(board.getCellAt(computer.pickLocation(temp).getRow(), computer.pickLocation(temp).getCol()));
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
			soln.add(board.getCellAt(computer.pickLocation(temp).getRow(), computer.pickLocation(temp).getCol()));
		}
		assertEquals(6, soln.size());
		// Tests that the just visisted room at (14, 5) is in the solution
		assertTrue(soln.contains(board.getCellAt(14, 5)));
		// Tests that a specific target at (11, 6) is in the solution
		assertTrue(soln.contains(board.getCellAt(11, 6)));

		
	}

}
