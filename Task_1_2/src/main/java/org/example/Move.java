package org.example;

import java.util.Scanner;

/**
 * Класс для совершения хода.
 */
public class Move {

    /**
     * Ход игрока.
     */
    public static boolean movePlayer(DeckLogic.Deck deck, Scanner scanner) {
        Output.yourMoveMassage();
        int action = 1;
        while (action != 0) {
            Output.choosingAnAction();
            action = scanner.nextInt();
            if (action == 1) {
                TopCardLogic.TopCard cur = deck.takeCard();
                if (!Main.player.addCard(cur)) {
                    Game.noCard();
                }
                Output.playerOpenCardMassage(cur);
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
        Output.dilerMoveMassage();
        Game.printStatistics(2);
        while (Main.dealer.getValue() < 17) {
            TopCardLogic.TopCard cur = deck.takeCard();
            if (!Main.dealer.addCard(cur)) {
                Game.noCard();
            }
            Output.dilerOpenCardMassage(cur);
            Game.printStatistics(2);
            if (Main.dealer.getValue() > 21) {
                return false;
            }
        }
        return true;
    }

}
