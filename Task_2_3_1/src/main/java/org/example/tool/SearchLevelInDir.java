package org.example.tool;

import java.util.List;

/**
 * Класс поиска подходящих нам файлов конфигурации уровня.
 */
public class SearchLevelInDir {
    /**
     * Поиск.
     */
    public static List<LevelPath> searchLevelInDir(String dirPath) {
        List<LevelPath> levels = new java.util.ArrayList<>();
        java.io.File dir = new java.io.File(dirPath);
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("Путь не является директорией: " + dirPath);
        }

        java.io.File[] files = dir.listFiles();
        if (files == null) {
            throw new RuntimeException("Не удалось прочитать содержимое директории: " + dirPath);
        }

        for (java.io.File file : files) {
            if (file.isFile() && file.getName().matches("lvl_\\d+\\.json")) {
                levels.add(new LevelPath(file.getName(), file.getAbsolutePath()));
            }
        }
        return levels;
    }
}
