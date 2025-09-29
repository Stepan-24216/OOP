package org.example.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс представляющий колоду карт.
 */
public class Deck {
    private final List<Card> cards;
    private final Random random = new Random();

    /**
     * Конструктор колоды.
     */
    public Deck(int countDeck) {
        cards = new ArrayList<>();

        for (int iterations = 0; iterations < countDeck; iterations++) {
            for (int suit = 0; suit < 4; suit++) {
                for (int rank = 2; rank <= 14; rank++) {
                    Rank cardRank = Rank.createRankUsNum(rank);
                    Suit cardSuit = Suit.createSuitUsNum(suit);
                    if (cardRank != null && cardSuit != null) {
                        cards.add(new Card(cardRank, cardSuit));
                    }
                }
            }
        }
        shuffle();
    }

    /**
     * Возвращает количество оставшихся карт.
     */
    public int getRemainingCards() {
        return cards.size();
    }

    /**
     * Перемешивает колоду.
     */
    public void shuffle() {
        for (int i = cards.size() - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            Card temp = cards.get(index);
            cards.set(index, cards.get(i));
            cards.set(i, temp);
        }
    }

    /**
     * Берет карту из колоды.
     */
    public Card takeCard() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        }
        return null;
    }

}