package tests;

/*
 * authors: John Ocker and Mitchell Waibel
 * 
 * Test package to make sure our config files are imported as they should be
 */

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class JOMW_FileInitTests {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 24;

	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		// Sets the config files to our layout and legend files
		board.setConfigFiles("JOMW_ClueLayout.csv", "JOMW_ClueLegend.txt"); 
		board.initialize();
	}
	@Test
	public void testRooms() {
		// Get the map of initial => room 
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size()); 
		// check the legend for all possible values
		assertEquals("Living Room", legend.get('L'));
		assertEquals("Den", legend.get('D'));
		assertEquals("Bathroom", legend.get('B'));
		assertEquals("Video Game Room", legend.get('V'));
		assertEquals("Observatory", legend.get('O'));
		assertEquals("Computer Room", legend.get('C'));
		assertEquals("Kitchen", legend.get('K'));
		assertEquals("Darkroom", legend.get('A'));
		assertEquals("Green Room", legend.get('G'));
		assertEquals("Closet", legend.get('X'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
	// two cells that are not a doorway.
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(1, 12);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(9, 3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(14, 8);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(11, 19);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces are not doors
		room = board.getCellAt(5, 9);
		assertFalse(room.isDoorway());
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(10, 12);
		assertFalse(cell.isDoorway());		

	}
	
	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(16, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRoomInitials() {
		// Test first cell in room
		assertEquals('L', board.getCellAt(0, 0).getInitial());
		assertEquals('B', board.getCellAt(0, 15).getInitial());
		assertEquals('V', board.getCellAt(14, 8).getInitial());
		// Test last cell in room
		assertEquals('D', board.getCellAt(9, 10).getInitial());
		assertEquals('K', board.getCellAt(21, 23).getInitial());
		// Test a walkway
		assertEquals('W', board.getCellAt(8, 13).getInitial());
		// Test the closet
		assertEquals('X', board.getCellAt(12, 16).getInitial());
	}
	

}
