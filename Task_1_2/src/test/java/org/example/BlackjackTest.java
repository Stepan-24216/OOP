package org.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.entities.Card;
import org.example.entities.Deck;
import org.example.entities.Hand;
import org.example.entities.Rank;
import org.example.entities.Suit;
import org.example.game.GameRound;
import org.example.game.Output;

/**
 * Тестовый класс для игры Blackjack.
 */
public class BlackjackTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    /**
     * Тест номиналов карт.
     */
    @Test
    void testRank() {
        Rank rank = Rank.TWO;
        assertEquals("2", rank.getName());
        assertEquals(2, rank.getRankNumber());
        assertEquals(2, rank.getCardValue());

        rank = Rank.JACK;
        assertEquals("Валет", rank.getName());
        assertEquals(11, rank.getRankNumber());
        assertEquals(10, rank.getCardValue());

        rank = Rank.ACE;
        assertEquals("Туз", rank.getName());
        assertEquals(14, rank.getRankNumber());
        assertEquals(11, rank.getCardValue());

        assertEquals(Rank.KING, Rank.createRankUsNum(13));
        assertNull(Rank.createRankUsNum(100));
    }

    /**
     * Тест мастей карт.
     */
    @Test
    void testSuit() {
        Suit suit = Suit.SPADES;
        assertEquals("Пик", suit.getName());
        assertEquals(0, suit.getNumber());

        suit = Suit.HEARTS;
        assertEquals("Червей", suit.getName());
        assertEquals(1, suit.getNumber());

        assertEquals(Suit.DIAMONDS, Suit.createSuitUsNum(2));
        assertNull(Suit.createSuitUsNum(5));
    }

    /**
     * Тест класса Card.
     */
    @Test
    void testCard() {
        Card card = new Card(Rank.ACE, Suit.HEARTS);
        assertEquals(Rank.ACE, card.getRank());
        assertEquals(11, card.getValue());
        assertEquals("Туз Червей (11)", card.toString());

        card.setValue(1);
        assertEquals(1, card.getValue());
        assertEquals("Туз Червей (1)", card.toString());
    }

    /**
     * Тест класса Deck.
     */
    @Test
    void testDeck() {
        Deck deck = new Deck(1); // Одна колода

        Card card = deck.takeCard();
        assertNotNull(card);
        assertEquals(51, deck.getRemainingCards());

        deck.shuffle();
        assertTrue(deck.getRemainingCards() > 0);

        Deck multiDeck = new Deck(2);
        assertEquals(104, multiDeck.getRemainingCards());
    }

    /**
     * Тест класса Hand.
     */
    @Test
    void testHand() {
        Hand hand = new Hand();
        assertEquals(0, hand.getValue());
        assertEquals(0, hand.getScore());
        assertTrue(hand.getCards().isEmpty());

        hand.addCard(new Card(Rank.ACE, Suit.SPADES));
        assertEquals(11, hand.getValue());
        assertEquals(1, hand.getCards().size());

        hand.addCard(new Card(Rank.KING, Suit.HEARTS));
        assertEquals(21, hand.getValue()); // 11 + 10 = 21
        assertEquals(2, hand.getCards().size());


        hand.win();
        assertEquals(1, hand.getScore());
        hand.win();
        assertEquals(2, hand.getScore());


        hand.newGame();
        assertEquals(0, hand.getValue());
        assertTrue(hand.getCards().isEmpty());
        assertEquals(2, hand.getScore());


        hand.addCard(new Card(Rank.ACE, Suit.SPADES));
        hand.addCard(new Card(Rank.KING, Suit.HEARTS));
        assertTrue(hand.toString().contains("Туз Пик (11)"));
        assertTrue(hand.toString().contains("Король Червей (10)"));
        assertTrue(hand.toString().contains("=> 21"));
    }

    /**
     * Тест логики работы с тузами в Hand.
     */
    @Test
    void testHandAceLogic() {
        Hand hand = new Hand();

        hand.addCard(new Card(Rank.ACE, Suit.SPADES));
        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));
        assertEquals(12, hand.getValue());

        hand.addCard(new Card(Rank.NINE, Suit.DIAMONDS));
        assertEquals(21, hand.getValue());

        Hand hand2 = new Hand();
        hand2.addCard(new Card(Rank.ACE, Suit.SPADES));
        hand2.addCard(new Card(Rank.ACE, Suit.HEARTS));
        hand2.addCard(new Card(Rank.ACE, Suit.DIAMONDS));
        hand2.addCard(new Card(Rank.ACE, Suit.CLUBS));
        hand2.addCard(new Card(Rank.SEVEN, Suit.SPADES));

        assertEquals(21, hand2.getValue());
    }

    /**
     * Тест класса Output.
     */
    @Test
    void testOutput() {
        Hand player = new Hand();
        Hand dealer = new Hand();

        player.addCard(new Card(Rank.TEN, Suit.HEARTS));
        player.addCard(new Card(Rank.SEVEN, Suit.SPADES));

        dealer.addCard(new Card(Rank.ACE, Suit.DIAMONDS));
        dealer.addCard(new Card(Rank.KING, Suit.CLUBS));

        Output output = new Output();

        output.printHands(player, dealer, true);
        String outputText = outContent.toString();
        assertTrue(outputText.contains("Ваши карты:"));
        assertTrue(outputText.contains("10 Червей (10)"));
        assertTrue(outputText.contains("7 Пик (7)"));
        assertTrue(outputText.contains("=> 17"));
        assertTrue(outputText.contains("Карты дилера:"));
        assertTrue(outputText.contains("<Скрытая карта>"));

        outContent.reset();

        output.printHands(player, dealer, false);
        outputText = outContent.toString();
        assertTrue(outputText.contains("Туз Бубен (11)"));
        assertTrue(outputText.contains("Король Треф (10)"));
        assertFalse(outputText.contains("<Скрытая карта>"));

        outContent.reset();

        output.winScore(true, 3, 2);
        outputText = outContent.toString();
        assertTrue(outputText.contains("Вы выиграли раунд! :)"));
        assertTrue(outputText.contains("3:2"));
        assertTrue(outputText.contains("в вашу пользу"));

        outContent.reset();

        output.winScore(false, 1, 3);
        outputText = outContent.toString();
        assertTrue(outputText.contains("Вы проиграли раунд! :("));
        assertTrue(outputText.contains("в пользу дилера"));

        outContent.reset();

        output.draw(2, 2);
        assertTrue(outContent.toString().contains("Количество очков одинаковое"));

        outContent.reset();

        output.newRound(5);
        assertTrue(outContent.toString().contains("Раунд 5"));
    }

    /**
     * Тест класса GameRound.
     */
    @Test
    void testGameRound() {
        Hand player = new Hand();
        Hand dealer = new Hand();
        Deck deck = new Deck(1);
        Output output = new Output();

        String input = "1\n0\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        GameRound gameRound = new GameRound(player, dealer, deck, output, scanner, 1);

        gameRound.start();

        assertTrue(player.getValue() > 0);
        assertTrue(dealer.getValue() > 0);
        assertTrue(player.getCards().size() >= 2); // Минимум 2 начальные карты + возможно еще одна
        assertTrue(dealer.getCards().size() >= 2);
    }

    /**
     * Тест определения победителя.
     */
    @Test
    void testDetermineWinnerLogic() {
        final Hand player = new Hand();
        final Hand dealer = new Hand();
        final Deck deck = new Deck(1);
        final Output output = new Output();
        final Scanner scanner = new Scanner(System.in);

        player.addCard(new Card(Rank.KING, Suit.SPADES));
        player.addCard(new Card(Rank.QUEEN, Suit.HEARTS));
        player.addCard(new Card(Rank.THREE, Suit.DIAMONDS)); // 10 + 10 + 3 = 23

        dealer.addCard(new Card(Rank.KING, Suit.CLUBS));
        dealer.addCard(new Card(Rank.SEVEN, Suit.SPADES)); // 10 + 7 = 17

        GameRound gameRound = new GameRound(player, dealer, deck, output, scanner, 1);

        gameRound.determineWinner();

        assertEquals(1, dealer.getScore());

        player.newGame();
        dealer.newGame();

        player.addCard(new Card(Rank.KING, Suit.SPADES));
        player.addCard(new Card(Rank.SEVEN, Suit.HEARTS)); // 10 + 7 = 17

        dealer.addCard(new Card(Rank.KING, Suit.CLUBS));
        dealer.addCard(new Card(Rank.QUEEN, Suit.SPADES));
        dealer.addCard(new Card(Rank.TWO, Suit.DIAMONDS)); // 10 + 10 + 2 = 22

        gameRound.determineWinner();

        assertEquals(1, player.getScore());
    }

    /**
     * Тест главного метода.
     */
    @Test
    public void testMain() {
        String input = "2\n0\n1\n0\n1\n0\n1\n0\n1\n0\n1\n0\n1"
            + "\n0\n1\n0\n1\n0\n1\n0\n1\n0\n1\n0\n0"
            + "\n0\n1\n0\n1\n0\n1\n0\n1\n0\n1\n0\n0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[] {});

    }

}