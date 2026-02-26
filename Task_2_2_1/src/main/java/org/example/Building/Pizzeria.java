package org.example.Building;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import org.example.Config.ConfigCreate;
import org.example.Config.PizzeriaConfig;
import org.example.Order;
import org.example.Workers.Baker;
import org.example.Workers.Courier;

/**
 * Класс моей пиццерии.
 */
public class Pizzeria {
    private final List<Thread> threads = new ArrayList<>();
    private final int endTime;
    private final Queue<Order> orders = new java.util.LinkedList<>();
    private final String pathToConfig;
    private final List<Runnable> workers = new ArrayList<>();
    public volatile boolean isOpen = true;
    private volatile Warehouse warehouse;

    /**
     * Конструктор пиццерии.
     */
    public Pizzeria(String pathToConfig, int endTime) {
        this.pathToConfig = pathToConfig;
        this.endTime = endTime;
    }

    /**
     * Проверка пустая ли очередь заказов.
     */
    public boolean orderNotEmpty() {
        return !orders.isEmpty();
    }

    /**
     * Метод добавляения заказа в очередь на приготовление.
     */
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

    /**
     * Метод взятия заказа при наличии.
     */
    public synchronized Order takeOrder() {
        if (orders.isEmpty()) {
            return null;
        }
        return orders.poll();
    }

    /**
     * Начало работы пиццерии.
     */
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

    /**
     * Метод стартовых действий.
     */
    private void start() {
        ConfigCreate configCreate = new ConfigCreate();
        PizzeriaConfig config = configCreate.createConfig(pathToConfig);
        warehouse = new Warehouse(config.getWarehouse().getCapacity());
        for (PizzeriaConfig.BakerConfig backerConf : config.getBakers()) {
            Baker backer =
                new Baker(backerConf.getId(), backerConf.getCookingSpeed(), warehouse, this);
            workers.add(backer);
        }
        for (PizzeriaConfig.CourierConfig courierConf : config.getCouriers()) {
            Courier courier =
                new Courier(courierConf.getId(), courierConf.getSpeed(),
                    courierConf.getTrunkCapacity(), warehouse,
                    this);
            workers.add(courier);
        }
        for (Runnable worker : workers) {
            Thread thread = new Thread(worker);
            threads.add(thread);
            thread.start();
        }
    }

    /**
     * Конец рабочего дня.
     */
    private void stop() {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Не удалось дождаться завершения потока ", e);
            }
        }
    }
}
