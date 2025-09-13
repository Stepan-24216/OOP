package org.example;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.List;

public class HandLogic {
    public static class Hand {
        private List<DeckLogic.TopCard> cards;
        private int handValue;
        private int handCount;
        private boolean hasAce;

        public Hand() {
            this.cards = new ArrayList<>();
            this.handValue = 0;
            this.handCount = 0;
            this.hasAce = false;
        }

        public int getHandValue(){ return handValue;
        }

        public void AddCard(DeckLogic.TopCard card) {
            if (card != null){
                cards.add(card);
                handCount++;
                calculateValue();
            }
        }
        private void calculateValue(){
            handValue = 0;
            hasAce = false;
            int acecnt = 0;
            for (DeckLogic.TopCard topCard: cards){
                handValue += topCard.getValue();
                if (topCard.getCard().getRank() == 14 && topCard.getCard().getValue() == 11){
                    hasAce = true;
                    acecnt++;
                }
                if (handValue > 21 && hasAce){
                    for (DeckLogic.TopCard aceCard: cards){
                        if (aceCard.getCard().getRank() == 14 && aceCard.getCard().getValue() == 11){
                            aceCard.getCard().setValue(1);
                        }
                    }
                    handValue -= 10;
                    acecnt--;
                    if (acecnt == 0){
                        hasAce = false;
                    }
                }
            }
        }

        public void printHandPlayer(){
            System.out.print("    Ваши карты: ");
            System.out.print(cards.toString() + " => " + handValue);
            System.out.print("\n");
        }

        public void printHandDiler(int countMove){
            if (countMove == 1){
                System.out.print("    Карты диллера: [");
                System.out.print(cards.get(0).toString() + " <SecreteCard>]");
            }
            else {
                System.out.print("    Карты диллера: ");
                System.out.print(cards.toString() + " => " + handValue);
            }
            System.out.print("\n");
        }
    }
}
