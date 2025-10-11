package org.example.entities;


/**
 * Класс enum для масти карт и методы работы с ним.
 */
public enum Suit {
    SPADES("Пик", 0),
    HEARTS("Червей", 1),
    DIAMONDS("Бубен", 2),
    CLUBS("Треф", 3);

    private final String name;
    private final int number;

    Suit(String name, int number) {
        this.name = name;
        this.number = number;
    }

    /**
     * Распознаёт масть по номеру.
     */
    public static Suit createSuitUsNum(int number) {
        for (Suit suit : values()) {
            if (suit.number == number) {
                return suit;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public int getNumber() {
        return number;
    }
}