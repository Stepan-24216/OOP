package org.example.workers;

import org.example.Order;
import org.example.enums.TypeWorker;
import org.example.building.Pizzeria;
import org.example.building.Warehouse;
import org.example.enums.Condition;

/**
 * Класс моих пекарей.
 */
public class Baker implements Worker {
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
        while (!Thread.currentThread().isInterrupted()) {
            Order order = pizzeria.takeOrder();
            if (order == null) break;
            order.setCondition(Condition.Cooking);
            try {
                Thread.sleep(cookingSpeed);
            } catch (InterruptedException e) {
                System.err.println(
                    "Ошибка доставки заказа, пекарь сжёг пиццу :(" + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
            warehouse.addOrder(order);
        }
    }

    public TypeWorker getType() {
        return TypeWorker.BAKER;
    }
}
