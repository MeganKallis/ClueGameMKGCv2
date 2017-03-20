package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player{
	public ComputerPlayer(String pn, int r, int c, Color colo) {
		super(pn, r, c, colo);
		// TODO Auto-generated constructor stub
	}
	
	// Method that selects a location from the target list
	public BoardCell pickLocation(Set<BoardCell> targets){
		BoardCell temp = new BoardCell(0,0,'4');
		return temp;
	}

	public void setVisited(int i, int j) {
		// TODO Auto-generated method stub
		
	}
}
