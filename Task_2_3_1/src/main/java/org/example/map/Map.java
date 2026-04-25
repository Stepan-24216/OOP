package org.example.map;

import java.util.ArrayList;
import java.util.List;
import org.example.config.LevelConfig;

/**
 * Класс карты игры.
 */
public class Map {
    private final int gameWidth;
    private final int gameHeight;
    private ArrayList<Cell> cellMap;

    /**
     * Конструктор.
     */
    public Map(int gameWidth, int gameHeight, List<LevelConfig.StonePositionConfig> stones) {
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
        createCellMap(gameWidth, gameHeight, stones);
    }

    /**
     * Рандомный спавн яблока на поле.
     */
    public void randomSpawnApple() {
        boolean flag = true;
        while (flag) {
            int randomIndex = (int) (Math.random() * cellMap.size());
            if (!cellMap.get(randomIndex).hasApple() && !cellMap.get(randomIndex).hasBody()
                && !cellMap.get(randomIndex).hasStone()) {
                cellMap.get(randomIndex).setType(TypeCell.APPLE);
                flag = false;
            }
        }
    }

    /**
     * Создание массива клеток нашего поля карты.
     */
    public void createCellMap(int gameWidth, int gameHeight,
                              List<LevelConfig.StonePositionConfig> stones) {
        cellMap = new ArrayList<>();
        int cellsInRow = gameWidth / 30;
        int cellsInColumn = gameHeight / 30;

        for (int y = 2; y < cellsInColumn; y++) {
            for (int x = 0; x < cellsInRow; x++) {
                cellMap.add(new Cell(x * 30, y * 30, TypeCell.CELL));
            }
        }
        for (LevelConfig.StonePositionConfig stone : stones) {
            int row = stone.getNumString() - 1;
            int col = stone.getNumCell() - 1;

            int playableRows = cellsInColumn - getOffsetRows();
            if (row < 0 || row >= playableRows || col < 0 || col >= cellsInRow) {
                continue;
            }

            int index = row * cellsInRow + col;
            cellMap.get(index).setType(TypeCell.STONE);
        }
    }

    /**
     * Получение сдвига поля для вывода счёта.
     */
    public int getOffsetRows() {
        return 2;
    }

    /**
     * Получение числа клеток в строке.
     */
    public int getCellsInRow() {
        return gameWidth / 30;
    }

    /**
     * Ширина поля.
     */
    public int getGameWidth() {
        return gameWidth;
    }

    /**
     * Высота поля.
     */
    public int getGameHeight() {
        return gameHeight;
    }

    /**
     * Получение массива наших клеток.
     */
    public ArrayList<Cell> getCellMap() {
        return cellMap;
    }
}
