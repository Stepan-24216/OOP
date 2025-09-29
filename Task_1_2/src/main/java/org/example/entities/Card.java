package org.example.entities;

/**
 * Класс представляющий карту.
 */
public class Card {
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
     * Устанавливает значение карты (для туза).
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Возвращает строковое представление карты.
     */

    public String toString() {
        return rank.getName() + " " + suit.getName() + " (" + value + ")";
    }
}