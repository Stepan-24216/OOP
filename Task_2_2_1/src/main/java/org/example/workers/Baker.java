package org.example.workers;

import org.example.Order;
import org.example.building.Pizzeria;
import org.example.building.Warehouse;
import org.example.enums.Condition;

/**
 * Класс моих пекарей.
 */
public class Baker implements Runnable {
    private final int id;
    private final int cookingSpeed;
    private final Warehouse warehouse;
    private final Pizzeria pizzeria;

    /**
     * Конструктор пекаря.
     */
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
        while (!Thread.currentThread().isInterrupted()
            && (pizzeria.isOpen() || pizzeria.orderNotEmpty())) {
            Order order = pizzeria.takeOrder();
            if (order != null) {
                order.setCondition(Condition.Cooking);
                try {
                    Thread.sleep(cookingSpeed);
                } catch (InterruptedException e) {
                    System.err.println(
                        "Ошибка доставки заказа, пекарь сжёг пиццу :(" + e.getMessage());
                    Thread.currentThread().interrupt();
                    break;
                }
                synchronized (warehouse) {
                    while (warehouse.getFreeSpace() < order.getCountPizzas()) {
                        try {
                            warehouse.wait();
                        } catch (InterruptedException e) {
                            System.err.println(
                                "Пекарь прерван при ожидании склада: " + e.getMessage());
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    warehouse.addOrder(order);
                    warehouse.notifyAll();
                }
            }
        }
        synchronized (warehouse) {
            warehouse.notifyAll();
        }
    }
}
