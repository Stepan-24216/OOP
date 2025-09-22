package org.example;

import java.util.Scanner;

/**
 * Главный класс приложения для игры в Blackjack.
 */
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static HandLogic.Hand player = new HandLogic.Hand();
    static HandLogic.Hand dealer = new HandLogic.Hand();
    static int countRound = 1;
    static int game = 1;
    static int countDeck;
    static DeckLogic.Deck deck;

    /**
     * Главный метод, запускающий игру.
     */
    public static void main(String[] args) {
        Output.greeting();
        countDeck = scanner.nextInt();
        deck = new DeckLogic.Deck(countDeck);
        while (game != 0) {
            DeckLogic.Deck deck = Game.startGame(player, dealer);
            if (Move.movePlayer(deck, scanner)) {
                if (!Move.moveDealer(deck)) {
                    Game.winer(true);
                } else {
                    if (player.getValue() > dealer.getValue()) {
                        Game.winer(true);
                    } else if (player.getValue() < dealer.getValue()) {
                        Game.winer(false);
                    } else {
                        Output.draw();
                    }
                }
            } else {
                Game.winer(false);
            }
            Output.questionNewGame();
            game = scanner.nextInt();
            if (game == 0) {
                Output.endGame();
            } else {
                player.newGame();
                dealer.newGame();
                countRound++;
            }
        }
    }
}