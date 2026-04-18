package org.example.building;

import static org.example.enums.Condition.Delivering;
import static org.example.enums.Condition.WaitingCourier;

import org.example.Order;
import org.example.SuperQueue;

/**
 * Класс склада для хранения приготовленных заказов.
 */
public class Warehouse {
    private final int capacity;
    private final SuperQueue storage;

    /**
     * Конструктор склада.
     */
    public Warehouse(int capacity) {
        this.capacity = capacity;
        this.storage = new SuperQueue(capacity);
    }

    /**
     * Проверка пустой ли склад.
     */
    public boolean isEmpty() {
        return storage.isEmpty();
    }

    /**
     * Добавление заказа на склад после освобождения места.
     */
    public void addOrder(Order order) {
        order.setCondition(WaitingCourier);
        storage.addElement(order);
    }

    /**
     * Взятие заказа со склада.
     */
    public Order takeOrder(int capacity) {
        return storage.takeIfFits(capacity);
    }

    public void close() {
        storage.close();
    }
}
