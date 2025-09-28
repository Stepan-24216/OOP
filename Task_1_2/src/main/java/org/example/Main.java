package org.example;

import java.util.Scanner;
import org.example.entities.Deck;
import org.example.entities.Hand;
import org.example.game.GameRound;
import org.example.game.Output;
/**
 * Главный класс приложения для игры в Blackjack.
 */
public class Main {

    /**
     * Главный метод, запускающий игру.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Output output = new Output();

        output.greeting();
        int countDeck = scanner.nextInt();

        Hand player = new Hand();
        Hand dealer = new Hand();
        Deck deck = new Deck(countDeck);

        int game = 1;
        int countRound = 1;

        while (game != 0) {
            GameRound gameRound = new GameRound(player, dealer, deck, output, scanner, countRound);
            gameRound.start();

            output.questionNewGame();
            game = scanner.nextInt();

            if (game == 0) {
                output.endGame(player.getScore(), dealer.getScore());
            } else {
                player.newGame();
                dealer.newGame();
                countRound++;
            }
        }
        scanner.close();
    }
}