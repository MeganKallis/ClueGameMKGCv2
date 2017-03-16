package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", cardType=" + cardType + "]";
	}

	public Card(String cardName, CardType cardType) {
		super();
		this.cardName = cardName;
		this.cardType = cardType;
	}

	public Card() {
		// TODO Auto-generated constructor stub
	}

	public boolean equals(){
		return true;
	}
}
