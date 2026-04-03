package org.example.map;

/**
 * Класс одной клетки на поле.
 */
public class Cell {
    private final Position position;
    private TypeCell type;

    /**
     * Конструктор.
     */
    public Cell(int x, int y, TypeCell type) {
        this.position = new Position(x, y);
        this.type = type;
    }

    /**
     * Получение типа клетки.
     */
    public TypeCell getType() {
        return type;
    }

    /**
     * Установка типа клетки.
     */
    public void setType(TypeCell type) {
        this.type = type;
    }

    /**
     * Получение позиции клетки.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Проверка на яблоко.
     */
    public boolean hasApple() {
        return type == TypeCell.Apple;
    }

    /**
     * Проверка на тело.
     */
    public boolean hasBody() {
        return type == TypeCell.Body;
    }

    /**
     * Проверка на камень.
     */
    public boolean hasStone() {
        return type == TypeCell.Stone;
    }

    /**
     * Класс позиции.
     */
    public class Position {
        private final int x;
        private final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
