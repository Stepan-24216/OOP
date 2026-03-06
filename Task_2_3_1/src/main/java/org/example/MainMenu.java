package org.example;

import static java.lang.Thread.sleep;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainMenu {
    Button startButton;
    private volatile boolean buttonPressed;

    public boolean buttonIsNotPressed() {
        return !buttonPressed;
    }

    public void printMainMenu(GraphicsContext gc, StackPane container) {
        gc.drawImage(new Image("file:/home/rend/java/OOP/Task_2_3_1/src/main/resources/menu.png"),0,0);
        this.buttonPressed = false;
        createButton(container);
    }

    public void closeMainMenu(StackPane container) {
        javafx.application.Platform.runLater(() -> {
            container.getChildren().remove(startButton);
        });
    }

    public void createButton(StackPane container) {
        startButton = new Button("НАЧАТЬ ИГРУ");

        startButton.setStyle(
            "-fx-font-size: 24px; " +
                "-fx-font-weight: bold; " +
                "-fx-background-color: #ff0000; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 15 30; " +
                "-fx-background-radius: 10;"
        );

        startButton.setOnAction(event -> {
            closeMainMenu(container);
            System.out.println("Игра началась!");
            buttonPressed = true;
        });

        container.getChildren().add(startButton);
    }
}
//"/home/rend/java/OOP/Task_2_3_1/src/main/resources/menu.png"
