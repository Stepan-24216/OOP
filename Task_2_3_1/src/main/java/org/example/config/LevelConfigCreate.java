package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

/**
 * Класс для создания конфига нашего уровня.
 */
public class LevelConfigCreate {
    /**
     * Создание конфига.
     */
    public LevelConfig createConfig(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(path), LevelConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить файл конфигурации по пути: " + path,
                e);
        }
    }
}
