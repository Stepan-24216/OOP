package org.example;

/**
 * Логика игры.
 */
public class Game {
    /**
     * Начинает игру.
     */
    public static DeckLogic.Deck startGame(HandLogic.Hand player, HandLogic.Hand dialer) {
        Output.newRound();
        if (!player.addCard(Main.deck.takeCard())) {
            Game.noCard();
        }
        if (!player.addCard(Main.deck.takeCard())) {
            Game.noCard();
        }
        if (!dialer.addCard(Main.deck.takeCard())) {
            Game.noCard();
        }
        if (!dialer.addCard(Main.deck.takeCard())) {
            Game.noCard();
        }
        printStatistics(1);
        return Main.deck;
    }

    /**
     * Объявляет победителя.
     */
    public static void winer(boolean win) {
        if (win) {
            Main.player.win();
        } else {
            Main.dealer.win();
        }
        Output.winScore(win);
    }

    /**
     * Обрабатывает ситуацию, когда карты закончились.
     */
    public static void noCard() {
        Output.printNoCard();
        printStatistics(2);
        if (Main.player.getValue() > Main.dealer.getValue()) {
            winer(true);
        } else if (Main.player.getValue() < Main.dealer.getValue()) {
            winer(false);
        } else {
            Output.draw();
        }
        System.exit(0);
    }

    /**
     * Печатает статистику игры.
     */
    public static void printStatistics(int countMove) {
        Output.printHandPlayer();
        Output.printHandDialer(countMove);
    }
}