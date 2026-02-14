package org.example;

import static org.example.Condition.cooked;
import static org.example.Condition.delivered;

import java.util.Queue;

public class Warehouse {
    private final int capacity;
    private int countPizzas;
    private Queue<Order> storage;

    Warehouse ( int capacity ) {
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

    public void addOrder(Order order) {
        if (isFull()) {
            return;
        }
        storage.add(order);
        countPizzas += order.getPizzas().size();
    }

    public Order takeOrder(int capacity) {
        if (isEmpty()) {
            return null;
        }
        Order order = storage.peek();
        if (order != null && capacity >= order.getCountPizzas() && order.getCondition() == cooked) {;
            order.setCondition(delivered);
            countPizzas -= order.getCountPizzas();
        } else {
            return null;
        }
        return storage.poll();
    }
}
