package org.example.game;

import java.util.ArrayList;
import java.util.List;
import org.example.map.Map;
import org.example.snake.Snake;

/**
 * Модель игры.
 */
public class GameModel {

    private final Map map;
    private final ArrayList<Snake> snakes;
    /**
     * Список наблюдателей.
     */
    private final List<GameObserver> observers = new ArrayList<>();
    private final int gameGoal;
    private volatile GameState gameState = GameState.PAUSE;

    /**
     * Конструктор.
     */
    public GameModel(Map map, ArrayList<Snake> snakes, int gameGoal) {
        this.map = map;
        this.snakes = snakes;
        this.gameGoal = gameGoal;
    }

    /**
     * Регистрация наблюдателя.
     */
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    /**
     * Уведомление всех наблюдателей об изменении состояния.
     */
    public void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.onGameStateChanged();
        }
    }

    /**
     * Перемещение всех змеек и проверка всех игровых событий.
     */
    public StepResult step(int newX, int newY, Snake snake) {
        int cellSize = 30;

        if (newX < 0 || newX > map.getGameWidth() - cellSize
            || newY < 60 || newY > map.getGameHeight() - cellSize) {
            notifyObservers();
            return new StepResult(GameState.LOSE, "Змейка вышла за пределы игрового поля!");
        }

        snake.move(newX, newY, map.getCellMap(), map.getGameWidth(), map.getOffsetRows());

        if (snake.checkDefeat(map)) {
            notifyObservers();
            return new StepResult(GameState.LOSE, "Змейка врезалась в препятствие!");
        }

        if (snake.tryEatApple(map)) {
            if (snake.getScore() >= gameGoal) {
                notifyObservers();
                return new StepResult(GameState.WIN, null);
            }
            map.randomSpawnApple();
        }

        notifyObservers();
        return new StepResult(GameState.PLAY, null);
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Snake> getSnakes() {
        return snakes;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }

    /**
         * Результат одного игрового шага.
         */
        public record StepResult(GameState state, String message) {
    }
}