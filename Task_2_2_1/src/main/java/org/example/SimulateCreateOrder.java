package org.example;

import java.util.ArrayList;
import java.util.Random;

/**
 * Класс симуляции поступления заказов.
 */
public class SimulateCreateOrder implements Runnable {
    private final Pizzeria pizzeria;
    private final int n; // общее количество заказов для генерации
    private final int m; // макс. количество пицц в заказе

    public SimulateCreateOrder(Pizzeria pizzeria, int n, int m) {
        this.pizzeria = pizzeria;
        this.n = n;
        this.m = m;
    }

    /**
     * Симуляция поступления заказов в пиццерию. Генерирует n заказов, каждый с случайным
     * количеством пицц от 1 до m.
     */
    @Override
    public void run() {
        Random random = new Random();
        Pizza[] menu = Pizza.values();

        for (int i = 0; i < n; i++) {
            ArrayList<Pizza> pizzas = new ArrayList<>();
            int count = random.nextInt(m) + 1;

            for (int j = 0; j < count; j++) {
                pizzas.add(menu[random.nextInt(menu.length)]);
            }

            Order order = new Order(i, pizzas);
            pizzeria.addOrder(order);

            try {
                Thread.sleep(random.nextInt(50));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
