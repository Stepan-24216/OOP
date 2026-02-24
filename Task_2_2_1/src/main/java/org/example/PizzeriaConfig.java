package org.example;

import java.util.List;

public class PizzeriaConfig {
    public WarehouseConfig warehouse;
    public List<BakerConfig> bakers;
    public List<CourierConfig> couriers;

    public static class WarehouseConfig {
        public int capacity;
    }

    public static class BakerConfig {
        public int id;
        public int cookingSpeed;
    }

    public static class CourierConfig {
        public int id;
        public int trunkCapacity;
        public int speed;
    }


}