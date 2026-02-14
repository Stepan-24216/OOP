package org.example;

import java.util.ArrayList;
import java.util.Queue;

public class Pizzeria {
    Queue<Order> orders = new java.util.LinkedList<>();
    ArrayList<Baker> backers = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public Order takeOrder() {
        if (orders.isEmpty()) {
            return null;
        }
        return orders.poll();
    }
}
