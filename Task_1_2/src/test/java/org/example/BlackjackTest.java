package org.example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для игры Blackjack
 */
public class BlackjackTest {
    /**
     * Тест логики карт
     */
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
        DeckLogic.TopCard topcard = new DeckLogic.TopCard(card, 10);
        assertEquals(10, topcard.getValue());
        assertEquals("10 Пик (10)", topcard.toString());

        DeckLogic.Deck deck = new DeckLogic.Deck(1);
        DeckLogic.TopCard cur = deck.takeCard();
        CardLogic.Card topcard2 = cur.getCard();
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8",
                "9", "10", "Валет", "Дама", "Король", "Туз"};
        String[] suits = {"Пик", "Червей", "Бубен", "Треф"};
        assertEquals(ranks[topcard2.getRank() - 2] + ' ' + suits[topcard2.getSuit()], topcard2.toString());
    }

    @Test
    void testHandLogic() {
        CardLogic.Card card = new CardLogic.Card(14, 0);
        DeckLogic.TopCard topcard = new DeckLogic.TopCard(card, 11);
        HandLogic.Hand hand = new HandLogic.Hand();
        hand.addCard(topcard);
        DeckLogic.Deck deck = new DeckLogic.Deck(1);

        hand.win();
        assertEquals(1, hand.getScore());
        assertEquals(11, hand.getValue());

        hand.addCard(topcard);
        assertEquals(12, hand.getValue());
        //testPrintHandDialer

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        hand.printHandDialer(2);

        String output = outContent.toString();

        assertTrue(output.contains("    Карты диллера: "));
        assertTrue(output.contains("Туз Пик (11), Туз Пик (11)] => 12"));

        //test-printHandPlayer

        hand.printHandPlayer();

        output = outContent.toString();

        assertTrue(output.contains("    Ваши карты:"));
        assertTrue(output.contains("Туз Пик (11), Туз Пик (11)] => 12"));

        //test-moveDialer
        HandLogic.moveDealer(deck);
        assertTrue(output.contains("Ход дилера:\n-------\nДилер открыл свою вторую карту"));

        System.setOut(System.out);
    }

    @Test
    void testGameLogic() {
        CardLogic.Card card = new CardLogic.Card(14, 0);
        DeckLogic.TopCard topcard = new DeckLogic.TopCard(card, 11);
        HandLogic.Hand hand1 = new HandLogic.Hand();
        HandLogic.Hand hand2 = new HandLogic.Hand();
        hand1.addCard(topcard);
        hand1.addCard(topcard);
        hand2.addCard(topcard);
        hand2.addCard(topcard);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String output = outContent.toString();


        Game.noCard();

        assertTrue(output.contains("Карты в колоде кончились\n"
                + "Подвожу итоги игры"));
        assertTrue(output.contains("    Ваши карты:"));
        assertTrue(output.contains("] => 0"));
        assertTrue(output.contains("    Карты диллера: "));
        assertTrue(output.contains("] => 0"));
        assertTrue(output.contains("Количество очков одинаковое\n Счёт:" + 0 + ":" + 0));

        Game.winer(true);

        assertTrue(output.contains("Вы выиграли раунд! :) " +
                    "Счет " + 1 + ":" + 0));
        assertTrue(output.contains(" в вашу пользу."));
        assertTrue(output.contains("\n"));

        Game.startGame(hand1,hand2);

        assertTrue(output.contains("Раунд " + 1 + "\n" +
                "Дилер раздал карты"));

        System.setOut(System.out);
    }

    @Test
    public void testMain() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;

        String input = "2\n0\n1\n0\n1\n0\n1\n0\n1\n0\n1\n0\n1"
                + "\n0\n1\n0\n1\n0\n1\n0\n1\n0\n1\n0\n0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

    }
}
