package org.example;

import static org.example.Condition.Delivering;
import static org.example.Condition.WaitingCourier;

import java.util.Queue;

/**
 * Класс склада для хранения приготовленных заказов.
 */
public class Warehouse {
    private final int capacity;
    private int countPizzas;
    private final Queue<Order> storage;

    Warehouse(int capacity) {
        this.capacity = capacity;
        this.countPizzas = 0;
        this.storage = new java.util.LinkedList<>();
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isFull() {
        return countPizzas >= capacity;
    }

    public boolean isEmpty() {
        return countPizzas == 0;
    }

    public int getFreeSpace() {
        return capacity - countPizzas;
    }

    /**
     * Добавление заказа на склад после освобождения места.
     */
    public synchronized void addOrder(Order order) {
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
