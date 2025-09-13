package org.example;

import java.util.Random;

public class DeckLogic {
    public static class TopCard {
        private final CardLogic.Card card;
        private final int value;

        public TopCard(CardLogic.Card card, int value) {
            this.card = card;
            this.value = value;
        }
        
        public CardLogic.Card getCard() {
            return card;
        }

        public int getValue() {
            return value;
        }

        public String toString() {
            return card.toString() + " (" + value + ")";
        }
    }
    public static class Deck{
        private final CardLogic.Card[] cards;
        private int topCardIndex;
        private final Random random = new Random();
        public Deck(int countDeck) {
            cards = new CardLogic.Card[52 * countDeck];
            topCardIndex = 0;
            int index = 0;
            for (int iterations = 0;iterations < countDeck;iterations++){
                for (int suit = 0; suit < 4; suit++) {
                    for (int rank = 2; rank <= 14; rank++) {
                        cards[index++] = new CardLogic.Card(rank, suit);
                    }
                }
            }
            shuffle();
        }

        public void shuffle() {
            for (int i = cards.length - 1; i > 0; i--) {
                int index = random.nextInt(i + 1);
                CardLogic.Card temp = cards[index];
                cards[index] = cards[i];
                cards[i] = temp;
            }
            topCardIndex = 0;
        }
        public TopCard TakeCard() {
            if (topCardIndex < cards.length) {
                TopCard current = new TopCard(cards[topCardIndex],cards[topCardIndex].getValue());
                topCardIndex++;
                return current;
            } else {
                return null;
            }
        }
    }
}

