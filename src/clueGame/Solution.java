package clueGame;

public class Solution {
	public Solution(Card person, Card room, Card weapon) {
		super();
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	public Solution() {
		this.person = null;
		this.room = null;
		this.weapon = null;
	}
	public Card person;
	public Card room;
	public Card weapon;
	
	
	public Card getPerson() {
		return person;
	}
	public Card getRoom() {
		return room;
	}
	public Card getWeapon() {
		return weapon;
	}
	
	public boolean equals(Solution s){
		if (this.person.equals(s.getPerson()) && (this.room.equals(s.getRoom()) && this.weapon.equals(s.getWeapon()))) {
			return true;
		}
		else return false;
	}
	public void setSolution(Card p, Card r, Card w) {
		person = p;
		room = r;
		weapon = w;
	}
	
}
