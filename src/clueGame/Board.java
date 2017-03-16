package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.awt.Color;
import java.lang.reflect.Field;

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
	public Set<Card> deck;
	public int numRooms = 0;
	public static int numPlayers = 0;
	public int numWeapons = 0;
	
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
		deck = new HashSet<Card>();
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
	
	public Color convertColor(String strColor) {
	    Color color; 
	    try {     
	        // We can use reflection to convert the string to a color
	        Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
	        color = (Color)field.get(null); 
	    } catch (Exception e) {  
	        color = null; // Not defined  
	    }
	    return color;
	}

	
	public void loadCardConfig() throws BadConfigFormatException {
		FileReader reader = null;
		Scanner in = null;
		
		try {
			reader = new FileReader(playerConfigFile);
			in = new Scanner(reader);
			int counter = 0;
			while (in.hasNext()) {
				String line = in.nextLine();
				String[] n = line.split(", ");
				String loadedCardType = n[0];
				if(loadedCardType.equals("Player")){
					if(n[1].equals("Pikachu")){
						Player human = new HumanPlayer(n[1], Integer.parseInt(n[3]), Integer.parseInt(n[4]), convertColor(n[2]));
						players[counter] = human;
						counter++;
						//Create a PERSON Card and add it to the deck
						Card card = new Card(n[1], CardType.PERSON);
						deck.add(card);
						numPlayers++;
						System.out.println("players: " + numPlayers);
					}
					// Add else statement to load other players
					else {
						Player comp = new ComputerPlayer(n[1], Integer.parseInt(n[3]), Integer.parseInt(n[4]), convertColor(n[2]));
						players[counter] = comp;
						counter++;
						Card card = new Card(n[1], CardType.PERSON);
						deck.add(card);
						numPlayers++;
						System.out.println("players: " + numPlayers);
					}
				}
				else if(loadedCardType.equals("Weapon")){
					Card card = new Card(n[1], CardType.WEAPON);
					deck.add(card);
					numWeapons++;
					System.out.println("weapons: " + numWeapons);
				}
				else throw new BadConfigFormatException();
			}
			for(Card d : deck){
				System.out.println(d);
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
				if(roomType.equals("Card")){
					Card card = new Card(roomName, CardType.ROOM);
					deck.add(card);
					numRooms++;
					System.out.println("rooms: " + numRooms);
				}
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
