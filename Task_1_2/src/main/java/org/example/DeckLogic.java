package org.example;

import java.util.Random;

/**
 * Логика работы с колодой карт.
 */
public class DeckLogic {

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
                        if (Rank.createRankUsNum(rank) != null &&
                                Suit.createSuitUsNum(suit) != null) {
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
        public TopCardLogic.TopCard takeCard() {
            if (topCardIndex < cards.length) {
                TopCardLogic.TopCard current = new TopCardLogic.TopCard(cards[topCardIndex],
                        cards[topCardIndex].getValue());
                topCardIndex++;
                return current;
            } else {
                return null;
            }
        }
    }
}