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
	private static Board board = Board.getInstance();
	private Set<Card> unseenWeapon;
	private Set<Card> unseenPersons;
	public ComputerPlayer(String pn, int r, int c, Color colo) {
		super(pn, r, c, colo);
		
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

	public Solution makeSuggestion(Board b) {
		Card wSuggestion= null;
		Card pSuggestion = null;
		Card rSuggestion = null;
		// Set weapon part of suggestion
	
			// Choose random weapon in unseen set
			
			int counter = 0;
			int rand = (int)(Math.random() * unseenWeapon.size());
			for (Card c : unseenWeapon) {
				if (counter == rand) {
					wSuggestion = c;
					break;
				}
				else counter++;
			}
			
		// Set person part of suggestion
	
			// Choose random weapon in unseen set
			
			counter = 0;
			rand = (int)(Math.random() * unseenPersons.size());
			for (Card c : unseenPersons) {
				if (counter == rand) {
					pSuggestion = c;
					break;
				}
				else counter++;
			}
			
		// Set room part of suggestion
		rSuggestion = new Card(b.getLegend().get(b.getCellAt(this.getRow(), this.getCol()).getInitial()), CardType.ROOM);
		// Create suggestion
		Solution newSuggestion = new Solution(pSuggestion, rSuggestion, wSuggestion);
		
		return newSuggestion;

	}
	

	public void setUnseenWeapon(Set<Card> theUnseen) {
		unseenWeapon = theUnseen;	
	}

	public void setUnseenPersons(Set<Card> theUnseen) {
		unseenPersons = theUnseen;
		
	}
}
