package org.example.view;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.game.GameObserver;
import org.example.map.Cell;
import org.example.map.Map;
import org.example.snake.Snake;
import org.example.snake.Tail;

/**
 * Отрисовка карты и змейки.
 */
public class GameRenderer implements GameObserver {

    private final GraphicsContext gc;
    private final Map map;
    private final ArrayList<Snake> snakes;
    private final int cellSize = 30;

    /**
     * Конструктор.
     */
    public GameRenderer(GraphicsContext gc, Map map, ArrayList<Snake> snakes) {
        this.gc = gc;
        this.map = map;
        this.snakes = snakes;
    }

    /**
     * Модель уведомила нас об изменении состояния.
     */
    @Override
    public void onGameStateChanged() {
        paintMap();
    }

    /**
     * Отрисовка карты целиком.
     */
    public void paintMap() {
        gc.setFill(Color.rgb(57, 255, 20));
        gc.fillRect(0, 60, map.getGameWidth(), map.getGameHeight());

        for (int x = 0; x <= map.getGameWidth(); x += cellSize) {
            gc.strokeLine(x, 60, x, map.getGameHeight());
        }
        for (int y = 60; y <= map.getGameHeight(); y += cellSize) {
            gc.strokeLine(0, y, map.getGameWidth(), y);
        }

        for (Cell cell : map.getCellMap()) {
            if (cell.hasApple()) {
                paintApple(cell.getPosition().getCordX(),
                    cell.getPosition().getCordY(), cellSize);
            }
            if (cell.hasStone()) {
                gc.setFill(Color.GRAY);
                gc.fillRect(cell.getPosition().getCordX(),
                    cell.getPosition().getCordY(), cellSize, cellSize);
            }
        }

        for (Snake snake : snakes) {
            paintSnake(snake);
        }
    }

    /**
     * Отрисовка змейки.
     */
    public void paintSnake(Snake snake) {
        paintHead(snake.getHead().getCordX(), snake.getHead().getCordY());
        for (Tail tail : snake.getTails().subList(1, snake.getTails().size())) {
            paintTail(tail);
        }
    }

    /**
     * Отрисовка хвоста змейки.
     */
    private void paintTail(Tail tail) {
        gc.setFill(Color.BLUE);
        gc.fillRect(tail.getCordX(), tail.getCordY(), cellSize, cellSize);
    }

    /**
     * Отрисовка головы змейки.
     */
    private void paintHead(int x, int y) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, 30, 30);

        gc.setFill(Color.MAGENTA);
        gc.fillRect(x + 4, y + 6, 6, 6);
        gc.fillRect(x + 20, y + 6, 6, 6);

        gc.setFill(Color.RED);
        gc.fillArc(x + 5, y + 15, 20, 10, 0, -180, javafx.scene.shape.ArcType.ROUND);
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