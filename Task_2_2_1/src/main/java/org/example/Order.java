package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Order {
    private final int orderNumber;
    private Condition condition;
    private ArrayList<Pizza> pizzas = new ArrayList<>();

    public Order(int orderNimber, ArrayList<Pizza> pizzas) {
        this.orderNumber = orderNimber;
        this.condition = Condition.adopted;
        this.pizzas = pizzas;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public int getOrderNimber() {
        return orderNumber;
    }

    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    public Condition getCondition() {
        return condition;
    }

    public int getCountPizzas() {
        return pizzas.size();
    }
}
