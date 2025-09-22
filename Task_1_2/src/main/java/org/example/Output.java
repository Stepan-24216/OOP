package org.example;

public class Output {

    public static void greeting(){
        System.out.println("Добро пожаловать в мою реализацию игры BlackJack");
        System.out.println("Выберите количество колод: ");
    }

    public static void questionNewGame(){
        System.out.println(
                "Хотите сыграть ещё?(Введите 1 для "
                        + "следующего раунда, 0 чтобы закончить игру)"
        );
    }

    public static void draw(){
        System.out.println("Количество очков одинаковое\n Счёт:"
                + Main.player.getScore() + ":" + Main.dealer.getScore());
    }
    public static void winScore(boolean win){
        if (win){
            System.out.print("Вы выиграли раунд! :) "
                    + "Счет " + Main.player.getScore() + ":" + Main.dealer.getScore());
        } else {
            System.out.print("Вы проиграли раунд! :( "
                    + "Счет " + Main.player.getScore() + ":" + Main.dealer.getScore());
        }
        if (Main.player.getScore() > Main.dealer.getScore()) {
            System.out.print(" в вашу пользу.");
        } else if (Main.player.getScore() < Main.dealer.getScore()) {
            System.out.print(" в пользу дилера.");
        }
        System.out.print("\n");
    }

    public static void newRound(){
        System.out.println("Раунд " + Main.countRound + "\n"
                + "Дилер раздал карты");
    }
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

    public static void endGame(){
        System.out.println("Финальный счёт: "
                + Main.player.getScore() + ":" + Main.dealer.getScore());
        System.out.println("Спасибо за игру заходи ещё");
    }
}

