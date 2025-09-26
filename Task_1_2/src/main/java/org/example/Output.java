package org.example;

/**
 * Класс для вывода информации пользователю.
 */
public class Output {

    /**
     * Приветствие.
     */
    public void greeting() {
        System.out.println("Добро пожаловать в мою реализацию игры BlackJack");
        System.out.println("Выберите количество колод: ");
    }

    /**
     * Вопрос в конце раунда.
     */
    public void questionNewGame() {
        System.out.println("Хотите сыграть ещё? (Введите 1 для следующего раунда, 0 чтобы закончить игру)");
    }

    /**
     * Выбор следующего действия для хода.
     */
    public void choosingAnAction() {
        System.out.println("Введите \"1\", чтобы взять карту, и \"0\", чтобы остановиться...");
    }

    public void yourMoveMessage() {
        System.out.println("Ваш ход:\n-------");
    }

    public void dealerMoveMessage() {
        System.out.println("Ход дилера:\n-------\nДилер открыл свою вторую карту");
    }

    public void playerOpenCardMessage(Card card) {
        System.out.println("Вы открыли карту " + card.toString());
    }

    public void dealerOpenCardMessage(Card card) {
        System.out.println("Дилер открыл карту " + card.toString());
    }

    public void draw(int playerScore, int dealerScore) {
        System.out.println("Количество очков одинаковое\nСчёт: " + playerScore + ":" + dealerScore);
    }

    /**
     * Печатает количество очков и статус победы или поражения.
     */
    public void winScore(boolean win, int playerScore, int dealerScore) {
        if (win) {
            System.out.print("Вы выиграли раунд! :) Счет " + playerScore + ":" + dealerScore);
        } else {
            System.out.print("Вы проиграли раунд! :( Счет " + playerScore + ":" + dealerScore);
        }

        if (playerScore > dealerScore) {
            System.out.print(" в вашу пользу.");
        } else if (playerScore < dealerScore) {
            System.out.print(" в пользу дилера.");
        }
        System.out.println();
    }

    public void newRound(int roundNumber) {
        System.out.println("Раунд " + roundNumber + "\nДилер раздал карты");
    }

    /**
     * Печатает руки игрока и дилера.
     */
    public void printHands(Hand player, Hand dealer, boolean hideDealerCard) {
        System.out.print("    Ваши карты: " + player.toString());
        System.out.println();

        System.out.print("    Карты дилера: ");
        if (hideDealerCard) {
            // Показываем только первую карту дилера
            var dealerCards = dealer.getCards();
            if (!dealerCards.isEmpty()) {
                System.out.print("[" + dealerCards.get(0).toString() + " <Скрытая карта>]");
            }
        } else {
            System.out.print(dealer.toString());
        }
        System.out.println();
    }

    public void printNoCard() {
        System.out.println("Карты в колоде кончились\nПодвожу итоги игры");
    }

    /**
     * Вывод итогов и конца игры.
     */
    public void endGame(int playerScore, int dealerScore) {
        System.out.println("Финальный счёт: " + playerScore + ":" + dealerScore);
        System.out.println("Спасибо за игру! Заходи ещё!");
    }
}