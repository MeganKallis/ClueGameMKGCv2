package clueGame;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	
	/*
	 * Initialize the cell with a certain row and column
	 */
	public BoardCell(int row, int col, char initial) {
		super();
		this.row = row;
		this.col = col;
		this.initial = initial;
		doorDirection = DoorDirection.NONE;
	}
	
	/*
	 * Ctor for when a BoardCell is a doorway
	 */
	public BoardCell(int row, int col, char initial, char doorDirection) {
		super();
		this.row = row;
		this.col = col;
		this.initial = initial;
		setDoorDirection(doorDirection);
	}
	
	/*
	 * Method to set door direction with a char
	 */
	public void setDoorDirection(char doorDirection) {
		if (Character.toLowerCase(doorDirection) == 'u') this.doorDirection = DoorDirection.UP;
		else if (Character.toLowerCase(doorDirection) == 'd') this.doorDirection = DoorDirection.DOWN;
		else if (Character.toLowerCase(doorDirection) == 'l') this.doorDirection = DoorDirection.LEFT;
		else if (Character.toLowerCase(doorDirection) == 'r') this.doorDirection = DoorDirection.RIGHT;
		else this.doorDirection = DoorDirection.NONE;
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
	
	/*
	 * Returns true only if the character is a w, which is a walkway in our layout file
	 */
	public boolean isWalkway() {
		if (initial == 'W') return true;
		return false;
	}
	
	/*
	 * If it is not the closet and not a walkway, the cell is a room. This includes the doorways
	 */
	public boolean isRoom() {
		if (initial != 'X' && initial != 'W') return true;
		return false;
	}
	
	/*
	 * Returns true if there is a door direction, which would imply that it is a door
	 */
	public boolean isDoorway() {
		if (doorDirection == DoorDirection.NONE) return false;
		return true;
	}

	/*
	 * Return the value of initial
	 */
	public char getInitial() {
		return initial;
	}
	
	/*
	 * Return the enumeration value of doorDirection
	 */
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
}
