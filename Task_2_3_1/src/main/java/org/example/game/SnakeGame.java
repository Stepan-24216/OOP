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
 * Главный класс нашей игры.
 */
public class SnakeGame extends Application {
    private final ArrayList<Snake> snakes = new ArrayList<>();
    private Map map;
    private Canvas canvas;
    private Scene scene;
    private Stage primaryStage;
    private StackPane gameLayer;
    private ScoreView score;
    private GameRenderer gameRenderer;
    private GameController gameController;
    private MainMenuController mainMenuController;

    /**
     * Инициализируем нужные нам классы и присваиваем.
     */
    public void initLevelDate() {
        LevelConfigCreate configCreate = new LevelConfigCreate();
        String path = mainMenuController.getSelectedLevel().getPath();
        LevelConfig config = configCreate.createConfig(path);
        score = new ScoreView();
        int cellSize = 30;
        int gameWidth = config.getSize().getWidth() * cellSize;
        int gameHeight = config.getSize().getHeight() * cellSize + 60;

        this.canvas = new Canvas(gameWidth, gameHeight);

        gameLayer.getChildren().clear();
        gameLayer.getChildren().add(canvas);

        map = new Map(gameWidth, gameHeight, config.getStones());
        Snake snake = new Snake(gameWidth, gameHeight);
        snakes.add(snake);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gameRenderer = new GameRenderer(gc, map, snakes);
        gameController = new GameController(map, snakes, gameRenderer, score, config.getGoal());
        gameLayer.requestFocus();

        primaryStage.setWidth(gameWidth);
        primaryStage.setHeight(gameHeight + 60);
        primaryStage.centerOnScreen();
    }

    /**
     * Метод с которого начинается игра, устанавливаем нужные нам конфигурации.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SnakeGame");
        this.primaryStage.setFullScreen(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_menu.fxml"));
        Parent menuRoot = loader.load();

        this.mainMenuController = loader.getController();
        this.mainMenuController.setGame(this);

        this.gameLayer = (StackPane) menuRoot.lookup("#container");

        this.scene = new Scene(menuRoot, 600, 600);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

        startFromMenu();
    }

    /**
     * Метод загрузки главного меню и начала игры.
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
            gameRenderer.paintMap();
            score.initScoreLabel(gameLayer);

            gameController.setGameState(GameState.PLAY);
            gameController.startGameLoop();
        });
    }
}
