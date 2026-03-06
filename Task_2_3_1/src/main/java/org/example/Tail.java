package org.example;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Tail {
    private int coordX;
    private int coordY;
    private int size;

    public Tail(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.size = 30;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public void paintTail(GraphicsContext gc) {
        // Тело
        gc.setFill(Color.BLUE);
        gc.fillRect(coordX, coordY, size, size);

    }
}
