package org.example;

/**
 * Класс enum для номинала
 * карт и методы работы с ним.
 */
public enum Rank {
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(11, "Валет"),
    QUEEN(12, "Дама"),
    KING(13, "Король"),
    ACE(14, "Туз");


    private final int rankNumber;
    private final String name;

    /**
     * Конструктор.
     */
    Rank(int numValue, String name) {
        this.rankNumber = numValue;
        this.name = name;
    }

    /**
     * Методы получения информации о enum.
     */
    public int getRankNumber() {
        return rankNumber;
    }

    public String getName() {
        return name;
    }

    /**
     * Получить игровое значение карты.
     */
    public int getCardValue() {
        if (rankNumber >= 2 && rankNumber <= 10) {
            return rankNumber;
        } else if (rankNumber >= 11
                && rankNumber <= 13) {
            return 10;
        } else { // ACE
            return 11;
        }
    }

    public String toString() {
        return name;
    }

    /**
     * Распознаёт номинал карты по номеру.
     */
    public static Rank createRankUsNum(int number) {
        for (Rank rank: values()) {
            if (rank.rankNumber == number) {
                return rank;
            }
        }
        return null;
    }
}