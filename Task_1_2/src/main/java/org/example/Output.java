package org.example;

public class Output {
    /**
     * Печатает руку игрока.
     */
    public static void printHandPlayer() {
        System.out.print("    Ваши карты: ");
        System.out.print(Main.player.cards.toString() + " => " + Main.player.handValue);
        System.out.print("\n");
    }

    /**
     * Печатает руку дилера.
     */
    public static void printHandDialer(int countMove) {
        if (countMove == 1) {
            System.out.print("    Карты дилера: ["
                    + Main.dealer.cards.get(0).toString() + " <SecreteCard>]");
        } else {
            System.out.print("    Карты дилера: ");
            System.out.print(Main.dealer.cards.toString() + " => " + Main.dealer.handValue);
        }
        System.out.print("\n");
    }
}

