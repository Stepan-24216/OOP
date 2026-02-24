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

    public void createConfig(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            config = mapper.readValue(new File(path), PizzeriaConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<PizzeriaConfig.BakerConfig> getBakers() {
        return config.bakers;
    }

    public List<PizzeriaConfig.CourierConfig> getCouriers() {
        return config.couriers;
    }

    public int getWarehouseCapacity() {
        return config.warehouse.capacity;
    }
}
