package org.example.Workers;

import static org.example.Condition.Completed;

import org.example.Order;
import org.example.Pizzeria;
import org.example.Warehouse;

/**
 * Класс моего курьера.
 */
public class Courier implements Runnable {
    private final int id;
    private final int speed;
    private final int capacity;
    private final Warehouse warehouse;
    private final Pizzeria pizzeria;

    /**
     * Конструктор курьера.
     */
    public Courier(int id, int speed, int capacity, Warehouse warehouse, Pizzeria pizzeria) {
        this.id = id;
        this.speed = speed;
        this.capacity = capacity;
        this.warehouse = warehouse;
        this.pizzeria = pizzeria;
    }

    /**
     * Рабочий день курьера.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() &&
            (pizzeria.isOpen() || !warehouse.isEmpty() || !pizzeria.orderIsEmpty())) {
            Order order = warehouse.takeOrder(capacity);
            if (order != null) {
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                order.setCondition(Completed);
            }
        }
    }
}
