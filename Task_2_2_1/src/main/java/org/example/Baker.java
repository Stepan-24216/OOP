package org.example;

public class Baker {
    private final int cookingSpeed;

    public Baker(int cookingSpeed) {
        this.cookingSpeed = cookingSpeed;
    }

    public int getCookingSpeed() {
        return cookingSpeed;
    }

    public void cookOrder(Order order, Warehouse warehouse) {
        try {
            Thread.sleep(cookingSpeed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        order.setCondition(Condition.cooked);
        if (warehouse.getFreeSpace() >= order.getCountPizzas()) {
            warehouse.addOrder(order);
        }
    }

}
