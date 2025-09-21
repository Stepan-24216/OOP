package org.example;

import java.util.Random;

/**
 * Логика работы с колодой карт.
 */
public class DeckLogic {
    /**
     * Класс представляющий верхнюю карту.
     */
    public static class TopCard {
        private final CardLogic.Card card;
        private final int value;

        /**
         * Конструктор верхней карты.
         */
        public TopCard(CardLogic.Card card, int value) {
            this.card = card;
            this.value = value;
        }

        /**
         * Возвращает карту.
         */
        public CardLogic.Card getCard() {
            return card;
        }

        /**
         * Возвращает значение карты.
         */
        public int getValue() {
            return value;
        }

        /**
         * Возвращает строковое представление карты.
         */
        public String toString() {
            return card.toString() + " (" + value + ")";
        }
    }

    /**
     * Класс представляющий колоду карт.
     */
    public static class Deck {
        private final CardLogic.Card[] cards;
        private int topCardIndex;
        private final Random random = new Random();

        /**
         * Конструктор колоды.
         */
        public Deck(int countDeck) {
            cards = new CardLogic.Card[52 * countDeck];
            topCardIndex = 0;
            int index = 0;
            for (int iterations = 0; iterations < countDeck; iterations++) {
                for (int suit = 0; suit < 4; suit++) {
                    for (int rank = 2; rank <= 14; rank++) {
                        if (Rank.createRankUsNum(rank) != null && Suit.createSuitUsNum(suit) != null) {
                            cards[index++] = new CardLogic.Card(Rank.createRankUsNum(rank),
                                    Suit.createSuitUsNum(suit));
                        }
                    }
                }
            }
            shuffle();
        }

        /**
         * Перемешивает колоду.
         */
        public void shuffle() {
            for (int i = cards.length - 1; i > 0; i--) {
                int index = random.nextInt(i + 1);
                CardLogic.Card temp = cards[index];
                cards[index] = cards[i];
                cards[i] = temp;
            }
            topCardIndex = 0;
        }

        /**
         * Берет карту из колоды.
         */
        public TopCard takeCard() {
            if (topCardIndex < cards.length) {
                TopCard current = new TopCard(cards[topCardIndex],
                        cards[topCardIndex].getValue());
                topCardIndex++;
                return current;
            } else {
                return null;
            }
        }
    }
}