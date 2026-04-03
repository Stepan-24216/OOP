package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.config.LevelConfig;
import org.example.config.LevelConfigCreate;
import org.example.map.Cell;
import org.example.map.Map;
import org.example.map.TypeCell;
import org.junit.jupiter.api.Test;

/**
 * Тест методов классов для создания карты.
 */

public class MapAndConfigTest {

    /**
     * Тест клеток.
     */
    @Test
    void testCell() {
        TypeCell body = TypeCell.Body;
        TypeCell stone = TypeCell.Stone;
        TypeCell apple = TypeCell.Apple;
        TypeCell cell1 = TypeCell.Cell;
        assertEquals(TypeCell.Body, body);
        assertEquals(TypeCell.Stone, stone);
        assertEquals(TypeCell.Apple, apple);
        assertEquals(TypeCell.Cell, cell1);

        Cell cell = new Cell(0, 60, apple);
        assertEquals(0, cell.getPosition().getX());
        assertEquals(60, cell.getPosition().getY());
        assertTrue(cell.hasApple());
        assertFalse(cell.hasBody());
        assertFalse(cell.hasStone());
        assertEquals(apple, cell.getType());
        cell.setType(stone);
        assertEquals(stone, cell.getType());
    }

    /**
     * Тест создания клеток для поля.
     */
    @Test
    void testCreateCellArray() {
        LevelConfigCreate configCreate = new LevelConfigCreate();
        LevelConfig config = configCreate.createConfig("src/main/resources/lvl_3.json");
        int gameWidth = config.getSize().getWidth() * 30;
        int gameHeight = config.getSize().getHeight() * 30 + 60;
        assertEquals(10, config.getSize().getHeight());
        assertEquals(10, config.getSize().getWidth());
        Map map = new Map(gameWidth, gameHeight, config.getStones());
        for (Cell cell : map.getCellMap()) {
            assertEquals(TypeCell.Cell, cell.getType());
        }
    }

    /**
     * Тест спавна яблок.
     */
    @Test
    void testSpawnApple() {
        LevelConfigCreate configCreate = new LevelConfigCreate();
        LevelConfig config = configCreate.createConfig("src/main/resources/lvl_3.json");
        int gameWidth = config.getSize().getWidth() * 30;
        int gameHeight = config.getSize().getHeight() * 30 + 60;
        Map map = new Map(gameWidth, gameHeight, config.getStones());
        map.randomSpawnApple();
        boolean appleFound = false;
        for (Cell cell : map.getCellMap()) {
            if (cell.hasApple()) {
                appleFound = true;
                break;
            }
        }
        assertTrue(appleFound);
    }

    /**
     * Тест геттеров.
     */
    @Test
    void testGetters() {
        LevelConfigCreate configCreate = new LevelConfigCreate();
        LevelConfig config = configCreate.createConfig("src/main/resources/lvl_3.json");
        int gameWidth = config.getSize().getWidth() * 30;
        int gameHeight = config.getSize().getHeight() * 30 + 60;
        Map map = new Map(gameWidth, gameHeight, config.getStones());
        assertEquals(gameWidth, map.getgameWidth());
        assertEquals(gameHeight, map.getgameHeight());
        assertEquals(100, map.getCellMap().size());
        assertEquals(gameWidth / 30, map.getCellsInRow());
        assertEquals(2, map.getOffsetRows());
    }
}