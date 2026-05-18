package org.example.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.example.config.LevelConfigCreate;

/**
 * Класс поиска подходящих нам файлов конфигурации уровня.
 */
public class SearchLevelInDir {
    /**
     * Поиск.
     */
    public static List<LevelPath> searchLevelInDir(String dirPath) {
        List<LevelPath> levels = new ArrayList<>();
        File dir = new File(dirPath);
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("Путь не является директорией: " + dirPath);
        }

        File[] files = dir.listFiles();
        if (files == null) {
            throw new RuntimeException("Не удалось прочитать содержимое директории: " + dirPath);
        }

        LevelConfigCreate configCreate = new LevelConfigCreate();

        for (File file : files) {
            if (file.isFile() && file.getName().matches("lvl_\\d+\\.json")) {
                try {
                    configCreate.createConfig(file.getAbsolutePath());
                    levels.add(new LevelPath(file.getName(), file.getAbsolutePath()));
                } catch (RuntimeException e) {
                    // Невалидные конфиги пропускаем: меню покажет отдельное сообщение об ошибке.
                }
            }
        }
        return levels;
    }
}
