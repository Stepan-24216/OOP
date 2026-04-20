package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

/**
 * Парсинг Json.
 */
public class ConfigCreate {

    /**
     * Создание конфига.
     */
    public PizzeriaConfig createConfig(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(path), PizzeriaConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить файл конфигурации по пути: " + path,
                e);
        }
    }
}
