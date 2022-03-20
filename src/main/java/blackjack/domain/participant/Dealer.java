package blackjack.domain.participant;

import java.util.function.Supplier;

import blackjack.domain.BettingAmount;
import blackjack.domain.BlackJack;
import blackjack.domain.Outcome;
import blackjack.domain.card.Hand;

public class Dealer extends Participant {

	private static final int CAN_DRAW_STANDARD = 16;
	private static final String DEALER_NAME = "딜러";

	private final Supplier<Boolean> drawable;

	public Dealer(final Hand hand, final Supplier<Boolean> drawable) {
		super(DEALER_NAME, hand, new BettingAmount(10));
		this.drawable = drawable;
	}

	@Override
	public void distributeBattingAmount(final Participant player) {
		final Outcome outcome = judgeCompeteResult(calculateFinalScore(), player.calculateFinalScore());
		if (outcome == Outcome.VICTORY) {
			final int finalIncome = battingAmount.getTotalValue() + player.getCurrentIncome();
			battingAmount = new BettingAmount(finalIncome, battingAmount.getInitialValue());
			player.loseAll();
		}
	}

	@Override
	public boolean canDraw() {
		if (hand.calculateScore() >= BlackJack.OPTIMIZED_WINNING_NUMBER) {
			return false;
		}
		if (hand.calculateScore() <= CAN_DRAW_STANDARD) {
			return true;
		}
		if (!hand.hasAce()) {
			return false;
		}
		return drawable.get();
	}

	@Override
	public int getDrawStandard() {
		return CAN_DRAW_STANDARD;
	}
}