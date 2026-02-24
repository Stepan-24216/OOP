package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import org.example.Workers.Baker;
import org.example.Workers.Courier;

public class Pizzeria {
    private volatile Queue<Order> orders = new java.util.LinkedList<>();
    private volatile Warehouse warehouse;
    private String pathToConfig;
    private List<Runnable> workers = new ArrayList<>();
    private final List<Thread> threads = new ArrayList<>();
    public volatile boolean isOpen = true;
    public int endTime;

    public Pizzeria(String pathToConfig, int endTime) {
        this.pathToConfig = pathToConfig;
        this.endTime = endTime;
    }

    public boolean orderIsEmpty() {
        return orders.isEmpty();
    }

    public synchronized void addOrder(Order order) {
        if (!isOpen) {
            return; // Заказ не принимается, так как пиццерия закрыта
        }
        orders.add(order);
        System.out.println("Заказ " + order.getOrderNimber() + " статус: " + order.getCondition());
    }

    public boolean isOpen() {
        return isOpen;
    }

    public synchronized Order takeOrder() {
        if (orders.isEmpty()) {
            return null;
        }
        return orders.poll();
    }

    public void pizzeriaWork() {
        System.out.println("Pizzeria is open!");
        start();
        try {
            Thread.sleep(endTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isOpen = false;
        stop();
        System.out.println("Pizzeria is closed!");
    }

    private void start() {
        ConfigCreate configCreate = new ConfigCreate();
        configCreate.createConfig(pathToConfig);
        warehouse = new Warehouse(configCreate.getWarehouseCapacity());
        for (PizzeriaConfig.BakerConfig backerConf : configCreate.getBakers()) {
            Baker backer = new Baker(backerConf.id, backerConf.cookingSpeed, warehouse, this);
            workers.add(backer);
        }
        for (PizzeriaConfig.CourierConfig courierConf : configCreate.getCouriers()) {
            Courier courier = new Courier(courierConf.id, courierConf.speed, courierConf.trunkCapacity, warehouse,this);
            workers.add(courier);
        }
        for (Runnable worker : workers) {
            Thread thread = new Thread(worker);
            threads.add(thread);
            thread.start();
        }
    }

    private void stop() {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
