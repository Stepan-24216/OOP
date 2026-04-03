package org.example.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Отображение очков змейки.
 */
public class ScoreView {
    private Label scoreLabel;

    public void initScoreLabel(StackPane gameLayer) {
        this.scoreLabel = new Label("Очки: 0");
        this.scoreLabel.setStyle(
            "-fx-font-size: 40px; "
                + "-fx-text-fill: #39FF14; "
                + "-fx-font-weight: bold; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0);"
        );

        StackPane.setAlignment(scoreLabel, Pos.TOP_CENTER);
        StackPane.setMargin(scoreLabel, new Insets(5, 0, 0, 0));

        gameLayer.getChildren().add(scoreLabel);
    }

    public void updateScore(int score) {
        Platform.runLater(() -> {
            scoreLabel.setText("Очки: " + score);
        });
    }
}
