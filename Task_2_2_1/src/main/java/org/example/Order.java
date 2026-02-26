package org.example;

import java.util.ArrayList;
import org.example.Enums.Condition;
import org.example.Enums.Pizza;

/**
 * Класс заказа.
 */
public class Order {
    private final int orderNumber;
    private final ArrayList<Pizza> pizzas;
    private Condition condition;

    /**
     * Конструктор заказа.
     */
    public Order(int orderNimber, ArrayList<Pizza> pizzas) {
        this.orderNumber = orderNimber;
        this.condition = Condition.Accepted;
        this.pizzas = pizzas;
    }

    /**
     * Добавление пицц в заказ.
     */
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    /**
     * Получить номер заказа.
     */
    public int getOrderNimber() {
        return orderNumber;
    }

    /**
     * Получить список пицц из заказа.
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Получить состояние заказа.
     */
    public Condition getCondition() {
        return condition;
    }

    /**
     * Смена состояния заказа.
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
        System.out.println("Заказ " + orderNumber + " статус: " + condition);
    }

    /**
     * Получение количества пицц.
     */
    public int getCountPizzas() {
        return pizzas.size();
    }
}
