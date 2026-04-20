package org.example;

import java.util.ArrayList;
import java.util.Random;
import org.example.building.Pizzeria;
import org.example.enums.Pizza;

/**
 * Класс симуляции поступления заказов.
 */
public class SimulateCreateOrder implements Runnable {
    private final Pizzeria pizzeria;
    private final int countOrderForGenerate; // общее количество заказов для генерации
    private final int maxCountPizzaInOrder; // максимальное количество пицц в заказе

    /**
     * Конструктор симулятора создания заказов.
     */
    public SimulateCreateOrder(Pizzeria pizzeria, int n, int m) {
        this.pizzeria = pizzeria;
        this.countOrderForGenerate = n;
        this.maxCountPizzaInOrder = m;
    }

    /**
     * Симуляция поступления заказов в пиццерию. Генерирует n заказов, каждый со случайным
     * количеством пицц от 1 до m.
     */
    @Override
    public void run() {
        Random random = new Random();
        Pizza[] menu = Pizza.values();

        for (int i = 0; i < countOrderForGenerate; i++) {
            ArrayList<Pizza> pizzas = new ArrayList<>();
            int count = random.nextInt(maxCountPizzaInOrder) + 1;

            for (int j = 0; j < count; j++) {
                pizzas.add(menu[random.nextInt(menu.length)]);
            }

            Order order = new Order(i, pizzas);
            try {
                pizzeria.addOrder(order);
            } catch (
                IllegalStateException e) {
                System.out.println(
                    "Пиццерия закрыта. Заказ " + order.getOrderNimber() + " не был добавлен.");
                break;
            }

            try {
                Thread.sleep(random.nextInt(50));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
