package org.example.view;

import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.game.GameState;
import org.example.snake.Snake;


/**
 * Получение камней на поле.
 */
public class GameEndView {
    /**
     * Окно с обработкой проигрыша.
     */
    public void handleGameOver(String message, Snake snake, Consumer<GameState> onChoice) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Игра окончена");
            alert.setHeaderText("Вы проиграли!");

            alert.setContentText(message + "\nВаш итоговый счет: " + snake.getScore());

            ButtonType menuButton = new ButtonType("В главное меню");
            ButtonType exitButton = new ButtonType("Выйти");
            alert.getButtonTypes().setAll(menuButton, exitButton);

            GameState chosenState = alert.showAndWait()
                .map(response -> response == exitButton ? GameState.EXIT : GameState.MAIN_MENU)
                .orElse(GameState.MAIN_MENU);

            onChoice.accept(chosenState);
        });
    }

    /**
     * Окно с обработкой победы.
     */
    public void handleGameWin(Consumer<GameState> onChoice) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Игра окончена");
            alert.setHeaderText("Вы победили!");

            alert.setContentText("Вы набрали нужное количество очков");

            ButtonType menuButton = new ButtonType("В главное меню");
            ButtonType exitButton = new ButtonType("Выйти");
            alert.getButtonTypes().setAll(menuButton, exitButton);

            GameState chosenState = alert.showAndWait()
                .map(response -> response == exitButton ? GameState.EXIT : GameState.MAIN_MENU)
                .orElse(GameState.MAIN_MENU);

            onChoice.accept(chosenState);
        });
    }
}
