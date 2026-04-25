package org.example.view;

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
    public GameState handleGameOver(String message, Snake snake) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Игра окончена");
        alert.setHeaderText("Вы проиграли!");

        alert.setContentText(message + "\nВаш итоговый счет: " + snake.getScore());

        ButtonType menuButton = new ButtonType("В главное меню");
        ButtonType exitButton = new ButtonType("Выйти");
        alert.getButtonTypes().setAll(menuButton, exitButton);

        return alert.showAndWait()
            .map(response -> response == exitButton ? GameState.EXIT : GameState.MAIN_MENU)
            .orElse(GameState.MAIN_MENU);
    }

    /**
     * Окно с обработкой победы.
     */
    public GameState handleGameWin() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Игра окончена");
        alert.setHeaderText("Вы победили!");

        alert.setContentText("Вы набрали нужное количество очков");

        ButtonType menuButton = new ButtonType("В главное меню");
        ButtonType exitButton = new ButtonType("Выйти");
        alert.getButtonTypes().setAll(menuButton, exitButton);

        return alert.showAndWait()
            .map(response -> response == exitButton ? GameState.EXIT : GameState.MAIN_MENU)
            .orElse(GameState.MAIN_MENU);
    }
}
