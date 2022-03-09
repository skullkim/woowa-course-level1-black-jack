package blackjack.domain;

public abstract class Role {

	protected final String name;
	protected final Hand hand;

	public Role(final String name, final Hand hand) {
		this.name = name;
		this.hand = hand;
	}

	public void draw(final Card card) {
		hand.addCard(card);
	}

	public int calculateFinalScore() {
		return hand.calculateOptimalScore();
	}

	public abstract boolean canDraw();

	public String getName() {
		return name;
	}

	public Hand getHand() {
		return hand;
	}
}