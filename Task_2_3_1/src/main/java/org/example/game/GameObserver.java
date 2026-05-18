package org.example.game;

/**
 * Интерфейс наблюдателя за состоянием игры.
 */
public interface GameObserver {

    /**
     * Вызывается моделью при изменении состояния карты или змейки.
     */
    void onGameStateChanged();
}