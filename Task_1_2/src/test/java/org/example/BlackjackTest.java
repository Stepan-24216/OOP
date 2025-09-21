package org.example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тестовый класс для игры Blackjack.
 */

public class BlackjackTest {

    /**
     * Тест номиналов карт.
     */
    @Test
    void testRank() {
        Rank rank = Rank.TWO;
        assertEquals("2", rank.getName());
        assertEquals(2, rank.getRankNumber());

        rank = Rank.THREE;
        assertEquals("3", rank.getName());
        assertEquals(3, rank.getRankNumber());

        rank = Rank.FOUR;
        assertEquals("4", rank.getName());
        assertEquals(4, rank.getRankNumber());

        rank = Rank.FIVE;
        assertEquals("5", rank.getName());
        assertEquals(5, rank.getRankNumber());

        rank = Rank.SIX;
        assertEquals("6", rank.getName());
        assertEquals(6, rank.getRankNumber());

        rank = Rank.SEVEN;
        assertEquals("7", rank.getName());
        assertEquals(7, rank.getRankNumber());

        rank = Rank.EIGHT;
        assertEquals("8", rank.getName());
        assertEquals(8, rank.getRankNumber());

        rank = Rank.NINE;
        assertEquals("9", rank.getName());
        assertEquals(9, rank.getRankNumber());

        rank = Rank.TEN;
        assertEquals("10", rank.getName());
        assertEquals(10, rank.getRankNumber());

        rank = Rank.JACK;
        assertEquals("Валет", rank.getName());
        assertEquals(11, rank.getRankNumber());

        rank = Rank.QUEEN;
        assertEquals("Дама", rank.getName());
        assertEquals(12, rank.getRankNumber());

        rank = Rank.KING;
        assertEquals("Король", rank.getName());
        assertEquals(13, rank.getRankNumber());

        rank = Rank.ACE;
        assertEquals("Туз", rank.getName());
        assertEquals(14, rank.getRankNumber());
    }

    /**
     * Тест номиналов карт.
     */
    @Test
    void testSuit() {
        Suit suit = Suit.SPADES;
        assertEquals("Пик", suit.getName());
        assertEquals(0, suit.getNumber());

        suit = Suit.HEARTS;
        assertEquals("Червей", suit.getName());
        assertEquals(1, suit.getNumber());

        suit = Suit.DIAMONDS;
        assertEquals("Бубен", suit.getName());
        assertEquals(2, suit.getNumber());

        suit = Suit.CLUBS;
        assertEquals("Треф", suit.getName());
        assertEquals(3, suit.getNumber());
    }

    /**
     * Тест логики карт.
     */
    @Test
    void testCardLogic() {
        CardLogic.Card card = new CardLogic.Card(Rank.createRankUsNum(10), Suit.createSuitUsNum(0));
        assertEquals(10, card.getValue());
        assertEquals(10, card.getRank());
        assertEquals("10 Пик", card.toString());

        card.setValue(777);

        assertEquals(777, card.getValue());
    }

    /**
     * Тест логики колоды.
     */
    @Test
    void tesDeckLogic() {
        CardLogic.Card card = new CardLogic.Card(Rank.createRankUsNum(10), Suit.createSuitUsNum(0));
        DeckLogic.TopCard topcard = new DeckLogic.TopCard(card, 10);
        assertEquals(10, topcard.getValue());
        assertEquals("10 Пик (10)", topcard.toString());

        DeckLogic.Deck deck = new DeckLogic.Deck(1);
        DeckLogic.TopCard cur = deck.takeCard();
        CardLogic.Card topcard2 = cur.getCard();
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8",
                "9", "10", "Валet", "Дама", "Король", "Туз"};
        String[] suits = {"Пик", "Червей", "Бубен", "Треф"};
    }

    /**
     * Тест логики руки.
     */
    @Test
    void testHandLogic() {
        CardLogic.Card card = new CardLogic.Card(Rank.createRankUsNum(14), Suit.createSuitUsNum(0));
        DeckLogic.TopCard topcard = new DeckLogic.TopCard(card, 11);
        HandLogic.Hand hand = new HandLogic.Hand();
        hand.addCard(topcard);
        DeckLogic.Deck deck = new DeckLogic.Deck(1);

        hand.win();
        assertEquals(1, hand.getScore());
        assertEquals(11, hand.getValue());

        hand.addCard(topcard);
        assertEquals(12, hand.getValue());

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        hand.printHandDialer(2);
        hand.printHandPlayer();
        HandLogic.moveDealer(deck);

        String output = outContent.toString();

        assertTrue(output.contains("    Карты дилера: "));
        assertTrue(output.contains("Туз Пик (11), Туз Пик (11)] => 12"));
        assertTrue(output.contains("    Ваши карты:"));
        assertTrue(output.contains("Туз Пик (11), Туз Пик (11)] => 12"));
        assertTrue(output.contains("Ход дилера:\n-------\nДилер открыл свою вторую карту"));

        System.setOut(System.out);
    }

    /**
     * Тест логики игры.
     */
    @Test
    void testGameLogic() {
        CardLogic.Card card = new CardLogic.Card(Rank.createRankUsNum(14), Suit.createSuitUsNum(0));
        DeckLogic.TopCard topcard = new DeckLogic.TopCard(card, 11);
        HandLogic.Hand hand1 = new HandLogic.Hand();
        HandLogic.Hand hand2 = new HandLogic.Hand();
        hand1.addCard(topcard);
        hand1.addCard(topcard);
        hand2.addCard(topcard);
        hand2.addCard(topcard);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Game.noCard();
        Game.winer(true);
        Game.startGame(hand1, hand2);
        String output = outContent.toString();

        assertTrue(output.contains("Карты в колоде кончились\n"
                + "Подвожу итоги игры"));
        assertTrue(output.contains("    Ваши карты:"));
        assertTrue(output.contains("] => 0"));
        assertTrue(output.contains("    Карты дилера: "));
        assertTrue(output.contains("] => 0"));
        assertTrue(output.contains("Количество очков одинаковое\n Счёт:" + 0 + ":" + 0));
        assertTrue(output.contains("Вы выиграли раунд! :) "
                + "Счет " + 1 + ":" + 0));
        assertTrue(output.contains(" в вашу пользу."));
        assertTrue(output.contains("\n"));
        assertTrue(output.contains("Раунд " + 1 + "\n"
                + "Дилер раздал карты"));

        System.setOut(System.out);
    }

    /**
     * Тест главного метода.
     */
    @Test
    public void testMain() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;

        String input = "2\n0\n1\n0\n1\n0\n1\n0\n1\n0\n1\n0\n1"
                + "\n0\n1\n0\n1\n0\n1\n0\n1\n0\n1\n0\n0"
                + "\n0\n1\n0\n1\n0\n1\n0\n1\n0\n1\n0\n0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

    }
}