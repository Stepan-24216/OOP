package org.example.tool;

/**
 * Класс пути к файлу.
 */
public class LevelPath {
    private String nameFile;
    private String path;

    public LevelPath(String nameFile, String path) {
        this.nameFile = nameFile;
        this.path = path;
    }

    /**
     * Получение имени файла.
     */
    public String getNameFile() {
        return nameFile;
    }

    /**
     * Получение пути.
     */
    public String getPath() {
        return path;
    }
}
