package org.example.Config;

import java.util.List;

/**
 * Конфиг пиццерии для записи данных из Json.
 */
public class PizzeriaConfig {
    private WarehouseConfig warehouse;
    private List<BakerConfig> bakers;
    private List<CourierConfig> couriers;

    /**
     * Получить вместимость склада.
     */
    public WarehouseConfig getWarehouse() { return warehouse; }
    /**
     * Получить список пекарей.
     */
    public List<BakerConfig> getBakers() { return bakers; }
    /**
     * Получить список курьеров.
     */
    public List<CourierConfig> getCouriers() { return couriers; }

    /**
     * Конфиг с важными полями склада.
     */
    public static class WarehouseConfig {
        private int capacity;

        public int getCapacity() { return capacity; }
    }

    /**
     * Конфиг с важными полями пекаря.
     */
    public static class BakerConfig {
        private int id;
        private int cookingSpeed;

        public int getId() { return id; }
        public int getCookingSpeed() { return cookingSpeed; }
    }

    /**
     * Конфиг с важными полями курьера.
     */
    public static class CourierConfig {
        private int id;
        private int trunkCapacity;
        private int speed;

        public int getId() { return id; }
        public int getTrunkCapacity() { return trunkCapacity; }
        public int getSpeed() { return speed; }
    }


}