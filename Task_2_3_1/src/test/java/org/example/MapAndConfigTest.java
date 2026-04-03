package org.example;

import org.example.config.LevelConfig;
import org.example.config.LevelConfigCreate;
import org.example.map.Cell;
import org.example.map.Map;
import org.example.map.TypeCell;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Тест методов классов для создания карты.
 */

public class MapAndConfigTest {

    /**
     * Тест клеток.
     */
    @Test
    void testCell() {
        TypeCell Body = TypeCell.Body;
        TypeCell Stone = TypeCell.Stone;
        TypeCell Apple = TypeCell.Apple;
        TypeCell cell1 = TypeCell.Cell;
        assertEquals(TypeCell.Body, Body);
        assertEquals(TypeCell.Stone, Stone);
        assertEquals(TypeCell.Apple, Apple);
        assertEquals(TypeCell.Cell, cell1);

        Cell cell = new Cell(0,60, Apple);
        assertEquals(0, cell.getPosition().getX());
        assertEquals(60, cell.getPosition().getY());
        assertTrue(cell.hasApple());
        assertFalse(cell.hasBody());
        assertFalse(cell.hasStone());
        assertEquals(Apple, cell.getType());
        cell.setType(Stone);
        assertEquals(Stone, cell.getType());
    }

    /**
     * Тест создания клеток для поля.
     */
    @Test
    void testCreateCellArray() {
        LevelConfigCreate configCreate = new LevelConfigCreate();
        LevelConfig config = configCreate.createConfig("src/main/resources/lvl_3.json");
        int GAME_WIDTH = config.getSize().getWidth() * 30;
        int GAME_HEIGHT = config.getSize().getHeight() * 30 + 60;
        assertEquals(10,config.getSize().getHeight());
        assertEquals(10,config.getSize().getWidth());
        Map map = new Map(GAME_WIDTH, GAME_HEIGHT, config.getStones());
        for (Cell cell: map.getCellMap()) {
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
        int GAME_WIDTH = config.getSize().getWidth() * 30;
        int GAME_HEIGHT = config.getSize().getHeight() * 30 + 60;
        Map map = new Map(GAME_WIDTH, GAME_HEIGHT, config.getStones());
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
        int GAME_WIDTH = config.getSize().getWidth() * 30;
        int GAME_HEIGHT = config.getSize().getHeight() * 30 + 60;
        Map map = new Map(GAME_WIDTH, GAME_HEIGHT, config.getStones());
        assertEquals(GAME_WIDTH, map.getGAME_WIDTH());
        assertEquals(GAME_HEIGHT, map.getGAME_HEIGHT());
        assertEquals(100, map.getCellMap().size());
        assertEquals(GAME_WIDTH/30, map.getCellsInRow());
        assertEquals(2, map.getOffsetRows());
    }
}