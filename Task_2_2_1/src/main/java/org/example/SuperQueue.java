package org.example;

import java.util.Queue;

/**
 * Класс моей супер крутой очереди для пиццерии.
 */
public class SuperQueue {
    private final Queue<Order> queue;
    private final int capacity;
    private int countPizzas;
    private boolean closed = false;

    /**
     * Конструктор.
     */
    public SuperQueue(int capacity) {
        this.countPizzas = 0;
        this.queue = new java.util.LinkedList<>();
        this.capacity = capacity;
    }

    /**
     * Конструктор 2.
     */
    public SuperQueue() {
        this(-1);
    }

    /**
     * Закрытие.
     */
    public synchronized void close() {
        closed = true;
        notifyAll();
    }

    /**
     * Проверка пустая ли очередь заказов.
     */
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Добавление элемента.
     */
    public synchronized boolean addElement(Order order) {
        while (capacity != -1 && countPizzas + order.getCountPizzas() > capacity) {
            if (closed) {
                return false;
            }
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        queue.add(order);
        countPizzas += order.getCountPizzas();
        notifyAll();
        return true;
    }

    /**
     * Получение элемента.
     */
    public synchronized Order takeElement() {
        while (queue.isEmpty()) {
            if (closed) {
                return null;
            }
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        Order order = queue.poll();
        countPizzas -= order.getCountPizzas();
        notifyAll();
        return order;
    }

    /**
     * Попытка взять заказ если курьер может.
     */
    public synchronized Order takeIfFits(int courierCapacity) {
        while (true) {
            if (!queue.isEmpty()) {
                for (Order order : queue) {
                    if (order.getCountPizzas() <= courierCapacity) {
                        queue.remove(order);
                        countPizzas -= order.getCountPizzas();
                        notifyAll();
                        return order;
                    }
                }
                if (closed) {
                    return null;
                }
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
                continue;
            }
            if (closed) {
                return null;
            }
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
    }
}
