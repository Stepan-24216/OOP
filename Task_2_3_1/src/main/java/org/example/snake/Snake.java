package org.example.snake;

import java.util.ArrayList;
import org.example.map.Cell;
import org.example.map.Map;
import org.example.map.TypeCell;
import org.example.tool.Tools;

/**
 * Класс нашей змейки.
 */
public class Snake {
    private final Tail head;
    private final ArrayList<Tail> tails;
    private volatile int score;

    /**
     * Конструктор.
     */
    public Snake(int gameWidth, int gameHeight) {
        this.score = 0;
        this.head = new Tail(gameWidth / 2 + (gameWidth / 2) % 30,
            gameHeight / 2 + (gameHeight / 2) % 30);
        this.tails = new ArrayList<>();
        this.tails.add(head);
    }

    /**
     * Двигаемся.
     */
    public void move(int cordX, int cordY, ArrayList<Cell> cellMap, int gameWidth,
                     int offsetRows) {
        int tailX = tails.get(tails.size() - 1).getCordX();
        int tailY = tails.get(tails.size() - 1).getCordY();
        int snakeSize = 30;
        int cellsInRow = gameWidth / snakeSize;

        int tailIndex = (tailY / snakeSize - offsetRows) * cellsInRow + (tailX / snakeSize);
        if (tailIndex >= 0 && tailIndex < cellMap.size()) {
            cellMap.get(tailIndex).setType(TypeCell.Cell);
        }

        if (tails.size() > 1) {
            for (int i = tails.size() - 1; i > 0; i--) {
                tails.get(i).setCordX(tails.get(i - 1).getCordX());
                tails.get(i).setCordY(tails.get(i - 1).getCordY());
                if (i <= tails.size() - 2) {
                    int idx = (tails.get(i).getCordY() / snakeSize - offsetRows) * cellsInRow
                        + (tails.get(i).getCordX() / snakeSize);
                    if (idx >= 0 && idx < cellMap.size()) {
                        cellMap.get(idx).setType(TypeCell.Body);
                    }
                }
            }
        }

        head.setCordX(cordX);
        head.setCordY(cordY);
    }

    /**
     * Кушаем яблоко.
     */
    public void eatApple() {
        score++;
        Tail newTail = new Tail(tails.get(tails.size() - 1).getCordX(),
            tails.get(tails.size() - 1).getCordY());
        tails.add(newTail);
    }

    /**
     * Попытка скушать яблоко.
     */
    public boolean tryEatApple(Map map) {
        int headIndex = Tools.getCellIndex(map, this.getCordX(), this.getCordY());

        if (map.getCellMap().get(headIndex).hasApple()) {
            map.getCellMap().get(headIndex).setType(TypeCell.Cell);
            this.eatApple();
            return true;
        }
        return false;
    }

    /**
     * Проверка на поражение.
     */
    public boolean checkDefeat(Map map) {
        int headIndex = Tools.getCellIndex(map, getCordX(), getCordY());
        if (headIndex < 0 || headIndex >= map.getCellMap().size()) {
            return true;
        }

        Cell currentCell = map.getCellMap().get(headIndex);
        return currentCell.hasBody() || currentCell.hasStone();
    }

    /**
     * Получение счёта.
     */
    public int getScore() {
        return score;
    }

    /**
     * Получение головы.
     */
    public Tail getHead() {
        return head;
    }

    /**
     * Получение координаты икса.
     */
    public int getCordX() {
        return head.getCordX();
    }

    /**
     * Получение координаты игрика.
     */
    public int getCordY() {
        return head.getCordY();
    }

    /**
     * Получение массива с хвостом.
     */
    public ArrayList<Tail> getTails() {
        return tails;
    }
}
