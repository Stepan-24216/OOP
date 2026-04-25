package org.example.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.example.game.GameObserver;
import org.example.snake.Snake;

/**
 * Отображение очков змейки.
 */
public class ScoreView implements GameObserver {

    private Label scoreLabel;
    private Snake snake;

    /**
     * Инициализация метки счёта и привязка к наблюдаемой змейке.
     */
    public void initScoreLabel(StackPane gameLayer, Snake snake) {
        this.snake = snake;
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

    /**
     * Читаем счёт и обновляем метку.
     */
    @Override
    public void onGameStateChanged() {
        if (snake != null && scoreLabel != null) {
            Platform.runLater(() -> scoreLabel.setText("Очки: " + snake.getScore()));
        }
    }
}