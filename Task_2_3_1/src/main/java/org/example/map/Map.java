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
    public Map(int GAME_WIDTH, int GAME_HEIGHT, List<LevelConfig.StonePositionConfig> stones) {
        this.gameHeight = GAME_HEIGHT;
        this.gameWidth = GAME_WIDTH;
        createCellMap(GAME_WIDTH, GAME_HEIGHT, stones);
    }

    /**
     * Рандомный спавн яблока на поле.
     */
    public void randomSpawnApple() {
        boolean flag = true;
        while (flag) {
            int randomIndex = (int) (Math.random() * cellMap.size());
            if (!cellMap.get(randomIndex).hasApple() && !cellMap.get(randomIndex).hasBody() &&
                !cellMap.get(randomIndex).hasStone()) {
                cellMap.get(randomIndex).setType(TypeCell.Apple);
                flag = false;
            }
        }
    }

    /**
     * Создание массива клеток нашего поля карты.
     */
    public void createCellMap(int GAME_WIDTH, int GAME_HEIGHT,
                              List<LevelConfig.StonePositionConfig> stones) {
        cellMap = new ArrayList<>();
        int cellsInRow = GAME_WIDTH / 30;
        int cellsInColumn = GAME_HEIGHT / 30;

        for (int y = 2; y < cellsInColumn; y++) {
            for (int x = 0; x < cellsInRow; x++) {
                cellMap.add(new Cell(x * 30, y * 30, TypeCell.Cell));
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
            cellMap.get(index).setType(TypeCell.Stone);
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
    public int getgameWidth() {
        return gameWidth;
    }

    /**
     * Высота поля.
     */
    public int getgameHeight() {
        return gameHeight;
    }

    /**
     * Получение массива наших клеток.
     */
    public ArrayList<Cell> getCellMap() {
        return cellMap;
    }
}
