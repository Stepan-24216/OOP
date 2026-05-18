package org.example.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import org.example.snake.Direction;
import org.example.snake.Snake;
import org.example.view.GameEndView;

/**
 * Контроллер игры.
 */
public class GameController {

    private final GameModel model;
    private final GameEndView gameEndView;
    private final Runnable returnToMenuAction;
    private final Runnable exitAction;
    private static final int INPUT_BUFFER_LIMIT = 3;

    private final Deque<Direction> directionBuffer = new ArrayDeque<>();
    private Direction currentDirection;
    private Timeline gameLoopTimeline;
    private boolean endGameHandled;

    /**
     * Конструктор.
     */
    public GameController(GameModel model, GameEndView gameEndView,
                          Runnable returnToMenuAction, Runnable exitAction) {
        this.model = model;
        this.gameEndView = gameEndView;
        this.returnToMenuAction = returnToMenuAction;
        this.exitAction = exitAction;

        new org.example.snake.GamepadController(this);
    }

    /**
     * Запуск игрового цикла.
     */
    public void startGameLoop() {
        if (model.getGameState() != GameState.PLAY) {
            return;
        }

        if (gameLoopTimeline == null) {
            gameLoopTimeline = new Timeline(new KeyFrame(Duration.millis(150), event -> tick()));
            gameLoopTimeline.setCycleCount(Timeline.INDEFINITE);
        }

        gameLoopTimeline.play();
    }

    /**
     * Один игровой тик.
     */
    private void tick() {
        if (model.getGameState() != GameState.PLAY) {
            return;
        }

        Direction direction = pollNextDirection();
        if (direction != null) {
            currentDirection = direction;
        }

        if (currentDirection == null) {
            return;
        }

        ArrayList<Snake> snakes = model.getSnakes();

        for (Snake snake : snakes) {
            int newX = snake.getHead().getCordX() + currentDirection.getDeltaX();
            int newY = snake.getHead().getCordY() + currentDirection.getDeltaY();

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
        if (model.getGameState() != GameState.PLAY || endGameHandled) {
            return;
        }

        endGameHandled = true;
        stopGameLoop();
        model.setGameState(endState);

        if (endState == GameState.WIN) {
            gameEndView.handleGameWin(this::applyEndAction);
        } else {
            gameEndView.handleGameOver(loseMessage, snake, this::applyEndAction);
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
                handleDirectionInput(Direction.UP);
                break;
            case S:
            case DOWN:
                resumeIfPaused();
                handleDirectionInput(Direction.DOWN);
                break;
            case A:
            case LEFT:
                resumeIfPaused();
                handleDirectionInput(Direction.LEFT);
                break;
            case D:
            case RIGHT:
                resumeIfPaused();
                handleDirectionInput(Direction.RIGHT);
                break;
            case SPACE:
                if (model.getGameState() == GameState.PAUSE) {
                    model.setGameState(GameState.PLAY);
                    startGameLoop();
                } else if (model.getGameState() == GameState.PLAY) {
                    model.setGameState(GameState.PAUSE);
                    stopGameLoop();
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
     * Приём направления движения от любого источника ввода.
     */
    public void handleDirectionInput(Direction direction) {
        enqueueDirection(direction);
    }

    /**
     * Сброс состояния управления перед новым запуском уровня.
     */
    public void resetSession() {
        directionBuffer.clear();
        currentDirection = null;
        endGameHandled = false;
        if (gameLoopTimeline != null) {
            gameLoopTimeline.stop();
        }
    }

    /**
     * Выполнение действия после выбора в окне окончания игры.
     */
    private void applyEndAction(GameState action) {
        if (action == GameState.EXIT) {
            exitAction.run();
            return;
        }

        if (returnToMenuAction != null) {
            returnToMenuAction.run();
        }
    }

    /**
     * Остановка игрового цикла.
     */
    private void stopGameLoop() {
        if (gameLoopTimeline != null) {
            gameLoopTimeline.stop();
        }
    }

    /**
     * Добавление направления в буфер.
     */
    private void enqueueDirection(Direction direction) {
        Direction referenceDirection = directionBuffer.peekLast();
        if (referenceDirection == null) {
            referenceDirection = currentDirection;
        }

        if (referenceDirection != null && direction.isOpposite(referenceDirection)) {
            return;
        }

        if (directionBuffer.isEmpty() && direction == currentDirection) {
            return;
        }

        if (!directionBuffer.isEmpty() && directionBuffer.peekLast() == direction) {
            return;
        }

        if (directionBuffer.size() >= INPUT_BUFFER_LIMIT) {
            return;
        }

        directionBuffer.addLast(direction);
    }

    /**
     * Взятие следующего направления из буфера.
     */
    private Direction pollNextDirection() {
        return directionBuffer.pollFirst();
    }

    /**
     * Установка состояния модели.
     */
    public synchronized void setGameState(GameState state) {
        model.setGameState(state);
    }
}