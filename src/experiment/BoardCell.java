package experiment;

public class BoardCell {
	private int row;
	private int col;
	
	/*
	 * Initialize the cell with a certain row and column
	 */
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	/*
	 * Return the row of the cell
	 */
	public int getRow() {
		return row;
	}
	
	/*
	 * Return the column of the cell
	 */
	public int getCol() {
		return col;
	}
	
	/*
	 * Output the cell data, mainly for debugging
	 */
	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", col=" + col + "]";
	}
	
}
