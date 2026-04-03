package org.example;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import org.example.map.Cell;
import org.example.map.Map;
import org.example.snake.GamepadController;
import org.example.snake.Snake;
import org.example.view.GameRenderer;
import org.example.view.ScoreView;

/**
 * Контролер игры.
 */
public class GameController {
    private final Map map;
    private final ArrayList<Snake> snakes;
    private final GameRenderer gameRenderer;
    private final ScoreView scoreView;
    private final int gameWidth;
    private final int gameHeight;
    private final ArrayList<Cell> cellMap;
    private final GamepadController gamepadController;
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private volatile boolean gamePaused = true;

    /**
     * Конструктор.
     */
    public GameController(Map map, ArrayList<Snake> snakes, GameRenderer gameRenderer,
                          ScoreView scoreView) {
        this.map = map;
        this.snakes = snakes;
        this.gameRenderer = gameRenderer;
        this.scoreView = scoreView;
        this.gameWidth = map.getGameWidth();
        this.gameHeight = map.getGameHeight();
        this.cellMap = map.getCellMap();
        this.gamepadController = new GamepadController(this);
    }

    /**
     * Начало цикла игры.
     */
    public void startGameLoop() {

        Thread gameThread = new Thread(() -> {
            while (!gamePaused) {
                Platform.runLater(this::updateGame);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        gameThread.start();
    }

    /**
     * Обновление игры.
     */
    private void updateGame() {
        for (Snake snake : snakes) {
            int speed = 30; // Пикселей за шаг
            int snakeX = snake.getHead().getCordX();
            int snakeY = snake.getHead().getCordY();
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

            int cellSize = 30;
            if (snakeX < 0 || snakeX > gameWidth - cellSize) {
                System.exit(0);
            }
            if (snakeY < 60 || snakeY > gameHeight - cellSize) {
                System.exit(0);
            }
            updateLogic();
            snake.move(snakeX, snakeY, cellMap, gameWidth, map.getOffsetRows());

            gameRenderer.paintMap();
        }
    }

    /**
     * Получение информации о событиях на карте.
     */
    private void updateLogic() {
        for (Snake snake : snakes) {
            if (checkCollision(snake)) {
                System.out.println("Игра окончена!");
                System.exit(0);
                return;
            }
            if (snake.tryEatApple(map)) {
                scoreView.updateScore(snake.getScore());
                map.randomSpawnApple();
            }
            snake.tryEatApple(map);
        }
    }

    /**
     * Начинаем считывать тыканья по клавишам.
     */
    public void setupControls(Canvas canvas, Scene scene) {
        canvas.setFocusTraversable(true);
        canvas.requestFocus();
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
    }

    /**
     * Создание событий для нажатия определённых клавиш.
     */
    public void handleKeyPress(KeyCode e) {
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
                if (!gamePaused) {
                    startGameLoop();
                }
                break;
            case ESCAPE:
                System.exit(0);
                break;
            default:
                break;
        }
    }

    /**
     * Выключаем паузу.
     */
    private void offPauseInGame() {
        if (gamePaused) {
            gamePaused = false;
            startGameLoop();
            if (gamepadController.isGamepadConnected()) {
                gamepadController.startGamepadThread();
            }
        }
    }

    /**
     * Получение информации о состоянии паузы.
     */
    public synchronized boolean getGamePaused() {
        return gamePaused;
    }

    /**
     * Установка состояния паузы.
     */
    public synchronized void setGamePaused(boolean state) {
        this.gamePaused = state;
    }

    /**
     * Получаем инфу о жизни и здоровье змейки.
     */
    private boolean checkCollision(Snake snake) {
        return snake.checkDefeat(map);
    }
}
