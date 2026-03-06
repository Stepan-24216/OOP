package org.example;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Snake {
    private volatile int score;
    private Tail head;
    private final int SNAKE_SIZE = 30;
    ArrayList<Tail> tails;

    public Snake() {
        this.score = 0;
        this.head = new Tail(300, 300);
        this.tails = new ArrayList<>();
        this.tails.add(head);
    }

    public void move(int coordX, int coordY, ArrayList<Cell> cellMap, int GAME_WIDTH, int offsetRows) {
        int SNAKE_SIZE = 30;
        int tailX = tails.get(tails.size() - 1).getCoordX();
        int tailY = tails.get(tails.size() - 1).getCoordY();
        int cellsInRow = GAME_WIDTH / SNAKE_SIZE;

        int tailIndex = (tailY / SNAKE_SIZE - offsetRows) * cellsInRow + (tailX / SNAKE_SIZE);
        if (tailIndex >= 0 && tailIndex < cellMap.size()) {
            cellMap.get(tailIndex).setHaveBody(false);
        }

        if (tails.size() > 1) {
            for (int i = tails.size() - 1; i > 0; i--) {
                tails.get(i).setCoordX(tails.get(i - 1).getCoordX());
                tails.get(i).setCoordY(tails.get(i - 1).getCoordY());
                if (i <= tails.size() - 2) {
                    int idx = (tails.get(i).getCoordY() / SNAKE_SIZE - offsetRows) * cellsInRow
                        + (tails.get(i).getCoordX() / SNAKE_SIZE);
                    if (idx >= 0 && idx < cellMap.size()) {
                        cellMap.get(idx).setHaveBody(true);
                    }
                }
            }
        }

        head.setCoordX(coordX);
        head.setCoordY(coordY);
    }

    public void eatApple() {
        score++;
        Tail newTail = new Tail(tails.get(tails.size() - 1).getCoordX(), tails.get(tails.size() - 1).getCoordY());
        tails.add(newTail);
    }

    public void deleteAppleIfEaten(GraphicsContext gc, Map map, ArrayList<Cell> cellMap) {
        int headIndex = Tools.getCellIndex(map,this.getCoordX(), this.getCoordY());
        if (headIndex < 0 || headIndex >= cellMap.size()) return;

        if (cellMap.get(headIndex).hasApple()) {
            cellMap.get(headIndex).setHaveApple(false);
            this.eatApple();
            SnakeGame.updateScore(this.getScore());
            map.randomSpawnApple(gc);
        }
    }

    public void paintSnake(GraphicsContext gc) {
        paintHead(gc, head.getCoordX(), head.getCoordY(), SNAKE_SIZE);
        for (Tail tail : tails.subList(1, tails.size())) {
            tail.paintTail(gc);
        }
    }

    private void paintHead(GraphicsContext gc, int x, int y, int size) {
        // Тело
        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, size, size);

        // Глаза
        gc.setFill(Color.MAGENTA);
        gc.fillRect(x + 4, y + 6, 6, 6);    // Левый глаз
        gc.fillRect(x + 20, y + 6, 6, 6);   // Правый глаз

        // Улыбка (опционально)
        gc.setFill(Color.RED);
        gc.fillArc(x + 5, y + 15, 20, 10, 0, -180, javafx.scene.shape.ArcType.ROUND);
    }

    public void defeatIfBody(Map map, ArrayList<Cell> cellMap) {
        int headIndex = Tools.getCellIndex(map,getCoordX(), getCoordY());
        if (headIndex < 0 || headIndex >= cellMap.size()) return;

        if (cellMap.get(headIndex).hasBody()) {
            System.exit(1);
        }
    }

    public int getScore() {
        return score;
    }

    public int getSizeSnake() {
        return SNAKE_SIZE;
    }
    
    public Tail getHead(){
        return head;
    }
    
    public int getCoordX(){
        return head.getCoordX();
    }
    
    public int getCoordY(){
        return head.getCoordY();
    }

}
