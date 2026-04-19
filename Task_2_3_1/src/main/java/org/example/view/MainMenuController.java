package org.example.view;

import java.io.File;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import org.example.game.SnakeGame;
import org.example.tool.LevelPath;
import org.example.tool.SearchLevelInDir;

/**
 * Настройки главного меню.
 */
public class MainMenuController {
    @FXML
    private StackPane container;
    @FXML
    private ComboBox<String> levelComboBox;
    @FXML
    private Label errorLabel;

    private List<LevelPath> levels;
    private LevelPath selectedLevel;
    private SnakeGame game;

    /**
     * Обработка нажатия на кнопку.
     */
    @FXML
    private void handleBrowseDirectory() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Выберите папку с уровнями");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));

        File selectedDir = chooser.showDialog(container.getScene().getWindow());
        if (selectedDir == null) {
            return;
        }

        loadLevelsFromDirectory(selectedDir.getAbsolutePath());
    }

    /**
     * Загрузка списка уровней.
     */
    private void loadLevelsFromDirectory(String dirPath) {
        try {
            levels = SearchLevelInDir.searchLevelInDir(dirPath);
            List<String> levelNames = levels.stream().map(LevelPath::getNameFile).toList();
            levelComboBox.setItems(FXCollections.observableArrayList(levelNames));
            levelComboBox.getSelectionModel().clearSelection();
            selectedLevel = null;

            if (levelNames.isEmpty()) {
                errorLabel.setText("В выбранной папке нет файлов вида lvl_*.json");
                errorLabel.setVisible(true);
            } else {
                errorLabel.setVisible(false);
            }
        } catch (Exception e) {
            levels = Collections.emptyList();
            levelComboBox.getItems().clear();
            selectedLevel = null;
            errorLabel.setText("Не удалось прочитать директорию: " + e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    /**
     * Обработка нажатия на кнопку.
     */
    @FXML
    public void initialize() {
        try {
            Image bgImage = new Image(getClass().getResourceAsStream("/menu.png"));

            BackgroundImage backgroundImage = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
            );

            container.setBackground(new javafx.scene.layout.Background(backgroundImage));

        } catch (Exception e) {
            container.setStyle("-fx-background-color: darkslategray;");
            System.err.println("Не удалось загрузить фон: " + e.getMessage());
        }
        levels =
            SearchLevelInDir.searchLevelInDir("src/main/resources");

        List<String> levelNames = levels.stream().map(LevelPath::getNameFile).toList();
        levelComboBox.setItems(FXCollections.observableArrayList(levelNames));

        levelComboBox.setOnAction(event -> {
            String selectedName = levelComboBox.getValue();
            selectedLevel = levels.stream()
                .filter(l -> l.getNameFile().equals(selectedName))
                .findFirst()
                .orElse(null);
            errorLabel.setVisible(false);
        });
    }

    /**
     * Обработка нажатия на кнопку.
     */
    @FXML
    private void handleStartGame() {
        if (selectedLevel == null) {
            errorLabel.setText("Выберите уровень!");
            errorLabel.setVisible(true);
            return;
        }

        errorLabel.setVisible(false);
        System.out.println("Игра началась: " + selectedLevel.getNameFile());

        if (game != null) {
            game.startFromMenu();
        }
    }

    /**
     * Получение выбранного уровня.
     */
    public LevelPath getSelectedLevel() {
        return selectedLevel;
    }

    /**
     * Присвоение игры для меню.
     */
    public void setGame(SnakeGame game) {
        this.game = game;
    }
}