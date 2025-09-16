package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class BlackjackTest {

    private Scanner scanner;

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
        //testPrintHandDiler

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        hand.printHandDiler(2);

        String output = outContent.toString();

        assertTrue(output.contains("    Карты диллера: "));
        assertTrue(output.contains("Туз Пик (11), Туз Пик (11)] => 12"));

        //testprintHandPlayer

        hand.printHandPlayer();

        output = outContent.toString();

        assertTrue(output.contains("    Ваши карты:"));
        assertTrue(output.contains("Туз Пик (11), Туз Пик (11)] => 12"));
        System.setOut(System.out);
    }

    @Test
    void testGameLogic() {
        CardLogic.Card card = new CardLogic.Card(14, 0);
        DeckLogic.topCard topcard = new DeckLogic.topCard(card, 11);
        HandLogic.Hand hand_1 = new HandLogic.Hand();
        HandLogic.Hand hand_2 = new HandLogic.Hand();
        hand_1.addCard(topcard);
        hand_1.addCard(topcard);
        hand_2.addCard(topcard);
        hand_2.addCard(topcard);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Game.printStatistics(2);

        String output = outContent.toString();
        assertTrue(output.contains("    Ваши карты:"));
        assertTrue(output.contains("] => 0"));
        assertTrue(output.contains("    Карты диллера: "));
        assertTrue(output.contains("] => 0"));

        System.setOut(System.out);
    }
}
