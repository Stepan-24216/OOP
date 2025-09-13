package org.example;

public class CardLogic {
    public static class Card {
        private int rank; // 2-10, 11-J, 12-Q, 13-K, 14-A
        private int suit; // 0-пики, 1-червы, 2-бубны, 3-трефы
        private int value;

        public Card(int rank, int suit) {
            this.rank = rank;
            this.suit = suit;

            if (2 <= rank && rank <= 10) {
                this.value = rank;
            } else if (11 <= rank && rank <= 13) {
                this.value = 10;
            } else if (rank == 14) {
                this.value = 11;
            }
        }

        public void setValue(int value) {
            this.value = value;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public void setSuit(int suit) {
            this.suit = suit;
        }

        public int getRank() {
            return rank;
        }

        public int getSuit() {
            return suit;
        }

        public int getValue() {
            return value;
        }

        public String toString() {
            String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Валет", "Дама", "Король", "Туз"};
            String[] suits = {"Пик", "Червей", "Бубен", "Треф"};
            return ranks[rank - 2] + ' ' + suits[suit];
        }
    }
}

