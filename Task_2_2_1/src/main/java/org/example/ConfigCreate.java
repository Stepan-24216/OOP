package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Парсинг Json.
 */
public class ConfigCreate {
    private PizzeriaConfig config;

    public ConfigCreate() {
        this.config = null;
    }

    /**
     * Создание конфига.
     */
    public void createConfig(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            config = mapper.readValue(new File(path), PizzeriaConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получить список пекарей.
     */
    public List<PizzeriaConfig.BakerConfig> getBakers() {
        return config.bakers;
    }

    /**
     * Получить список курьеров.
     */
    public List<PizzeriaConfig.CourierConfig> getCouriers() {
        return config.couriers;
    }

    /**
     * Получить вместимость склада.
     */
    public int getWarehouseCapacity() {
        return config.warehouse.capacity;
    }
}
