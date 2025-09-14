package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HandLogic {
    public static class Hand {
        private List<DeckLogic.TopCard> cards;
        private int handValue;
        private int handCount;
        private boolean hasAce;
        private int handScore;

        public Hand() {
            this.cards = new ArrayList<>();
            this.handValue = 0;
            this.handCount = 0;
            this.handScore = 0;
            this.hasAce = false;
        }

        public boolean AddCard(DeckLogic.TopCard card) {
            if (card != null){
                cards.add(card);
                handCount++;
                calculateValue();
                return true;
            }
            return false;
        }

        public void newGame(){
            this.cards = new ArrayList<>();
            this.handValue = 0;
            this.handCount = 0;
            this.hasAce = false;
        }

        public int getValue(){ return handValue; }
        public int getScore(){ return handScore; }
        public void win() { handScore++;}

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

    public static boolean movePlayer(DeckLogic.Deck deck, Scanner scanner) {
        System.out.println("Ваш ход:\n-------");
        int action = 1;
        while (action != 0){
            System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...");
            action = scanner.nextInt();
            if (action == 1){
                DeckLogic.TopCard cur = deck.TakeCard();
                if (!Main.Player.AddCard(cur)){ Game.noCard();}
                System.out.println("Вы открыли карту "+ cur.toString());
                Game.printStatistics(1);
                if (Main.Player.getValue() > 21) { return false; }
            }
        }
        return true;
    }

    public static boolean moveDiler(DeckLogic.Deck deck) {
        System.out.println("Ход дилера:\n-------\nДилер открыл свою вторую карту");
        Game.printStatistics(2);
        while (Main.Diler.getValue() < 17){
            DeckLogic.TopCard cur = deck.TakeCard();
            if (!Main.Diler.AddCard(cur)){ Game.noCard();}
            System.out.println("Дилер открыл карту "+ cur.toString());
            Game.printStatistics(2);
            if (Main.Diler.getValue() > 21) { return false; }
        }
        return true;
    }
}
