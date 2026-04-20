package org.example.building;

import java.util.ArrayList;
import java.util.List;
import org.example.Order;
import org.example.SuperQueue;
import org.example.config.ConfigCreate;
import org.example.config.PizzeriaConfig;
import org.example.enums.TypeWorker;
import org.example.workers.Baker;
import org.example.workers.Courier;
import org.example.workers.Worker;

/**
 * Класс моей пиццерии.
 */
public class Pizzeria {
    private final List<Worker> workers;
    private final List<WorkerThread> workerThreads;
    private final int endTime;
    private final SuperQueue orders;
    private final String pathToConfig;
    private volatile boolean isOpen;
    private volatile Warehouse warehouse;

    /**
     * Конструктор пиццерии.
     */
    public Pizzeria(String pathToConfig, int endTime) {
        this.pathToConfig = pathToConfig;
        this.endTime = endTime;
        this.isOpen = true;
        this.orders = new SuperQueue();
        this.workers = new ArrayList<>();
        this.workerThreads = new ArrayList<>();
    }

    /**
     * Метод проверки наличия заказов в очереди на приготовление.
     */
    public boolean orderNotEmpty() {
        return !orders.isEmpty();
    }

    /**
     * Метод добавления заказа в очередь на приготовление.
     */
    public void addOrder(Order order) {
        if (!isOpen) {
            throw new IllegalStateException(
                "Не удалось добавить заказ: пиццерия закрыта.");// Заказ не принимается, так как
            // пиццерия закрыта
        }
        orders.addElement(order);
        System.out.println("Заказ " + order.getOrderNimber() + " статус: " + order.getCondition());
    }

    /**
     * Метод взятия заказа при наличии.
     */
    public Order takeOrder() {
        return orders.takeElement();
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

        orders.close();

        waitWorkersByType(TypeWorker.BAKER);

        warehouse.close();

        waitWorkersByType(TypeWorker.COURIER);

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
            addWorker(new Baker(backerConf.getId(), backerConf.getCookingSpeed(), warehouse, this));
        }
        for (PizzeriaConfig.CourierConfig courierConf : config.getCouriers()) {
            addWorker(new Courier(courierConf.getId(), courierConf.getSpeed(),
                courierConf.getTrunkCapacity(), warehouse));
        }

        startAllWorkers();
    }

    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    private void startAllWorkers() {
        for (Worker worker : workers) {
            Thread thread = new Thread(worker);
            workerThreads.add(new WorkerThread(worker, thread));
            thread.start();
        }
    }

    private void waitWorkersByType(TypeWorker type) {
        for (WorkerThread workerThread : workerThreads) {
            if (workerThread.worker.getType() != type) {
                continue;
            }
            try {
                workerThread.thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private record WorkerThread(Worker worker, Thread thread) {
    }
}
