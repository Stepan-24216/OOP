package org.example;

public class Cell {
    private final Position position;
    private TypeCell type;
    
    public Cell(int x, int y,TypeCell type) {
        this.position = new Position(x, y);
        this.type = type;
    }
    
    public void setType(TypeCell type) {
        this.type = type;
    }
    
    public Position getPosition() {
        return position;
    }

    public boolean hasApple() {
        return type == TypeCell.Apple;
    }
    
    public boolean hasBody() {
        return type == TypeCell.Body;
    }

    public boolean hasStone() {
        return type == TypeCell.Stone;
    }
    
    class Position {
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
