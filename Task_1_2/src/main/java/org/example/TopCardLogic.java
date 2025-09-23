package org.example;

/**
 * Класс для использования верхней карты в колоде.
 */
public class TopCardLogic {

    /**
     * Класс представляющий верхнюю карту.
     */
    public static class TopCard {
        private final CardLogic.Card card;
        private int value;

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

        public void setValue(int val){
            value = val;
        }

        /**
         * Возвращает строковое представление карты.
         */
        public String toString() {
            return card.toString() + " (" + value + ")";
        }
    }
}
