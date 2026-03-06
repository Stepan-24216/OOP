package org.example;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SnakeGame extends Application {
    private final int GAME_WIDTH = 600;
    private final int GAME_HEIGHT = 660;
    private final int cellSize = 30;
    private ArrayList<Cell> cellMap;
    public volatile boolean upPressed, downPressed, leftPressed, rightPressed;
    private Snake snake;
    private final Map map;
    ArrayList <Snake> snakes = new ArrayList<>();
    Canvas canvas;
    GraphicsContext gc;
    VBox root;
    Scene scene;
    Stage primaryStage;
    StackPane gameLayer;
    MainMenu mainMenu;
    private static Label scoreLabel;
    private boolean gamePaused;
    private GamepadController gamepadController;

    public SnakeGame() {
        this.map = new Map(GAME_WIDTH, GAME_HEIGHT, cellSize);
        this.canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        this.gc = canvas.getGraphicsContext2D();
        this.gamePaused = false;
        this.root = new VBox(10);
        this.scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SnakeGame");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
        this.primaryStage.setResizable(false);
        // В конструкторе или методе start:
        this.gameLayer = new StackPane();
        gameLayer.getChildren().add(canvas); // Холст на нижнем слое
        root.getChildren().add(gameLayer);   // Добавляем этот "слоеный пирог" в главный VBox

        setupControls(canvas, scene);
        cellMap = map.createCellMap(GAME_WIDTH, GAME_HEIGHT);
        this.snake = new Snake();
        snakes.add(snake);
        this.gamepadController = new GamepadController();

        MainMenu mainMenu = new MainMenu();
        mainMenu.printMainMenu(gc, gameLayer);
        Thread waitThread = new Thread(() -> {
            while (mainMenu.buttonIsNotPressed()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }javafx.application.Platform.runLater(() -> {
                mainMenu.closeMainMenu(gameLayer);
                randomSpawnApple(gc);
                map.paintMap(gc,snakes);
                initScoreLabel(gameLayer);
                startGameLoop(gc);
            });
        });
        waitThread.setDaemon(true);
        waitThread.start();
    }

    private void startGameLoop(GraphicsContext gc) {

        Thread gameThread = new Thread(() -> {
            while (!gamePaused) {
                updateGame(gc,snake);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        gameThread.start();
    }

    private void setupControls(Canvas canvas, Scene scene) {
        canvas.setFocusTraversable(true);
        canvas.requestFocus();
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
    }

    private void handleKeyPress(KeyCode e) {
        switch (e) {
            case W:
            case UP:
                offPauseInGame();
                if (downPressed) {
                    break;
                }
                upPressed = true;
                leftPressed = false;
                rightPressed = false;
                break;
            case S:
            case DOWN:
                offPauseInGame();
                if (upPressed) {
                    break;
                }
                downPressed = true;
                leftPressed = false;
                rightPressed = false;
                break;
            case A:
            case LEFT:
                offPauseInGame();
                if (rightPressed) {
                    break;
                }
                leftPressed = true;
                upPressed = false;
                downPressed = false;
                break;
            case D:
            case RIGHT:
                offPauseInGame();
                if (leftPressed) {
                    break;
                }
                rightPressed = true;
                upPressed = false;
                downPressed = false;
                break;
            case SPACE:
                gamePaused = !gamePaused;
                if (!gamePaused){
//                    MainMenu.closeMainMenu(gameLayer);
                    startGameLoop(gc);
                } else {
//                    MainMenu.printMainMenu(gc,gameLayer);
                }
                break;
            case ESCAPE:
                System.exit(0);
                break;
        }
    }

    private void offPauseInGame() {
        if (gamePaused) {
            gamePaused = false;
            startGameLoop(gc);
        }
    }

    private void randomSpawnApple(GraphicsContext gc) {
        boolean flag = true;
        while (flag) {
            int randomIndex = (int) (Math.random() * cellMap.size());
            if (!cellMap.get(randomIndex).hasApple() && !cellMap.get(randomIndex).hasBody()) {
                cellMap.get(randomIndex).setHaveApple(true);
                map.paintApple(gc, cellMap.get(randomIndex).getPosition().getX(), cellMap.get(randomIndex).getPosition().getY(),
                    cellSize);
                flag = false;
            }
        }
    }

    private void updateGame(GraphicsContext gc, Snake snake) {
        // Обновляем состояние геймпада
        gamepadController.updateGamepadState();

        // Проверяем ввод с геймпада в дополнение к клавиатуре
//        if (gamepadController.isUpPressed()) upPressed = true;
//        if (gamepadController.isDownPressed()) downPressed = true;
//        if (gamepadController.isLeftPressed()) leftPressed = true;
//        if (gamepadController.isRightPressed()) rightPressed = true;

        int speed = 30; // Пикселей за шаг
        int snakeX = snake.getHead().getCoordX();
        int snakeY = snake.getHead().getCoordY();
        if (upPressed) {
            snakeY -= speed;
        }
        if (downPressed) {
            snakeY += speed;
        }
        if (leftPressed) {
            snakeX -= speed;
        }
        if (rightPressed) {
            snakeX += speed;
        }

        if (snakeX < 0 || snakeX > GAME_WIDTH - cellSize){
            System.exit(0);
        }
        if (snakeY < 60 || snakeY > GAME_HEIGHT - cellSize){
            System.exit(0);
        }

        snake.move(snakeX, snakeY, cellMap, GAME_WIDTH,map.getOffsetRows());
        // Перерисовываем экран
        map.paintMap(gc,snakes);
    }

    public static void updateScore(int score) {
        // Platform.runLater нужен, если ты вызываешь это из другого потока (Thread)
        javafx.application.Platform.runLater(() -> {
            scoreLabel.setText("Очки: " + score);
        });
    }

    public void initScoreLabel(StackPane gameLayer) {
        this.scoreLabel = new Label("Очки: 0");
        this.scoreLabel.setStyle(
            "-fx-font-size: 40px; " +
                "-fx-text-fill: #39FF14; " + // Неоновый зеленый
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0);"
        );

        // Располагаем сверху по центру
        StackPane.setAlignment(scoreLabel, Pos.TOP_CENTER);
        StackPane.setMargin(scoreLabel, new Insets(5, 0, 0, 0));

        gameLayer.getChildren().add(scoreLabel);
    }

    @Override
    public void stop() {
        if (gamepadController != null) {
            gamepadController.shutdown();
        }
    }
}
