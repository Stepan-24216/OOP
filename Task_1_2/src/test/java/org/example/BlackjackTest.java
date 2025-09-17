package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
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
        System.setOut(System.out);
    }

    @Test
    void testGameLogic() {
        CardLogic.Card card = new CardLogic.Card(14, 0);
        DeckLogic.TopCard topcard = new DeckLogic.TopCard(card, 11);
        HandLogic.Hand hand_1 = new HandLogic.Hand();
        HandLogic.Hand hand_2 = new HandLogic.Hand();
        hand_1.addCard(topcard);
        hand_1.addCard(topcard);
        hand_2.addCard(topcard);
        hand_2.addCard(topcard);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String output = outContent.toString();

        Game.noCard();

        assertTrue(output.contains("Карты в колоде кончились\n" +
                                 "Подвожу итоги игры"));
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

        System.setOut(System.out);
    }

    @Test
    public void testMain() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        // Подготавливаем искусственный ввод
        String input = "1\n1\n0\n0\n"; // 1 колода, 1 раунд, выход
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Запускаем main
        Main.main(new String[]{});

        // Проверяем что вывод содержит ожидаемые сообщения
        String output = outContent.toString();
        assertTrue(output.contains("BlackJack"));
        assertTrue(output.contains("Финальный счёт"));
    }
}
