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
        protected List<TopCardLogic.TopCard> cards;
        protected int handValue;
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
        public boolean addCard(TopCardLogic.TopCard card) {
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
            for (TopCardLogic.TopCard topCard : cards) {
                handValue += topCard.getValue();
                if (topCard.getCard().getRank().getRankNumber() == 14
                        && topCard.getCard().getValue() == 11) {
                    hasAce = true;
                    accent++;
                }
                if (handValue > 21 && hasAce) {
                    for (TopCardLogic.TopCard aceCard : cards) {
                        if (aceCard.getCard().getRank().getRankNumber() == 14
                                && aceCard.getCard().getValue() == 11) {
                            aceCard.setValue(1);
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
    }

}