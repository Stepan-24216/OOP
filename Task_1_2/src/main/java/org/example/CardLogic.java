package org.example;

import java.util.Random;

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

        public void SetValue(int value) {
            this.value = value;
        }

        public void SetRank(int rank) {
            this.rank = rank;
        }

        public void SetSuit(int suit) {
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
            String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
            String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
            return ranks[rank - 2]+ ' ' + suits[suit];
        }
    }
    public static class Deck{
        private Card[] cards;
        private int topCardIndex;
        private Random random = new Random();
        public Deck() {
            cards = new Card[52];
            topCardIndex = 0;
            int index = 0;
            for (int suit = 0; suit < 4; suit++) {
                for (int rank = 2; rank <= 14; rank++) {
                    cards[index++] = new Card(rank, suit);
                }
            }
            shuffle(); // вызываем shuffle без параметров
        }

        // Метод shuffle без параметров - работает с внутренним массивом cards
        public void shuffle() {
            for (int i = cards.length - 1; i > 0; i--) {
                int index = random.nextInt(i + 1);
                // Меняем местами Card объекты, а не int
                Card temp = cards[index];
                cards[index] = cards[i];
                cards[i] = temp;
            }
            topCardIndex = 0; // Сбрасываем индекс после перемешивания
        }
        public Card TakeCard() {
            if (topCardIndex < cards.length) {
                return cards[topCardIndex++];
            } else {
                return null;
            }
        }
    }
}
