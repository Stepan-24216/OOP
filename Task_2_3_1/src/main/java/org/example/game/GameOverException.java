package org.example.game;

/**
 * Исключение при проигрыше.
 */
public class GameOverException extends RuntimeException {
    public GameOverException(String message) {
        super(message);
    }
}