package org.example.building;

import static org.example.enums.Condition.Delivering;
import static org.example.enums.Condition.WaitingCourier;

import java.util.Queue;
import org.example.Order;

/**
 * Класс склада для хранения приготовленных заказов.
 */
public class Warehouse {
    private final int capacity;
    private final Queue<Order> storage;
    private int countPizzas;

    /**
     * Конструктор склада.
     */
    public Warehouse(int capacity) {
        this.capacity = capacity;
        this.countPizzas = 0;
        this.storage = new java.util.LinkedList<>();
    }

    /**
     * Проверка пустой ли склад.
     */
    public synchronized boolean isEmpty() {
        return countPizzas == 0;
    }

    /**
     * Получить количество свободных мест для пиццы.
     */
    public synchronized int getFreeSpace() {
        return capacity - countPizzas;
    }

    /**
     * Добавление заказа на склад после освобождения места.
     */
    public void addOrder(Order order) {
        while (capacity - countPizzas < order.getCountPizzas()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        storage.add(order);
        countPizzas += order.getPizzas().size();
        order.setCondition(WaitingCourier);
        notifyAll();
    }

    /**
     * Взятие заказа со склада.
     */
    public synchronized Order takeOrder(int capacity) {
        if (isEmpty()) {
            return null;
        }
        Order order = storage.peek();
        if (order != null && capacity >= order.getCountPizzas() &&
            order.getCondition() == WaitingCourier) {
            storage.poll();
            order.setCondition(Delivering);
            countPizzas -= order.getCountPizzas();
            notifyAll();
            return order;
        }
        return null;
    }
}
