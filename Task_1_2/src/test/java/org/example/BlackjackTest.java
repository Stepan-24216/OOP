package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlackjackTest {

    @Test
    void testCardLogic() {
        CardLogic.Card card = new CardLogic.Card(10, 0);
        assertEquals(10, card.getValue());
        assertEquals(10, card.getRank());
        assertEquals(0, card.getSuit());
        assertEquals("10 Пик", card.toString());

        card.setValue(777);
        card.setRank(2);
        card.setSuit(3);

        assertEquals(777, card.getValue());
        assertEquals(2, card.getRank());
        assertEquals(3, card.getSuit());
    }

    @Test
    void tesDeckLogic() {
        CardLogic.Card card = new CardLogic.Card(10, 0);
        DeckLogic.topCard topcard = new DeckLogic.topCard(card, 10);
        assertEquals(10, topcard.getValue());
        assertEquals("10 Пик (10)", topcard.toString());

        DeckLogic.Deck deck = new DeckLogic.Deck(1);
        DeckLogic.topCard cur = deck.takeCard();
        cur.getCard();
    }

    @Test
    void testHandLogic() {
        CardLogic.Card card = new CardLogic.Card(14, 0);
        DeckLogic.topCard topcard = new DeckLogic.topCard(card, 11);
        HandLogic.Hand hand = new HandLogic.Hand();
        hand.addCard(topcard);

        hand.win();
        assertEquals(1, hand.getScore());
        assertEquals(11, hand.getValue());

        hand.addCard(topcard);
        assertEquals(12, hand.getValue());
    }

    @Test
    void testMainLogic() {

    }
}
