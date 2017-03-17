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
	
	
}
