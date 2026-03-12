package org.example;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Map {
    private ArrayList<Cell> cellMap;
    private final int GAME_WIDTH;
    private final int GAME_HEIGHT;
    private final int cellSize;

    public Map(int GAME_WIDTH, int GAME_HEIGHT,int cellSize) {
        this.GAME_HEIGHT = GAME_HEIGHT;
        this.GAME_WIDTH = GAME_WIDTH;
        this.cellSize = cellSize;
        createCellMap(GAME_WIDTH, GAME_HEIGHT);
    }

    public void paintMap(GraphicsContext gc, ArrayList<Snake> snakes) {
        gc.setFill(Color.rgb(57, 255, 20));
        gc.fillRect(0, 60, GAME_WIDTH, GAME_HEIGHT);
        for (int x = 0; x <= GAME_WIDTH; x += cellSize) {
            gc.strokeLine(x, 60, x, GAME_HEIGHT);
        }
        for (int y = 60; y <= GAME_HEIGHT; y += cellSize) {
            gc.strokeLine(0, y, GAME_WIDTH, y);
        }
        for (Cell cell : cellMap) {
            if (cell.hasApple()) {
                this.paintApple(gc, cell.getPosition().getX(), cell.getPosition().getY(), this.cellSize);
            }
            // рисуй камень!
        }
        for (Snake snake : snakes) {
            snake.paintSnake(gc);

            snake.deleteAppleIfEaten(gc, this, cellMap);
            snake.defeatIfBody(this, cellMap);
        }
    }
    public void randomSpawnApple(GraphicsContext gc) {
        boolean flag = true;
        while (flag) {
            int randomIndex = (int) (Math.random() * cellMap.size());
            if (!cellMap.get(randomIndex).hasApple() && !cellMap.get(randomIndex).hasBody()) {
                cellMap.get(randomIndex).setType(TypeCell.Apple);
                paintApple(gc, cellMap.get(randomIndex).getPosition().getX(), cellMap.get(randomIndex).getPosition().getY(),
                    cellSize);
                flag = false;
            }
        }
    }

    public ArrayList<Cell> createCellMap(int GAME_WIDTH, int GAME_HEIGHT) {
        cellMap = new ArrayList<>();
        int cellsInRow = GAME_WIDTH / 30;
        int cellsInColumn = GAME_HEIGHT / 30;

        for (int y = 2; y < cellsInColumn; y++) {
            for (int x = 0; x < cellsInRow; x++) {
                cellMap.add(new Cell(x * 30, y * 30,TypeCell.Cell));
            }
        }
        return cellMap;
    }

    public int getOffsetRows() {
        return 2;
    }

    public int getCellsInRow() {
        return GAME_WIDTH / 30;
    }

    public void paintApple(GraphicsContext gc, int x, int y, int size) {
        // Тело
        gc.setFill(Color.RED);
        gc.fillOval(x, y, size, size);

        // Черенок (коричневая линия)
        gc.setFill(Color.rgb(139, 69, 19));
        gc.fillRect(x + size / 2 - 2, y - 5, 4, 8);

        // Листик (зелёный)
        gc.setFill(Color.GREEN);
        gc.fillOval(x + size / 2 + 2, y - 3, 6, 4);
    }
}
