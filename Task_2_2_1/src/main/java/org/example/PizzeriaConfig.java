package org.example;

import java.util.List;

/**
 * Конфиг пиццерии для записи данных из Json.
 */
public class PizzeriaConfig {
    public WarehouseConfig warehouse;
    public List<BakerConfig> bakers;
    public List<CourierConfig> couriers;

    /**
     * Конфиг с важными полями склада.
     */
    public static class WarehouseConfig {
        public int capacity;
    }

    /**
     * Конфиг с важными полями пекаря.
     */
    public static class BakerConfig {
        public int id;
        public int cookingSpeed;
    }

    /**
     * Конфиг с важными полями курьера.
     */
    public static class CourierConfig {
        public int id;
        public int trunkCapacity;
        public int speed;
    }


}