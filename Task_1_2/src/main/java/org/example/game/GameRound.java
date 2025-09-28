package org.example.game;

import java.util.Scanner;
import org.example.entities.Card;
import org.example.entities.Deck;
import org.example.entities.Hand;

/**
 * Класс для симуляции одного игрового раунда.
 */
public class GameRound {
    private final Hand player;
    private final Hand dealer;
    private final Deck deck;
    private final Output output;
    private final Scanner scanner;
    private final int roundNumber;

    /**
     * Конструктор игрового раунда.
     */
    public GameRound(Hand player, Hand dealer, Deck deck,
                     Output output, Scanner scanner, int roundNumber) {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
        this.output = output;
        this.scanner = scanner;
        this.roundNumber = roundNumber;
    }

    /**
     * Начинает и выполняет раунд.
     */
    public void start() {
        output.newRound(roundNumber);

        dealInitialCards();

        if (playerTurn()) {
            // Ход дилера, если игрок не проиграл
            dealerTurn();
        }

        // Определение победителя
        determineWinner();
    }

    private void dealInitialCards() {
        // Раздача карт игроку
        player.addCard(deck.takeCard());
        player.addCard(deck.takeCard());

        // Раздача карт дилеру
        dealer.addCard(deck.takeCard());
        dealer.addCard(deck.takeCard());

        output.printHands(player, dealer, true);
    }

    private boolean playerTurn() {
        output.yourMoveMessage();

        while (true) {
            output.choosingAnAction();
            int action = scanner.nextInt();

            if (action == 1) {
                Card card = deck.takeCard();
                if (card == null) {
                    handleNoCards();
                    return false;
                }

                player.addCard(card);
                output.playerOpenCardMessage(card);
                output.printHands(player, dealer, true);

                if (player.getValue() > 21) {
                    return false; // Игрок проиграл
                }
            } else if (action == 0) {
                break; // Игрок остановился
            }
        }
        return true;
    }

    private boolean dealerTurn() {
        output.dealerMoveMessage();
        output.printHands(player, dealer, false);

        while (dealer.getValue() < 17) {
            Card card = deck.takeCard();
            if (card == null) {
                handleNoCards();
                return false;
            }

            dealer.addCard(card);
            output.dealerOpenCardMessage(card);
            output.printHands(player, dealer, false);

            if (dealer.getValue() > 21) {
                return false; // Дилер проиграл
            }
        }
        return true;
    }

    /**
     * Определение победителя.
     */
    public void determineWinner() {
        int playerValue = player.getValue();
        int dealerValue = dealer.getValue();

        if (playerValue > 21) {
            dealer.win();
            output.winScore(false, player.getScore(), dealer.getScore());
        } else if (dealerValue > 21) {
            player.win();
            output.winScore(true, player.getScore(), dealer.getScore());
        } else if (playerValue > dealerValue) {
            player.win();
            output.winScore(true, player.getScore(), dealer.getScore());
        } else if (playerValue < dealerValue) {
            dealer.win();
            output.winScore(false, player.getScore(), dealer.getScore());
        } else {
            output.draw(player.getScore(), dealer.getScore());
        }
    }

    private void handleNoCards() {
        output.printNoCard();
        int playerValue = player.getValue();
        int dealerValue = dealer.getValue();

        if (playerValue > dealerValue) {
            player.win();
            output.winScore(true, player.getScore(), dealer.getScore());
        } else if (playerValue < dealerValue) {
            dealer.win();
            output.winScore(false, player.getScore(), dealer.getScore());
        } else {
            output.draw(player.getScore(), dealer.getScore());
        }
    }
}