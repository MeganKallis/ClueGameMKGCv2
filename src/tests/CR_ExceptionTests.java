package tests;

/*
 * This program tests that, when loading config files, exceptions 
 * are thrown appropriately.
 */

import java.io.FileNotFoundException;

import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;

public class CR_ExceptionTests {

	// Test that an exception is thrown for a config file that does not 
	// have the same number of columns for each row
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		// Note that we are using a LOCAL Board variable, because each 
		// test will load different files
		Board board = Board.getInstance();
<<<<<<< HEAD
		board.setConfigFiles("CR_ClueLayoutBadColumns.csv", "CR_ClueLegend.txt", "");
=======
		board.setConfigFiles("CR_ClueLayoutBadColumns.csv", "CR_ClueLegend.txt","");
>>>>>>> fe16038ccca9d231b0d73536c5cd65079d7664e1
		// Instead of initialize, we call the two load functions directly.
		// This is necessary because initialize contains a try-catch. 
		board.loadRoomConfig();
		// This one should throw an exception
		board.loadBoardConfig();
	}
	
	// Test that an exception is thrown for a config file that specifies 
	// a room that is not in the legend. See first test for other important comments. 
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		Board board = Board.getInstance();
<<<<<<< HEAD
		board.setConfigFiles("CR_ClueLayoutBadRoom.csv", "CR_ClueLegend.txt", "");
=======
		board.setConfigFiles("CR_ClueLayoutBadRoom.csv", "CR_ClueLegend.txt","");
>>>>>>> fe16038ccca9d231b0d73536c5cd65079d7664e1
		board.loadRoomConfig();
		board.loadBoardConfig();
	}
	
	// Test that an exception is thrown for a config file with a room type 
	// that is not Card or Other
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		Board board = Board.getInstance();
<<<<<<< HEAD
		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegendBadFormat.txt", "");
=======
		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegendBadFormat.txt","");
>>>>>>> fe16038ccca9d231b0d73536c5cd65079d7664e1
		board.loadRoomConfig();
	}

}
