package org.example;

import java.util.ArrayList;

public class Order {
    private final int orderNumber;
    private Condition condition;
    private ArrayList<Pizza> pizzas;

    public Order(int orderNimber, ArrayList<Pizza> pizzas) {
        this.orderNumber = orderNimber;
        this.condition = Condition.Accepted;
        this.pizzas = pizzas;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
        System.out.println("Заказ " + orderNumber + " статус: " + condition);
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
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
