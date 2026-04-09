package org.example.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.snake.Snake;

/**
 * Получение камней на поле.
 */
public class GameEndView {
    public void handleGameOver(String message, Snake snake) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Игра окончена");
        alert.setHeaderText("Вы проиграли!");

        alert.setContentText(message + "\nВаш итоговый счет: " + snake.getScore());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        });
    }

    public void handleGameWin() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Игра окончена");
        alert.setHeaderText("Вы победили!");

        alert.setContentText("Вы набрали нужное количество очков");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        });
    }
}
