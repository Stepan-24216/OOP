package org.example;

public class Game {
    public static DeckLogic.Deck startGame(HandLogic.Hand player, HandLogic.Hand dialer) {
        System.out.println("Раунд " + Main.countRound + "\n"
                + "Дилер раздал карты");
        if (!player.addCard(Main.deck.takeCard())) {
            Game.noCard();
        }
        if (!player.addCard(Main.deck.takeCard())) {
            Game.noCard();
        }
        if (!dialer.addCard(Main.deck.takeCard())) {
            Game.noCard();
        }
        if (!dialer.addCard(Main.deck.takeCard())) {
            Game.noCard();
        }
        printStatistics(1);
        return Main.deck;
    }

    public static void winer(boolean win) {
        if (win) {
            Main.player.win();
            System.out.print("Вы выиграли раунд! :) "
                    + "Счет " + Main.player.getScore() + ":" + Main.dealer.getScore());
        } else {
            Main.dealer.win();
            System.out.print("Вы проиграли раунд! :( " +
                    "Счет " + Main.player.getScore() + ":" + Main.dealer.getScore());
        }
        if (Main.player.getScore() > Main.dealer.getScore()) {
            System.out.print(" в вашу пользу.");
        } else if (Main.player.getScore() < Main.dealer.getScore()) {
            System.out.print(" в пользу дилера.");
        }
        System.out.print("\n");
    }

    public static void noCard() {
        System.out.println("Карты в колоде кончились\n"
                + "Подвожу итоги игры");
        printStatistics(2);
        if (Main.player.getValue() > Main.dealer.getValue()) {
            winer(true);
        } else if (Main.player.getValue() < Main.dealer.getValue()) {
            winer(false);
        } else {
            System.out.println("Количество очков одинаковое\n Счёт:"
                    + Main.player.getScore() + ":" + Main.dealer.getScore());
        }
        System.exit(0);
    }

    public static void printStatistics(int countMove) {
        Main.player.printHandPlayer();
        Main.dealer.printHandDialer(countMove);
    }
}
