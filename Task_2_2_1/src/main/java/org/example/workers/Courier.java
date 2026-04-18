package org.example.workers;

import static org.example.enums.Condition.Completed;

import org.example.Order;
import org.example.enums.TypeWorker;
import org.example.building.Pizzeria;
import org.example.building.Warehouse;

/**
 * Класс моего курьера.
 */
public class Courier implements Worker {
    private final int id;
    private final int speed;
    private final int capacity;
    private final Warehouse warehouse;

    /**
     * Конструктор курьера.
     */
    public Courier(int id, int speed, int capacity, Warehouse warehouse) {
        this.id = id;
        this.speed = speed;
        this.capacity = capacity;
        this.warehouse = warehouse;
    }

    /**
     * Рабочий день курьера.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Order order = warehouse.takeOrder(capacity);
            if (order == null) break;
            try {
                Thread.sleep(speed);
                order.setCondition(Completed);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public TypeWorker getType() {
        return TypeWorker.COURIER;
    }
}
