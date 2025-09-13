package org.example;

import java.util.Scanner;

public class Main {
    public static int startGame(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в мою реализацию игры BlackJack\n" +
                "Выберите количество колод: ");
        int countDeck = scanner.nextInt();
        System.out.println("Раунд 1\n" +
                "Дилер раздал карты");
        return countDeck;
    }
    public static void main(String[] args) {
        int countDeck = startGame();
        DeckLogic.Deck deck = new DeckLogic.Deck(countDeck);
        HandLogic.Hand Player = new HandLogic.Hand();
        HandLogic.Hand Diler = new HandLogic.Hand();
        DeckLogic.TopCard cur = deck.TakeCard();
        Player.AddCard(cur);
        cur = deck.TakeCard();
        Player.AddCard(cur);
        Player.printHandPlayer();
        cur = deck.TakeCard();
        Diler.AddCard(cur);
        cur = deck.TakeCard();
        Diler.AddCard(cur);
        Diler.printHandDiler(1);
        Diler.printHandDiler(2);
//                "Карты дилера: [Туз Трефы (11), <закрытая карта ]\n" +
//                "\n" +
//                "Ваш ход\n" +
//                "-------\n" +
//                "Введите “1”, чтобы взять карту, и “0”, чтобы остановиться .\n" +
//                "1\n" +
//                "Вы открыли карту Семерка Пики (7)\n" +
//                "Ваши карты: [Пиковая дама (10), Тройка Червы (3), Семерка Пики\n" +
//                "(7)] > 20\n" +
//                "Карты дилера: [Туз Трефы (11), <закрытая карта ]\n" +
//                "Введите “1”, чтобы взять карту, и “0”, чтобы остановиться .\n" +
//                "0\n" +
//                "Ход дилера");
    }
}

