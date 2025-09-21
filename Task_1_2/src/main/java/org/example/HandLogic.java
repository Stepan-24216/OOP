package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Логика работы с рукой игрока.
 */
public class HandLogic {
    /**
     * Класс представляющий руку игрока.
     */
    public static class Hand {
        private List<DeckLogic.TopCard> cards;
        private int handValue;
        private boolean hasAce;
        private int handScore;

        /**
         * Конструктор руки.
         */
        public Hand() {
            this.cards = new ArrayList<>();
            this.handValue = 0;
            this.handScore = 0;
            this.hasAce = false;
        }

        /**
         * Добавляет карту в руку.
         */
        public boolean addCard(DeckLogic.TopCard card) {
            if (card != null) {
                cards.add(card);
                calculateValue();
                return true;
            }
            return false;
        }

        /**
         * Начинает новую игру.
         */
        public void newGame() {
            this.cards = new ArrayList<>();
            this.handValue = 0;
            this.hasAce = false;
        }

        /**
         * Возвращает значение руки.
         */
        public int getValue() {
            return handValue;
        }

        /**
         * Возвращает счет.
         */
        public int getScore() {
            return handScore;
        }

        /**
         * Увеличивает счет.
         */
        public void win() {
            handScore++;
        }

        /**
         * Вычисляет значение руки.
         */
        private void calculateValue() {
            handValue = 0;
            hasAce = false;
            int accent = 0;
            for (DeckLogic.TopCard topCard : cards) {
                handValue += topCard.getValue();
                if (topCard.getCard().getRank().getRankNumber() == 14
                        && topCard.getCard().getValue() == 11) {
                    hasAce = true;
                    accent++;
                }
                if (handValue > 21 && hasAce) {
                    for (DeckLogic.TopCard aceCard : cards) {
                        if (aceCard.getCard().getRank().getRankNumber() == 14
                                && aceCard.getCard().getValue() == 11) {
                            aceCard.getCard().setValue(1);
                        }
                    }
                    handValue -= 10;
                    accent--;
                    if (accent == 0) {
                        hasAce = false;
                    }
                }
            }
        }

        /**
         * Печатает руку игрока.
         */
        public void printHandPlayer() {
            System.out.print("    Ваши карты: ");
            System.out.print(cards.toString() + " => " + handValue);
            System.out.print("\n");
        }

        /**
         * Печатает руку дилера.
         */
        public void printHandDialer(int countMove) {
            if (countMove == 1) {
                System.out.print("    Карты дилера: ["
                        + cards.get(0).toString() + " <SecreteCard>]");
            } else {
                System.out.print("    Карты дилера: ");
                System.out.print(cards.toString() + " => " + handValue);
            }
            System.out.print("\n");
        }
    }

    /**
     * Ход игрока.
     */
    public static boolean movePlayer(DeckLogic.Deck deck, Scanner scanner) {
        System.out.println("Ваш ход:\n-------");
        int action = 1;
        while (action != 0) {
            System.out.println("Введите \"1\", чтобы взять карту, и \"0\", чтобы остановиться...");
            action = scanner.nextInt();
            if (action == 1) {
                DeckLogic.TopCard cur = deck.takeCard();
                if (!Main.player.addCard(cur)) {
                    Game.noCard();
                }
                System.out.println("Вы открыли карту " + cur.toString());
                Game.printStatistics(1);
                if (Main.player.getValue() > 21) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Ход дилера.
     */
    public static boolean moveDealer(DeckLogic.Deck deck) {
        System.out.println("Ход дилера:\n-------\nДилер открыл свою вторую карту");
        Game.printStatistics(2);
        while (Main.dealer.getValue() < 17) {
            DeckLogic.TopCard cur = deck.takeCard();
            if (!Main.dealer.addCard(cur)) {
                Game.noCard();
            }
            System.out.println("Дилер открыл карту " + cur.toString());
            Game.printStatistics(2);
            if (Main.dealer.getValue() > 21) {
                return false;
            }
        }
        return true;
    }
}