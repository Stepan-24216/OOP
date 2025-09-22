package org.example;

import java.util.Scanner;
/**
 * Класс для совершения хода.
 */
public class Move {
    public static boolean movePlayer(DeckLogic.Deck deck, Scanner scanner) {
        System.out.println("Ваш ход:\n-------");
        int action = 1;
        while (action != 0) {
            System.out.println("Введите \"1\", чтобы взять карту, и \"0\", чтобы остановиться...");
            action = scanner.nextInt();
            if (action == 1) {
                TopCardLogic.TopCard cur = deck.takeCard();
                if (!Main.player.addCard(cur)) {
                    Game.noCard();
                }
                System.out.println("Вы открыли карту " + cur.toString());
                Game.printStatistics(1);
                if (Main.player.getValue() > 21) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Ход дилера.
     */
    public static boolean moveDealer(DeckLogic.Deck deck) {
        System.out.println("Ход дилера:\n-------\nДилер открыл свою вторую карту");
        Game.printStatistics(2);
        while (Main.dealer.getValue() < 17) {
            TopCardLogic.TopCard cur = deck.takeCard();
            if (!Main.dealer.addCard(cur)) {
                Game.noCard();
            }
            System.out.println("Дилер открыл карту " + cur.toString());
            Game.printStatistics(2);
            if (Main.dealer.getValue() > 21) {
                return false;
            }
        }
        return true;
    }

}
