package org.example.tool;

import org.example.map.Map;

/**
 * Получение номера ячейки в массиве клеток.
 */
public class Tools {
    /**
     * Получить индекс ячейки по координатам пикселя.
     *
     * @param map карта игры
     * @param pixelX координата X в пикселях
     * @param pixelY координата Y в пикселях
     * @return индекс ячейки в массиве
     */
    public static int getCellIndex(Map map, int pixelX, int pixelY) {
        int cellX = pixelX / 30;
        int cellY = pixelY / 30;
        int offsetRows = map.getOffsetRows();
        int cellsInRow = map.getCellsInRow();
        return (cellY - offsetRows) * cellsInRow + cellX;
    }
}
