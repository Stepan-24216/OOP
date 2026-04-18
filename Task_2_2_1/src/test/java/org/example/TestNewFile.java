package org.example;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.example.building.Warehouse;
import org.example.enums.TypeWorker;
import org.example.enums.Pizza;
import org.example.workers.Baker;
import org.example.workers.Courier;
import org.example.workers.Worker;
import org.junit.jupiter.api.Test;

/**
 * Тест новых файлов.
 */
public class TestNewFile {
    /**
     * Проверяет, что enum работников содержит ожидаемые значения.
     */
    @Test
    public void testTypeWorkerContainsExpectedValues() {
        assertArrayEquals(
            new TypeWorker[] {TypeWorker.BAKER, TypeWorker.COURIER},
            TypeWorker.values()
        );
    }

    /**
     * Проверяет базовый контракт Worker и типы конкретных работников.
     */
    @Test
    public void testWorkersImplementContractAndReturnType() {
        Warehouse warehouse = new Warehouse(5);
        Worker baker = new Baker(1, 1, warehouse, null);
        Worker courier = new Courier(1, 1, 3, warehouse);

        assertEquals(TypeWorker.BAKER, baker.getType());
        assertEquals(TypeWorker.COURIER, courier.getType());
    }

    /**
     * Проверяет начальные поля заказа и подсчет количества пицц.
     */
    @Test
    public void testOrderStoresPizzas() {
        ArrayList<Pizza> pizzas = new ArrayList<>();
        pizzas.add(Pizza.MARGHERITA);
        pizzas.add(Pizza.PEPPERONI);
        Order order = new Order(7, pizzas);

        assertEquals(7, order.getOrderNimber());
        assertEquals(2, order.getCountPizzas());
        assertEquals(pizzas, order.getPizzas());
    }

    /**
     * Проверяет базовое FIFO-поведение очереди.
     */
    @Test
    public void testSuperQueueAddAndTake() {
        SuperQueue queue = new SuperQueue();
        Order first = createOrder(1, 1);
        Order second = createOrder(2, 2);

        assertTrue(queue.addElement(first));
        assertTrue(queue.addElement(second));
        assertSame(first, queue.takeElement());
        assertSame(second, queue.takeElement());
        assertTrue(queue.isEmpty());
    }

    /**
     * Проверяет, что закрытая пустая очередь возвращает null при взятии.
     */
    @Test
    public void testClosedEmptyQueueReturnsNull() {
        SuperQueue queue = new SuperQueue();
        queue.close();

        assertNull(queue.takeElement());
    }

    /**
     * Проверяет, что takeIfFits выбирает подходящий по вместимости заказ.
     */
    @Test
    public void testTakeIfFitsReturnsSmallOrder() {
        SuperQueue queue = new SuperQueue();
        Order big = createOrder(1, 4);
        Order small = createOrder(2, 2);

        queue.addElement(big);
        queue.addElement(small);

        assertSame(small, queue.takeIfFits(2));
        assertSame(big, queue.takeElement());
    }

    /**
     * Создание заказа.
     */
    private static Order createOrder(int id, int pizzaCount) {
        ArrayList<Pizza> pizzas = new ArrayList<>();
        for (int i = 0; i < pizzaCount; i++) {
            pizzas.add(Pizza.MARGHERITA);
        }
        return new Order(id, pizzas);
    }
}
