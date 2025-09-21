package org.example;

public enum Suit {
    SPADES("Пик", 0),
    HEARTS("Червей", 1),
    DIAMONDS("Бубен", 2),
    CLUBS("Треф", 3);

    private final String name;
    private final int number;

    Suit(String Name,int number) {
        this.name = Name;
        this.number = number;
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

    public static Suit createSuitUsNum(int number){
        for (Suit suit: values()){
            if (suit.number == number){
                return suit;
            }
        }
        return null;
    }
}