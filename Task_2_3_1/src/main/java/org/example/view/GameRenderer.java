package org.example.view;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.map.Cell;
import org.example.map.Map;
import org.example.snake.Snake;
import org.example.snake.Tail;

/**
 * Класс для отрисовки объектов на карте.
 */
public class GameRenderer {
    private GraphicsContext gc;
    private Map map;
    private ArrayList<Snake> snakes;
    private int cellSize = 30;

    /**
     * Конструктор.
     */
    public GameRenderer(GraphicsContext gc, Map map, ArrayList<Snake> snakes) {
        this.gc = gc;
        this.map = map;
        this.snakes = snakes;
    }

    /**
     * Отрисовка змейки.
     */
    public void paintSnake(Snake snake) {
        paintHead(gc, snake.getHead().getCordX(), snake.getHead().getCordY());
        for (Tail tail : snake.getTails().subList(1, snake.getTails().size())) {
            paintTail(gc, tail);
        }
    }

    /**
     * Отрисовка хвоста змейки.
     */
    public void paintTail(GraphicsContext gc, Tail tail) {
        gc.setFill(Color.BLUE);
        gc.fillRect(tail.getCordX(), tail.getCordY(), cellSize, cellSize);

    }

    /**
     * Отрисовка головы змейки.
     */
    private void paintHead(GraphicsContext gc, int x, int y) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, 30, 30);

        gc.setFill(Color.MAGENTA);
        gc.fillRect(x + 4, y + 6, 6, 6);
        gc.fillRect(x + 20, y + 6, 6, 6);

        gc.setFill(Color.RED);
        gc.fillArc(x + 5, y + 15, 20, 10, 0, -180, javafx.scene.shape.ArcType.ROUND);
    }

    /**
     * Отрисовка карты.
     */
    public void paintMap() {
        gc.setFill(Color.rgb(57, 255, 20));
        gc.fillRect(0, 60, map.getgameWidth(), map.getgameHeight());
        for (int x = 0; x <= map.getgameWidth(); x += cellSize) {
            gc.strokeLine(x, 60, x, map.getgameHeight());
        }
        for (int y = 60; y <= map.getgameHeight(); y += cellSize) {
            gc.strokeLine(0, y, map.getgameWidth(), y);
        }
        for (Cell cell : map.getCellMap()) {
            if (cell.hasApple()) {
                paintApple(cell.getPosition().getCordX(), cell.getPosition().getCordY(), this.cellSize);
            }
            if (cell.hasStone()) {
                gc.setFill(Color.GRAY);
                gc.fillRect(cell.getPosition().getCordX(), cell.getPosition().getCordY(), cellSize,
                    cellSize);
            }
        }
        for (Snake snake : snakes) {
            paintSnake(snake);
        }
    }

    /**
     * Отрисовка яблока.
     */
    public void paintApple(int x, int y, int size) {
        gc.setFill(Color.RED);
        gc.fillOval(x, y, size, size);

        gc.setFill(Color.rgb(139, 69, 19));
        gc.fillRect(x + size / 2 - 2, y - 5, 4, 8);

        gc.setFill(Color.GREEN);
        gc.fillOval(x + size / 2 + 2, y - 3, 6, 4);
    }
}
