package org.example.game;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.config.LevelConfig;
import org.example.config.LevelConfigCreate;
import org.example.map.Map;
import org.example.snake.Snake;
import org.example.view.GameRenderer;
import org.example.view.MainMenuController;
import org.example.view.ScoreView;

/**
 * Главный класс игры.
 */
public class SnakeGame extends Application {

    private final ArrayList<Snake> snakes = new ArrayList<>();
    private Map map;
    private Canvas canvas;
    private Scene scene;
    private Stage primaryStage;
    private StackPane gameLayer;
    private ScoreView scoreView;
    private GameRenderer gameRenderer;
    private GameController gameController;
    private GameModel gameModel;
    private MainMenuController mainMenuController;

    /**
     * Инициализация уровня.
     */
    public void initLevelDate() {
        LevelConfigCreate configCreate = new LevelConfigCreate();
        String path = mainMenuController.getSelectedLevel().getPath();
        LevelConfig config = configCreate.createConfig(path);

        int cellSize = 30;
        int gameWidth = config.getSize().getWidth() * cellSize;
        int gameHeight = config.getSize().getHeight() * cellSize + 60;

        this.canvas = new Canvas(gameWidth, gameHeight);
        gameLayer.getChildren().clear();
        gameLayer.getChildren().add(canvas);

        map = new Map(gameWidth, gameHeight, config.getStones());
        snakes.clear();
        Snake snake = new Snake(gameWidth, gameHeight);
        snakes.add(snake);

        gameModel = new GameModel(map, snakes, config.getGoal());

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gameRenderer = new GameRenderer(gc, map, snakes);
        scoreView = new ScoreView();

        gameModel.addObserver(gameRenderer);
        gameModel.addObserver(scoreView);

        gameController = new GameController(gameModel, this::returnToMainMenu, this::exitGame);

        gameLayer.requestFocus();
        primaryStage.setWidth(gameWidth);
        primaryStage.setHeight(gameHeight + 60);
        primaryStage.centerOnScreen();
    }

    /**
     * Точка входа JavaFX.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SnakeGame");
        this.primaryStage.setFullScreen(true);

        Parent menuRoot = loadMenuRoot();
        this.scene = new Scene(menuRoot, 600, 600);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    /**
     * Запуск игры из главного меню.
     */
    public void startFromMenu() {
        Platform.runLater(() -> {
            if (canvas != null) {
                return;
            }
            if (mainMenuController == null || mainMenuController.getSelectedLevel() == null) {
                return;
            }

            initLevelDate();
            gameController.setupControls(canvas, scene);
            map.randomSpawnApple();

            scoreView.initScoreLabel(gameLayer, snakes.get(0));

            gameRenderer.paintMap();

            gameController.setGameState(GameState.PLAY);
            gameController.startGameLoop();
        });
    }

    /**
     * Возврат в главное меню.
     */
    public void returnToMainMenu() {
        Platform.runLater(() -> {
            if (gameController != null) {
                gameController.setGameState(GameState.PAUSE);
            }

            canvas = null;
            map = null;
            scoreView = null;
            gameRenderer = null;
            gameController = null;
            gameModel = null;
            snakes.clear();

            Parent menuRoot = loadMenuRoot();
            scene.setRoot(menuRoot);
        });
    }

    /**
     * Корректное завершение приложения.
     */
    public void exitGame() {
        if (gameController != null) {
            gameController.setGameState(GameState.PAUSE);
        }
        primaryStage.close();
        Platform.exit();
    }


    /**
     * Загрузка FXML главного меню.
     */
    private Parent loadMenuRoot() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_menu.fxml"));
            Parent menuRoot = loader.load();

            this.mainMenuController = loader.getController();
            this.mainMenuController.setGame(this);
            this.gameLayer = (StackPane) menuRoot.lookup("#container");

            return menuRoot;
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить главное меню", e);
        }
    }
}