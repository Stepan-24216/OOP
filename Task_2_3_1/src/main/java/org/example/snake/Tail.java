package org.example.snake;

/**
 * Класс хвоста.
 */
public class Tail {
    private int cordX;
    private int cordY;

    /**
     * Конструктор.
     */
    public Tail(int cordX, int cordY) {
        this.cordX = cordX;
        this.cordY = cordY;
    }

    /**
     * Получение координаты икс.
     */
    public int getCordX() {
        return cordX;
    }

    /**
     * Установка координаты икс.
     */
    public void setCordX(int cordX) {
        this.cordX = cordX;
    }

    /**
     * Получение координаты игрик.
     */
    public int getCordY() {
        return cordY;
    }

    /**
     * Установка координаты игрик.
     */
    public void setCordY(int cordY) {
        this.cordY = cordY;
    }
}
