package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import clueGame.BoardCell;

import java.util.Scanner;

public class Board {
	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private Set<BoardCell> visited;
	private Player[] players;
	
	/*
	 * Default ctor for the board 
	 */
	private Board() {
		super();
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		legend = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		players = new Player[6];
	}

	/*
	 * Create the board instance using the Singleton pattern
	 */
	public static Board getInstance() {
		Board board = new Board();
		return board;
	}
	
	/*
	 * Initialize the board based on the config files
	 */
	public void initialize() {
		try {
			this.loadRoomConfig();
			this.loadBoardConfig();
			this.loadCardConfig();
		} catch (BadConfigFormatException e) {
			e.getMessage();
		}
		this.calcAdjacencies();
		return;
	}
	
	public void loadCardConfig() throws BadConfigFormatException {
		FileReader reader = null;
		Scanner in = null;
		
		try {
			reader = new FileReader(playerConfigFile);
			in = new Scanner(reader);
			while (in.hasNext()) {
				String line = in.nextLine();
				String[] n = line.split(", ");
				String loadedCardType = n[0];
				if(loadedCardType == "Player"){
					
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Card config File not found");
		} finally {
			in.close();
		}
		return;
	}

	/*
	 * Load the ClueLegend file into the legend map 
	 */
	public void loadRoomConfig() throws BadConfigFormatException {
		FileReader reader = null;
		Scanner in = null;
		
		try {
			reader = new FileReader(roomConfigFile);
			in = new Scanner(reader);
			while (in.hasNext()) {
				String line = in.nextLine();
				Character c = line.charAt(0);
				String roomName = line.split(", ")[1];
				String roomType = line.split(", ")[2];
				if (!roomType.equalsIgnoreCase("card") && !roomType.equalsIgnoreCase("other")) {
					String message = "Room \"" + roomName + "\" in config file \"" + roomConfigFile + "\" has the wrong type \"" + roomType + "\""; 
					throw new BadConfigFormatException(message);
				}
				legend.put(c, roomName);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Room config File not found :(");
		} finally {
			in.close();
		}
		return;
	}
	
	/*
	 * Create the Board based on the input file specified in boardConfigFile
	 */
	public void loadBoardConfig() throws BadConfigFormatException {
		FileReader reader = null;
		Scanner in = null;
		
		try { // catch FileNotFoundException for boardConfigFile
			reader = new FileReader(boardConfigFile);
			in = new Scanner(reader);
			
			int i = 0; // int to keep track of the current row
			int n = 0; // int to keep track of the number of columns in the current row
			while (in.hasNext()) {
				String line = in.nextLine();
				n = line.split(",").length; // get number of columns in this row
				
				if (i == 0) numColumns = n; // on first pass, set number of columns to test following rows
				if (n != numColumns) throw new BadConfigFormatException("\"" + boardConfigFile + "\" has an inconsistant number of columns"); // test if this row has the correct number of columns
				
				String[] s = new String[n]; // init array of strings
				s = line.split(","); // fill array of strings with row separated by ","
				for (int j = 0; j < n; ++j) { // for each element in the array, create a BoardCell at that location
					char c = s[j].charAt(0); // get initial for that BoardCell
					if (!legend.containsKey(c)) throw new BadConfigFormatException("\"" + c + "\" doesn't exist in " + roomConfigFile); // if the initial doesn't exist in the legend, throw exception
					if (s[j].length() == 1) { // make BoardCell that is not a doorway
						BoardCell b = new BoardCell(i, j, c);
						board[i][j] = b;
					}
					else { // make BoardCell that is a doorway
						char d = s[j].charAt(1);
						BoardCell b = new BoardCell(i, j, c, d);
						board[i][j] = b;
					}
				}
				i++;
			}
			numRows = i;
		} catch (FileNotFoundException e) {
			System.out.println("Room config File not found :(");
		} finally {
			in.close();
		}
		
		return;
	}
	
	public void calcAdjacencies() {
		for (int row = 0; row < numRows; ++row) {
			for (int col = 0; col < numColumns; ++col) {
				Set<BoardCell> adjs = new HashSet<BoardCell>();
				if (board[row][col].isDoorway()) {
					if (board[row][col].getDoorDirection() == DoorDirection.UP) {
						adjs.add(board[row-1][col]);
					}
					else if (board[row][col].getDoorDirection() == DoorDirection.DOWN) {
						adjs.add(board[row+1][col]);
					}
					else if (board[row][col].getDoorDirection() == DoorDirection.LEFT) {
						adjs.add(board[row][col-1]);
					}
					else if (board[row][col].getDoorDirection() == DoorDirection.RIGHT) {
						adjs.add(board[row][col+1]);
					}
				}
				else if (board[row][col].isWalkway()) {
					if (row > 0 && (board[row-1][col].isWalkway() || (board[row-1][col].getDoorDirection() == DoorDirection.DOWN))) {
						adjs.add(board[row-1][col]);
					}
					if (row + 1 < numRows && (board[row+1][col].isWalkway() || (board[row+1][col].getDoorDirection() == DoorDirection.UP))) {
						adjs.add(board[row+1][col]);
					}
					if (col > 0 && (board[row][col-1].isWalkway() || (board[row][col-1].getDoorDirection() == DoorDirection.RIGHT))) {
						adjs.add(board[row][col-1]);
					}
					if (col+1 < numColumns && (board[row][col+1].isWalkway() || (board[row][col+1].getDoorDirection() == DoorDirection.LEFT))) {
						adjs.add(board[row][col+1]);
					}
				}
				adjMatrix.put(board[row][col], adjs);
			}
		}
		return;
	}
	
	/*
	 * Calculate possible targets from a BoardCell and path length
	 */
	public void calcTargets(BoardCell cell, int pathLength) {
		targets.clear();
		recursiveCalcTargets(cell, pathLength);
		return;
	}
	
	public void recursiveCalcTargets(BoardCell cell, int pathLength) {
		visited.add(cell);
		for (BoardCell c : getAdjList(cell)) {
			if (visited.contains(c)) continue;
			visited.add(c);
			if (pathLength == 1 || c.isDoorway()) targets.add(c);
			else recursiveCalcTargets(c, pathLength - 1);
			visited.remove(c);
		}
		return;
	}
	
	/*
	 * Calculate possible targets from a cell at (row, col) and a path length
	 */
	public void calcTargets(int row, int col, int pathLength) {
		calcTargets(getCellAt(row, col), pathLength);
		return;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	/*
	 * Return adjacency list from a BoardCell
	 */
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMatrix.get(cell);
	}
	
	/*
	 * Return adjacency list from a cell at (row, col)
	 */
	public Set<BoardCell> getAdjList(int row, int col) {
		return getAdjList(getCellAt(row, col));
	}
	
	public void setConfigFiles(String boardFile, String roomFile, String playerFile) {
		boardConfigFile = boardFile;
		roomConfigFile = roomFile;
		playerConfigFile = playerFile;
		return;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}

	public Player[] getPlayers() {
		// TODO Auto-generated method stub
		return players;
	}
}
