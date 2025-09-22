package org.example;

/**
 * Логика работы с картами.
 */
public class CardLogic {
    /**
     * Класс представляющий карту.
     */
    public static class Card {
        private final Rank rank;
        private final Suit suit;
        private int value;

        /**
         * Конструктор карты.
         */
        public Card(Rank rank, Suit suit) {
            this.rank = rank;
            this.suit = suit;
            this.value = rank.getCardValue();
        }

        /**
         * Возвращает ранг карты.
         */
        public Rank getRank() {
            return rank;
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
            return rank.getName() + " " + suit.getName();
        }

    }
}