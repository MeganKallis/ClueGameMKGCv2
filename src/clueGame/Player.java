package clueGame;

import java.awt.Color;

public class Player {
	private String playerName;
	private int row;
	private int col;
	public Color color;
	
	public Player(String pn, int r, int c, Color colo){
		playerName = pn;
		row = r;
		col = c;
		color = colo;
	}
	public void setPlayer(String pn, int r, int c, Color colo){
		playerName = pn;
		row = r;
		col = c;
		color = colo;
		return;
	}
	
	public boolean equals(Player p){
		if(playerName.equals(p.getPlayerName()) && row == p.getRow() && col == p.getCol() && color == p.getColor()){
			return true;
		} else return false;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Card disproveSuggestion(Solotion suggestion){
		Card temp = new Card();
		return temp;
	}
	
}
