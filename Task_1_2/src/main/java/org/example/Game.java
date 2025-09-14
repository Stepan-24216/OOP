package org.example;

public class Game {
    public static DeckLogic.Deck startGame(HandLogic.Hand Player,HandLogic.Hand Diler){
        System.out.println("Раунд "+Main.countRound+"\n" +
                "Дилер раздал карты");
        if (!Player.AddCard(Main.deck.TakeCard())){ Game.noCard();}
        if (!Player.AddCard(Main.deck.TakeCard())){ Game.noCard();}
        if (!Diler.AddCard(Main.deck.TakeCard())){ Game.noCard();}
        if (!Diler.AddCard(Main.deck.TakeCard())){ Game.noCard();}
        printStatistics(1);
        return Main.deck;
    }

    public static void winer(boolean win){
        if (win){
            Main.Player.win();
            System.out.print("Вы выиграли раунд! :) " +
                    "Счет "+ Main.Player.getScore()+":"+Main.Diler.getScore());
            if (Main.Player.getScore() > Main.Diler.getScore()){ System.out.print(" в вашу пользу.");}
            else if (Main.Player.getScore() < Main.Diler.getScore()){ System.out.print(" в пользу дилера.");}
            System.out.print("\n");
        }
        else {
            Main.Diler.win();
            System.out.print("Вы проиграли раунд! :( " +
                    "Счет "+ Main.Player.getScore()+":"+Main.Diler.getScore());
            if (Main.Player.getScore() > Main.Diler.getScore()){ System.out.print(" в вашу пользу.");}
            else if (Main.Player.getScore() < Main.Diler.getScore()){ System.out.print(" в пользу дилера.");}
            System.out.print("\n");
        }
    }

    public static void noCard(){
        System.out.println("Карты в колоде кончились\n" +
                "Подвожу итоги игры");
        printStatistics(2);
        if (Main.Player.getValue() > Main.Diler.getValue()){ winer(true);}
        else if (Main.Player.getValue() < Main.Diler.getValue()){ winer(false);}
        else { System.out.println("Количество очков одинаковое\n Счёт:"+Main.Player.getScore()+":"+Main.Diler.getScore());}
        System.exit(0);
    }

    public static void printStatistics(int countMove){
        Main.Player.printHandPlayer();
        Main.Diler.printHandDiler(countMove);
    }
}
