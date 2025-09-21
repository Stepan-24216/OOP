package org.example;

/**
 * Логика работы с картами.
 */
public class CardLogic {
    /**
     * Класс представляющий карту.
     */
    public static class Card {
        private Rank rank;
        private Suit suit;
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
         * Устанавливает значение карты.
         */
        public void setValue(int value) {
            this.value = value;
        }

        /**
         * Устанавливает ранг карты.
         */
        public void setRank(Rank rank) {
            this.rank = rank;
            this.value = rank.getCardValue(); // Обновляем значение при изменении ранга
        }

        /**
         * Устанавливает масть карты.
         */
        public void setSuit(Suit suit) {
            this.suit = suit;
        }

        /**
         * Возвращает ранг карты.
         */
        public Rank getRank() {
            return rank;
        }

        /**
         * Возвращает масть карты.
         */
        public Suit getSuit() {
            return suit;
        }

        /**
         * Возвращает значение карты.
         */
        public int getValue() {
            return value;
        }

        /**
         * Возвращает числовое значение ранга.
         */
        public int getRankValue() {
            return rank.getRankNumber();
        }

        /**
         * Возвращает строковое представление карты.
         */
        public String toString() {
            return rank.getName() + " " + suit.getName();
        }

        /**
         * Возвращает короткое строковое представление карты.
         */
        public String toShortString() {
            return rank.toString() + " " + suit.toString();
        }
    }
}