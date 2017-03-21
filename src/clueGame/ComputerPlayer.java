package clueGame;

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

public class ComputerPlayer extends Player{
	private int visitedRow;
	private int visitedCol;
	private static Board board;
	private Set<Card> unseenWeapon;
	private Set<Card> unseenPersons;
	public ComputerPlayer(String pn, int r, int c, Color colo) {
		super(pn, r, c, colo);
		board = Board.getInstance();
		unseenWeapon = new HashSet<Card>();
		unseenPersons = new HashSet<Card>();
		// TODO Auto-generated constructor stub
	}
	
	// Method that selects a location from the target list
	public BoardCell pickLocation(Set<BoardCell> targets){
		for(BoardCell b: targets){
			if (b.isDoorway() && (b.getRow() != visitedRow || b.getCol() != visitedCol)){
				return b;
			}
		}
		int counter = 0;
		int randomNum = (int)(Math.random()*targets.size());
		for(BoardCell b: targets){
			if(counter == randomNum) {
				return b; 
			}
			else counter++;
		}
		return null;
	}

	public void setVisited(int i, int j) {
		visitedRow = i;
		visitedCol = j;
	}

	public Solution suggestion() {
		System.out.println(unseenWeapon.iterator().next());
		return null;
	}

	public void setUnseenWeapon(Set<Card> theUnseen) {
		unseenWeapon = theUnseen;	
	}

	public void setUnseenPersons(Set<Card> theUnseen) {
		unseenPersons = theUnseen;
		
	}
}
