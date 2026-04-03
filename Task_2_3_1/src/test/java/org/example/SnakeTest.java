package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.config.LevelConfig;
import org.example.config.LevelConfigCreate;
import org.example.map.Cell;
import org.example.map.Map;
import org.example.snake.Snake;
import org.example.snake.Tail;
import org.junit.jupiter.api.Test;


/**
 * Тест методов классов для змейки.
 */

public class SnakeTest {
    /**
     * Тест класса хвоста.
     */
    @Test
    void testTail() {
        Tail tail = new Tail(0, 60);
        assertEquals(0, tail.getCordX());
        assertEquals(60, tail.getCordY());
        tail.setCordX(100);
        tail.setCordY(100);
        assertEquals(100, tail.getCordX());
        assertEquals(100, tail.getCordY());
    }

    /**
     * Тест создания и методов змейки.
     */
    @Test
    void testCreateSnake() {
        LevelConfigCreate configCreate = new LevelConfigCreate();
        LevelConfig config = configCreate.createConfig("src/main/resources/lvl_3.json");
        int gameWidth = config.getSize().getWidth() * 30;
        int gameHeight = config.getSize().getHeight() * 30 + 60;
        Map map = new Map(gameWidth, gameHeight, config.getStones());
        Snake snake = new Snake(gameWidth, gameHeight);
        snake.move(0, 120, map.getCellMap(), gameWidth, map.getOffsetRows());
        assertEquals(0, snake.getCordX());
        assertEquals(120, snake.getCordY());
        assertEquals(1, snake.getTails().size());
        snake.eatApple();
        assertEquals(2, snake.getTails().size());
        assertEquals(1, snake.getScore());
    }

    /**
     * Тест поедания яблока.
     */
    @Test
    void testEatAppleInMap() {
        LevelConfigCreate configCreate = new LevelConfigCreate();
        LevelConfig config = configCreate.createConfig("src/main/resources/lvl_3.json");
        int gameWidth = config.getSize().getWidth() * 30;
        int gameHeight = config.getSize().getHeight() * 30 + 60;
        Map map = new Map(gameWidth, gameHeight, config.getStones());
        map.randomSpawnApple();
        Snake snake = new Snake(gameWidth, gameHeight);
        for (Cell cell : map.getCellMap()) {
            snake.move(cell.getPosition().getCordX(), cell.getPosition().getCordY(), map.getCellMap(),
                map.getgameWidth(),
                map.getOffsetRows());
            if (cell.hasApple()) {
                assertTrue(snake.tryEatApple(map));
                break;
            } else {
                assertFalse(snake.tryEatApple(map));
            }
        }
        assertEquals(2, snake.getTails().size());
        assertFalse(snake.checkDefeat(map));
        snake.move(1000, 1000, map.getCellMap(), map.getgameWidth(),
            map.getOffsetRows());
        assertTrue(snake.checkDefeat(map));
    }
}