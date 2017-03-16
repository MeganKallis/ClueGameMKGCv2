package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class JOMW_BoardAdjTargetTests {
	private static Board board;
	
	@Before
	public void initialize() {		
		board = Board.getInstance();
<<<<<<< HEAD
		board.setConfigFiles("JOMW_ClueLayout.csv", "JOMW_ClueLegend.txt", "MKGCv2_PlayersFile.txt");	
=======
		board.setConfigFiles("JOMW_ClueLayout.csv", "JOMW_ClueLegend.txt","");		
>>>>>>> fe16038ccca9d231b0d73536c5cd65079d7664e1
		board.initialize();
		return;
	}
	
	/*
	 * Make sure locations at any location in the room have no adjacent possibilities
	 */
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(1, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(4, 10);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(8, 20);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(13, 5);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(17, 19);
		assertEquals(0, testList.size());
	}
	
	/*
	 * Make sure there's only 1 adjacency for a door.
	 */
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(9, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(10, 3)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(11, 19);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(10, 19)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(14, 8);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 7)));
		//TEST DOORWAY UP
		testList = board.getAdjList(18, 14);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(18, 15)));
		
	}
	
	
	/*
	 * Tests adjacencies outside a doorway
	 */
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction DOWN
		Set<BoardCell> testList = board.getAdjList(9, 19);
		assertTrue(testList.contains(board.getCellAt(8, 19)));
		assertTrue(testList.contains(board.getCellAt(10, 19)));
		assertTrue(testList.contains(board.getCellAt(9, 18)));
		assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(12, 10);
		assertTrue(testList.contains(board.getCellAt(11, 10)));
		assertTrue(testList.contains(board.getCellAt(13, 10)));
		assertTrue(testList.contains(board.getCellAt(12, 9)));
		assertTrue(testList.contains(board.getCellAt(12, 11)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(14, 7);
		assertTrue(testList.contains(board.getCellAt(13, 7)));
		assertTrue(testList.contains(board.getCellAt(15, 7)));
		assertTrue(testList.contains(board.getCellAt(14, 6)));
		assertTrue(testList.contains(board.getCellAt(14, 8)));
		assertEquals(4, testList.size());
		// Test beside a door direction Right
		testList = board.getAdjList(1, 13);
		assertTrue(testList.contains(board.getCellAt(0, 13)));
		assertTrue(testList.contains(board.getCellAt(1, 12)));
		assertTrue(testList.contains(board.getCellAt(1, 14)));
		assertTrue(testList.contains(board.getCellAt(2, 13)));
		assertEquals(4, testList.size());
	}

	/*
	 * Test adjacenies for various walkway positions.
	 */
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, two walkway pieces
		Set<BoardCell> testList = board.getAdjList(0, 6);
		assertTrue(testList.contains(board.getCellAt(0, 5)));
		assertTrue(testList.contains(board.getCellAt(1, 6)));
		assertEquals(2, testList.size());
		
		// Test on left edge of board, two walkway pieces
		testList = board.getAdjList(11, 0);
		assertTrue(testList.contains(board.getCellAt(10, 0)));
		assertTrue(testList.contains(board.getCellAt(11, 1)));
		assertEquals(2, testList.size());

		// Test between two rooms, walkways up and down
		testList = board.getAdjList(18, 10);
		assertTrue(testList.contains(board.getCellAt(17, 10)));
		assertTrue(testList.contains(board.getCellAt(19, 10)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(10, 6);
		assertTrue(testList.contains(board.getCellAt(9, 6)));
		assertTrue(testList.contains(board.getCellAt(11, 6)));
		assertTrue(testList.contains(board.getCellAt(10, 5)));
		assertTrue(testList.contains(board.getCellAt(10, 7)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(21, 6);
		assertTrue(testList.contains(board.getCellAt(20, 6)));
		assertTrue(testList.contains(board.getCellAt(21, 7)));
		assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(5, 23);
		assertTrue(testList.contains(board.getCellAt(5, 22)));
		assertTrue(testList.contains(board.getCellAt(6, 23)));
		assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(17, 14);
		assertTrue(testList.contains(board.getCellAt(17, 13)));
		assertTrue(testList.contains(board.getCellAt(17, 15)));
		assertTrue(testList.contains(board.getCellAt(16, 14)));
		assertEquals(3, testList.size());
	}
	
	/*
	 * Test target list for one step away
	 */
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(4, 6, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 6)));
		assertTrue(targets.contains(board.getCellAt(4, 5)));	
		assertTrue(targets.contains(board.getCellAt(5, 6)));
		
		board.calcTargets(14, 23, 1);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 22)));
		assertTrue(targets.contains(board.getCellAt(13, 23)));		
	}
	
	/*
	 * test target list for two steps away
	 */
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(0, 14, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 13)));
		assertTrue(targets.contains(board.getCellAt(2, 14)));
		
		board.calcTargets(5, 17, 2);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 16)));
		assertTrue(targets.contains(board.getCellAt(4, 18)));	
		assertTrue(targets.contains(board.getCellAt(5, 15)));
		assertTrue(targets.contains(board.getCellAt(5, 19)));
		assertTrue(targets.contains(board.getCellAt(6, 16)));
		assertTrue(targets.contains(board.getCellAt(6, 18)));
	}
	
	/*
	 * test target list for four steps away
	 */
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(19, 10, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 11)));
		assertTrue(targets.contains(board.getCellAt(17, 12)));
		assertTrue(targets.contains(board.getCellAt(20, 9)));
		assertTrue(targets.contains(board.getCellAt(21 ,10)));
		assertTrue(targets.contains(board.getCellAt(20, 7)));
		assertTrue(targets.contains(board.getCellAt(21, 8)));
		assertTrue(targets.contains(board.getCellAt(20, 11)));
	}	
	
	/*
	 * test target list for six steps away
	 */
	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(12, 7, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(29, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 13)));
		assertTrue(targets.contains(board.getCellAt(13 ,10)));	
		assertTrue(targets.contains(board.getCellAt(11, 12)));	
		assertTrue(targets.contains(board.getCellAt(10, 11)));	
		assertTrue(targets.contains(board.getCellAt(11, 10)));	
		assertTrue(targets.contains(board.getCellAt(10, 9)));	
		assertTrue(targets.contains(board.getCellAt(11, 8)));
		assertTrue(targets.contains(board.getCellAt(12, 9)));
		assertTrue(targets.contains(board.getCellAt(12, 11)));
		assertTrue(targets.contains(board.getCellAt(7, 6)));
		assertTrue(targets.contains(board.getCellAt(8, 5)));
		assertTrue(targets.contains(board.getCellAt(8, 7)));
		assertTrue(targets.contains(board.getCellAt(9, 6)));
		assertTrue(targets.contains(board.getCellAt(10, 5)));
		assertTrue(targets.contains(board.getCellAt(10, 7)));
		assertTrue(targets.contains(board.getCellAt(11, 6)));
		assertTrue(targets.contains(board.getCellAt(13, 6)));
		assertTrue(targets.contains(board.getCellAt(14, 7)));
		assertTrue(targets.contains(board.getCellAt(18, 7)));
		assertTrue(targets.contains(board.getCellAt(16, 7)));
		assertTrue(targets.contains(board.getCellAt(17, 6)));
		assertTrue(targets.contains(board.getCellAt(15, 6)));
		assertTrue(targets.contains(board.getCellAt(12, 5)));
		assertTrue(targets.contains(board.getCellAt(10, 3)));
		assertTrue(targets.contains(board.getCellAt(11, 2)));
		assertTrue(targets.contains(board.getCellAt(11, 4)));
		assertTrue(targets.contains(board.getCellAt(14, 8)));
		assertTrue(targets.contains(board.getCellAt(14, 5)));
		assertTrue(targets.contains(board.getCellAt(12, 3)));
		
	}	
	
	/*
	 * tests getting into room exactly two steps away
	 */
	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(20, 9, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		// directly left and right
		assertTrue(targets.contains(board.getCellAt(20, 7)));
		assertTrue(targets.contains(board.getCellAt(20, 11)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(21, 8)));
		assertTrue(targets.contains(board.getCellAt(21, 10)));
		assertTrue(targets.contains(board.getCellAt(19, 10)));
	}
	
	/*
	 * tests getting into room closer that given steps
	 */
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(10, 19, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(8, 18)));
		assertTrue(targets.contains(board.getCellAt(10, 18)));
		assertTrue(targets.contains(board.getCellAt(12, 18)));
		assertTrue(targets.contains(board.getCellAt(9, 19)));
		// into the rooms
		assertTrue(targets.contains(board.getCellAt(8, 19)));
		assertTrue(targets.contains(board.getCellAt(11, 19)));		
		// 
	}
	
	/*
	 * tests target list for getting out of room at one and two steps
	 */
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(12, 3, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(11, 3)));
		// Take two steps
		board.calcTargets(12, 3, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(11, 4)));
		assertTrue(targets.contains(board.getCellAt(11, 2)));
		assertTrue(targets.contains(board.getCellAt(10, 3)));
	}
}
