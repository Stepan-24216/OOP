package org.example;

import static org.example.Condition.ready;

public class Courier {
    private final int capacity;

    public Courier(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void takeOrder(Warehouse warehouse) {
        if (warehouse.isEmpty()) {
            // join threed
        }
        Order order = warehouse.takeOrder(capacity);
        if (order == null) {
            // join threed
        }
        order.setCondition(ready);
    }
}
