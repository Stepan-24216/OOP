package org.example.game;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import org.example.snake.GamepadController;
import org.example.snake.Snake;
import org.example.view.GameEndView;

/**
 * Контроллер игры
 */
public class GameController {

    private final GameModel model;
    private final GamepadController gamepadController;
    private final GameEndView gameEndView;
    private final Runnable returnToMenuAction;
    private final Runnable exitAction;

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    /**
     * Конструктор.
     */
    public GameController(GameModel model, Runnable returnToMenuAction, Runnable exitAction) {
        this.model = model;
        this.gamepadController = new GamepadController(this);
        this.gameEndView = new GameEndView();
        this.returnToMenuAction = returnToMenuAction;
        this.exitAction = exitAction;
    }

    /**
     * Запуск игрового цикла.
     */
    public void startGameLoop() {
        if (model.getGameState() != GameState.PLAY) {
            return;
        }

        Thread gameThread = new Thread(() -> {
            while (model.getGameState() == GameState.PLAY) {
                Platform.runLater(this::tick);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        gameThread.setDaemon(true);
        gameThread.start();
    }

    /**
     * Один игровой тик.
     */
    private void tick() {
        if (model.getGameState() != GameState.PLAY) {
            return;
        }

        ArrayList<Snake> snakes = model.getSnakes();

        for (Snake snake : snakes) {
            int speed = 30;
            int newX = snake.getHead().getCordX();
            int newY = snake.getHead().getCordY();

            if (upPressed) {
                newY -= speed;
            }
            if (downPressed) {
                newY += speed;
            }
            if (leftPressed) {
                newX -= speed;
            }
            if (rightPressed) {
                newX += speed;
            }

            GameModel.StepResult result = model.step(newX, newY, snake);

            if (result.state() == GameState.LOSE) {
                finishGame(GameState.LOSE, result.message(), snake);
                return;
            }
            if (result.state() == GameState.WIN) {
                finishGame(GameState.WIN, null, snake);
                return;
            }
        }
    }

    /**
     * Окончание игры.
     */
    private void finishGame(GameState endState, String loseMessage, Snake snake) {
        if (model.getGameState() != GameState.PLAY) {
            return;
        }

        model.setGameState(endState);

        GameState action = (endState == GameState.WIN)
            ? gameEndView.handleGameWin()
            : gameEndView.handleGameOver(loseMessage, snake);

        if (action == GameState.EXIT) {
            exitAction.run();
            return;
        }

        if (returnToMenuAction != null) {
            returnToMenuAction.run();
        }
    }

    /**
     * Привязка обработчика клавиш к сцене.
     */
    public void setupControls(Canvas canvas, Scene scene) {
        canvas.setFocusTraversable(true);
        canvas.requestFocus();
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
    }

    /**
     * Обработка нажатий клавиш.
     */
    public void handleKeyPress(KeyCode e) {
        switch (e) {
            case W:
            case UP:
                resumeIfPaused();
                if (!downPressed) {
                    upPressed = true;
                    leftPressed = false;
                    rightPressed = false;
                }
                break;
            case S:
            case DOWN:
                resumeIfPaused();
                if (!upPressed) {
                    downPressed = true;
                    leftPressed = false;
                    rightPressed = false;
                }
                break;
            case A:
            case LEFT:
                resumeIfPaused();
                if (!rightPressed) {
                    leftPressed = true;
                    upPressed = false;
                    downPressed = false;
                }
                break;
            case D:
            case RIGHT:
                resumeIfPaused();
                if (!leftPressed) {
                    rightPressed = true;
                    upPressed = false;
                    downPressed = false;
                }
                break;
            case SPACE:
                if (model.getGameState() == GameState.PAUSE) {
                    model.setGameState(GameState.PLAY);
                    startGameLoop();
                } else if (model.getGameState() == GameState.PLAY) {
                    model.setGameState(GameState.PAUSE);
                }
                break;
            case ESCAPE:
                exitAction.run();
                break;
            default:
                break;
        }
    }

    /**
     * Снимаем паузу при нажатии кнопки движения.
     */
    private void resumeIfPaused() {
        if (model.getGameState() == GameState.PAUSE) {
            model.setGameState(GameState.PLAY);
            startGameLoop();
        }
    }

    /**
     * Установка состояния модели.
     */
    public synchronized void setGameState(GameState state) {
        model.setGameState(state);
    }
}