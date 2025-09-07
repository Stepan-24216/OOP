package org.example;


public class Main {
    public static void main(String[] args) {
        CardLogic.Deck deck = new CardLogic.Deck();
        int playerValue = 0;
        int dilerValue = 0;
        System.out.println("Первые 5 карт после перемешивания:");
        for (int i = 0; i < 5; i++) {
            System.out.println(deck.TakeCard());
        }
    }
}