package org.example.Workers;

import static org.example.Condition.Completed;
import static org.example.Condition.Delivering;

import org.example.Order;
import org.example.Pizzeria;
import org.example.Warehouse;

public class Courier implements Runnable{
    private final int id;
    private final int speed;
    private final int capacity;
    private Warehouse warehouse;
    private Pizzeria pizzeria;

    public Courier(int id, int speed, int capacity, Warehouse warehouse,Pizzeria pizzeria) {
        this.id = id;
        this.speed = speed;
        this.capacity = capacity;
        this.warehouse = warehouse;
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && (pizzeria.isOpen() || !warehouse.isEmpty())) {
//            if (!pizzeria.isOpen() && warehouse.isEmpty()) {
//                break;
//            }
            Order order = warehouse.takeOrder(capacity);
            if (order != null) {
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                order.setCondition(Completed);
            }
        }
    }
}
