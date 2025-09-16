package org.example;

public class Game {
    public static DeckLogic.Deck startGame(HandLogic.Hand Player, HandLogic.Hand Diler) {
        System.out.println("Раунд " + Main.countRound + "\n" +
                "Дилер раздал карты");
        if (!Player.addCard(Main.deck.takeCard())) {
            Game.noCard();
        }
        if (!Player.addCard(Main.deck.takeCard())) {
            Game.noCard();
        }
        if (!Diler.addCard(Main.deck.takeCard())) {
            Game.noCard();
        }
        if (!Diler.addCard(Main.deck.takeCard())) {
            Game.noCard();
        }
        printStatistics(1);
        return Main.deck;
    }

    public static void winer(boolean win) {
        if (win) {
            Main.player.win();
            System.out.print("Вы выиграли раунд! :) " +
                    "Счет " + Main.player.getScore() + ":" + Main.diler.getScore());
        } else {
            Main.diler.win();
            System.out.print("Вы проиграли раунд! :( " +
                    "Счет " + Main.player.getScore() + ":" + Main.diler.getScore());
        }
        if (Main.player.getScore() > Main.diler.getScore()) {
            System.out.print(" в вашу пользу.");
        } else if (Main.player.getScore() < Main.diler.getScore()) {
            System.out.print(" в пользу дилера.");
        }
        System.out.print("\n");
    }

    public static void noCard() {
        System.out.println("Карты в колоде кончились\n" +
                "Подвожу итоги игры");
        printStatistics(2);
        if (Main.player.getValue() > Main.diler.getValue()) {
            winer(true);
        } else if (Main.player.getValue() < Main.diler.getValue()) {
            winer(false);
        } else {
            System.out.println("Количество очков одинаковое\n Счёт:" + Main.player.getScore() + ":" + Main.diler.getScore());
        }
        System.exit(0);
    }

    public static void printStatistics(int countMove) {
        Main.player.printHandPlayer();
        Main.diler.printHandDiler(countMove);
    }
}
