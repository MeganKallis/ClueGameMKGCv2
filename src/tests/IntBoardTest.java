package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTest {
	IntBoard board;
	
	/*
	 * Initialize the board with cells
	 */
	@Before
	public void initialize() {
		BoardCell[][] grid = new BoardCell[4][4];
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				grid[row][col] = new BoardCell(row,col);
			}
		}
		board = new IntBoard(grid);
	}

	/*
	 * Test adjacencies for top left corner
	 */
	@Test
	public void testAdjacency00() {
		BoardCell cell = board.getCell(0, 0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	/*
	 * Test adjacencies for bottom right corner
	 */
	@Test
	public void testAdjacency33() {
		BoardCell cell = board.getCell(3, 3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(2, testList.size());
	}
	
	/*
	 * Test adjacencies for right edge
	 */
	@Test
	public void testAdjacency13() {
		BoardCell cell = board.getCell(1, 3);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertEquals(3, testList.size());
	}
	
	/*
	 * Test adjacencies for left edge
	 */
	@Test
	public void testAdjacency20() {
		BoardCell cell = board.getCell(2, 0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(3, 0)));
		assertEquals(3, testList.size());
	}
	
	/*
	 * Test adjacencies for second column middle of board
	 */
	@Test
	public void testAdjacency11() {
		BoardCell cell = board.getCell(1, 1);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertEquals(4, testList.size());
	}
	
	/*
	 * Test adjacencies for third column middle of board
	 */
	@Test
	public void testAdjacency22() {
		BoardCell cell = board.getCell(2, 2);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(4, testList.size());
	}
	
	/*
	 * Test targets for top edge 1 space away
	 */
	@Test
	public void testTargets01_1() {
		BoardCell cell = board.getCell(0, 1);
		board.calcTargets(cell,  1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
	}
	
	/*
	 * Test targets for top edge 3 spaces away
	 */
	@Test
	public void testTargets01_3() {
		BoardCell cell = board.getCell(0, 1);
		board.calcTargets(cell,  3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(3, 1)));
	}
	
	/*
	 * Test targets for top edge 5 spaces away
	 */
	@Test
	public void testTargets01_5() {
		BoardCell cell = board.getCell(0, 1);
		board.calcTargets(cell,  5);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	/*
	 * Test targets for middle of board 2 spaces away
	 */
	@Test
	public void testTargets21_2() {
		BoardCell cell = board.getCell(2, 1);
		board.calcTargets(cell,  2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(3, 2)));
	}
	
	/*
	 * Test targets for middle of board 4 spaces away
	 */
	@Test
	public void testTargets21_4() {
		BoardCell cell = board.getCell(2, 1);
		board.calcTargets(cell,  4);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(3, 2)));
	}
	
	/*
	 * Test targets for middle of board 6 spaces away
	 */
	@Test
	public void testTargets21_6() {
		BoardCell cell = board.getCell(2, 1);
		board.calcTargets(cell,  6);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(3, 2)));
	}

}
