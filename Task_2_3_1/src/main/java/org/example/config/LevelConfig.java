package org.example.config;

import java.util.List;

/**
 * Конфигурация уровня.
 */
public class LevelConfig {
    private GameSizeConfig size;
    private List<StonePositionConfig> stones;

    public GameSizeConfig getSize() {
        return size;
    }

    public List<StonePositionConfig> getStones() {
        return stones;
    }

    /**
     * Класс с размерами поля.
     */
    public static class GameSizeConfig {
        private int height;
        private int width;

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }

    /**
     * Класс позиций камней на поле.
     */
    public static class StonePositionConfig {
        private int numString;
        private int numCell;

        public int getNumString() {
            return numString;
        }

        public int getNumCell() {
            return numCell;
        }
    }
}
