package org.example;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static HandLogic.Hand Player = new HandLogic.Hand();
    static HandLogic.Hand Diler = new HandLogic.Hand();
    static int countRound = 1;
    static int game = 1;
    static int countDeck;
    static DeckLogic.Deck deck;

    public static void main() {
        System.out.println("Добро пожаловать в мою реализацию игры BlackJack");
        System.out.println("Выберите количество колод: ");
        countDeck = scanner.nextInt();
        deck = new DeckLogic.Deck(countDeck);
        while (game != 0){
            DeckLogic.Deck deck = Game.startGame(Player,Diler);
            if (HandLogic.movePlayer(deck,scanner)){
                if (!HandLogic.moveDiler(deck)){
                    Game.winer(true);
                }
                else {
                    if (Player.getValue() > Diler.getValue()){ Game.winer(true);}
                    else if (Player.getValue() < Diler.getValue()){ Game.winer(false);}
                    else { System.out.println("Количество очков одинаковое\n Счёт:"+Player.getScore()+":"+Diler.getScore());}
                }
            }
            else{
                Game.winer(false);
            }
            System.out.println("Хотите сыграть ещё?(Введите 1 для следующего раунда, 0 чтобы закончить игру)");
            game = scanner.nextInt();
            if (game == 0){
                System.out.println("Финальный счёт: "+Player.getScore()+":"+Diler.getScore());
                System.out.println("Спасибо за игру заходи ещё");
            }
            else {
                Player.newGame();
                Diler.newGame();
                countRound++;
            }
        }
    }
}

