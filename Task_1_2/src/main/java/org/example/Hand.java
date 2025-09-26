package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс представляющий руку игрока.
 */
public class Hand {
    private List<Card> cards;
    private int handValue;
    private int handScore;
    private boolean hasAce;

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
    public boolean addCard(Card card) {
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
     * Возвращает список карт в руке.
     */
    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    /**
     * Вычисляет значение руки.
     */
    private void calculateValue() {
        handValue = 0;
        hasAce = false;
        int aceCount = 0;

        for (Card card : cards) {
            if (card.getRank() == Rank.ACE) {
                card.setValue(11);
            }
        }

        for (Card card : cards) {
            handValue += card.getValue();
            if (card.getRank() == Rank.ACE && card.getValue() == 11) {
                hasAce = true;
                aceCount++;
            }
        }

        for (Card card : cards) {
            if (handValue <= 21) break;

            if (card.getRank() == Rank.ACE && card.getValue() == 11) {
                card.setValue(1);
                handValue -= 10;
                aceCount--;
            }
        }

        hasAce = (aceCount > 0);
    }

    /**
     * Возвращает строковое представление руки.
     */
    public String toString() {
        return cards.toString() + " => " + handValue;
    }
}