package org.example;

public class Tools {
    public static int getCellIndex(Map map, int pixelX, int pixelY) {
        int cellX = pixelX / 30;
        int cellY = pixelY / 30;
        int offsetRows = map.getOffsetRows();
        int cellsInRow = map.getCellsInRow();
        return (cellY - offsetRows) * cellsInRow + cellX;
    }
}
