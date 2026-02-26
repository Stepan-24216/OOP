package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.example.Building.Pizzeria;
import org.example.Enums.Condition;
import org.example.Enums.Pizza;
import org.junit.jupiter.api.Test;

/**
 * Базовый тест работы.
 */
public class TestWork {
    /**
     * Тест на 100 сгенерированных данных.
     */
    @Test
    public void testWorking() {
        Pizzeria pizzeria = new Pizzeria("src/main/resources/config.json", 5000);
        SimulateCreateOrder simulateCreateOrder = new SimulateCreateOrder(pizzeria, 100, 5);
        Thread thread = new Thread(simulateCreateOrder);
        thread.start();

        pizzeria.pizzeriaWork();
    }

    /**
     * Тест баззовой работы с маленьким количеством заказов.
     */
    @Test
    public void testBaseWorking() {
        Pizzeria pizzeria = new Pizzeria("src/main/resources/config.json", 5000);
        ArrayList<Pizza> pizzas = new ArrayList<>();
        pizzas.add(Pizza.PEPPERONI);
        pizzas.add(Pizza.MARGHERITA);
        pizzas.add(Pizza.VEGGIE);
        Order order1 = new Order(1, pizzas);
        pizzeria.addOrder(order1);
        ArrayList<Pizza> pizzas2 = new ArrayList<>();
        pizzas2.add(Pizza.MEAT_LOVERS);
        pizzas2.add(Pizza.MARGHERITA);
        Order order2 = new Order(2, pizzas);
        pizzeria.addOrder(order2);


        pizzeria.pizzeriaWork();
        assertEquals(Condition.Completed, order1.getCondition());
        assertEquals(Condition.Completed, order2.getCondition());
    }

}
