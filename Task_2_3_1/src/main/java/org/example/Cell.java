package org.example;

public class Cell {
    private Position position;
    private boolean haveApple;
    private boolean haveBody;

    public Cell(int x, int y) {
        this.position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }

    public boolean hasApple() {
        return haveApple;
    }

    public void setHaveApple(boolean haveApple) {
        this.haveApple = haveApple;
    }

    public boolean hasBody() {
        return haveBody;
    }

    public void setHaveBody(boolean haveBody) {
        this.haveBody = haveBody;
    }

    class Position {
        private int x;
        private int y;

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
