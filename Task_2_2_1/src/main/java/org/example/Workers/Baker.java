package org.example.Workers;

import org.example.Condition;
import org.example.Order;
import org.example.Pizzeria;
import org.example.Warehouse;

/**
 * Класс моих пекарей.
 */
public class Baker implements Runnable {
    private final int id;
    private final int cookingSpeed;
    private final Warehouse warehouse;
    private final Pizzeria pizzeria;

    public Baker(int id, int cookingSpeed, Warehouse warehouse, Pizzeria pizzeria) {
        this.id = id;
        this.cookingSpeed = cookingSpeed;
        this.warehouse = warehouse;
        this.pizzeria = pizzeria;
    }

    /**
     * Рабочий день пекаря.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() &&
            (pizzeria.isOpen() || !pizzeria.orderIsEmpty())) {
            Order order = pizzeria.takeOrder();
            if (order != null) {
                order.setCondition(Condition.Cooking);
                try {
                    Thread.sleep(cookingSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (warehouse.getFreeSpace() < order.getCountPizzas()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                warehouse.addOrder(order);
            }
        }
    }
}
