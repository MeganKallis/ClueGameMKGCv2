package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private BoardCell[][] grid;
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;

	/*
	 * Initialize the board and calculate the adjacencies
	 */
	public IntBoard(BoardCell[][] grid) {
		super();
		this.grid = grid;
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		calcAdjacencies();
	}
	
	/*
	 * Adds adjacent cells to all cells in the grid to the adjMtx map.
	 * When adjs.add() is called, the parameter must be grid[i][j] so it
	 * points directly to the data in memory instead of a new BoardCell.
	 * This makes checking if the map contains a cell easier.
	 */
	public void calcAdjacencies() {
		for (int row = 0; row < grid.length; ++row) {
			for (int col = 0; col < grid[row].length; ++col) {
				Set<BoardCell> adjs = new HashSet<BoardCell>();
				if (row > 0) {
					adjs.add(grid[row-1][col]);
				}
				if (row + 1 < grid.length) {
					adjs.add(grid[row+1][col]);
				}
				if (col > 0) {
					adjs.add(grid[row][col-1]);
				}
				if (col+1 < grid[row].length) {
					adjs.add(grid[row][col+1]);
				}
				adjMtx.put(grid[row][col], adjs);
			}
		}
		return;
	}
	
	/*
	 * Recursively calculate all the targets for a certain cell and a certain path length
	 */
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited.add(startCell);
		for (BoardCell c : getAdjList(startCell)) {
			if (visited.contains(c)) continue;
			visited.add(c);
			if (pathLength == 1) targets.add(c);
			else calcTargets(c, pathLength - 1);
			visited.remove(c);
		}
		return;
	}
	
	/*
	 * Return all the targets that were calculated for a cell
	 */
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	/*
	 * Returns the set of all adjacent cells for a certain cell
	 */
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}
	
	/*
	 * Returns a BoardCell on the grid
	 */
	public BoardCell getCell(int row, int col) {
		return grid[row][col]; 
	}
}
