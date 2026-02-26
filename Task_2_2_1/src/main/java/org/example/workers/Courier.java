package org.example.workers;

import static org.example.enums.Condition.Completed;

import org.example.building.Pizzeria;
import org.example.building.Warehouse;
import org.example.Order;

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
        while (!Thread.currentThread().isInterrupted()
            && (pizzeria.isOpen() || !warehouse.isEmpty() || pizzeria.orderNotEmpty())) {
            Order order;
            synchronized (warehouse) {
                while (warehouse.isEmpty() && pizzeria.isOpen()) {
                    try {
                        warehouse.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                order = warehouse.takeOrder(capacity);
                if (order != null) {
                    warehouse.notifyAll();
                }
            }
            if (order != null) {
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    System.err.println(
                        "Ошибка доставки заказа, ваш курьер съел пиццу :)" + e.getMessage());
                    Thread.currentThread().interrupt();
                    break;
                }
                order.setCondition(Completed);
            }
        }
        synchronized (warehouse) {
            warehouse.notifyAll();
        }
    }
}
