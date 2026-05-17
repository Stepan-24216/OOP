package org.example.snake;

/**
 * Направление движения змейки.
 */
public enum Direction {
    UP(0, -30),
    DOWN(0, 30),
    LEFT(-30, 0),
    RIGHT(30, 0);

    private final int deltaX;
    private final int deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public boolean isOpposite(Direction other) {
        return (this == UP && other == DOWN)
            || (this == DOWN && other == UP)
            || (this == LEFT && other == RIGHT)
            || (this == RIGHT && other == LEFT);
    }
}

