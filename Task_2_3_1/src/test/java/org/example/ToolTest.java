package org.example;

import org.example.config.LevelConfig;
import org.example.config.LevelConfigCreate;
import org.example.map.Map;
import org.example.tool.LevelPath;
import org.example.tool.SearchLevelInDir;
import org.example.tool.Tools;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тест вспомогательных инструментов.
 */
public class ToolTest {
    /**
     * Тест инструмента вычисления клетки по кордам.
     */
    @Test
    void testTools() {
        LevelConfigCreate configCreate = new LevelConfigCreate();
        LevelConfig config = configCreate.createConfig("src/main/resources/lvl_3.json");
        int GAME_WIDTH = config.getSize().getWidth() * 30;
        int GAME_HEIGHT = config.getSize().getHeight() * 30 + 60;
        Map map = new Map(GAME_WIDTH, GAME_HEIGHT, config.getStones());
        Tools.getCellIndex(map, 60, 60);
        assertEquals(2, Tools.getCellIndex(map, 60, 60));
    }

    /**
     * Тест класса пути к файлу.
     */
    @Test
    void testLevelPath() {
        LevelPath levelPath = new LevelPath("123","456");
        assertEquals("123", levelPath.getNameFile());
        assertEquals("456", levelPath.getPath());
    }

    /**
     * Тест поиска файлов подходящих нам по шаблону имени.
     */
    @Test
    void testSearchLevelPath() {
        SearchLevelInDir searchLevelInDir = new SearchLevelInDir();
        assertEquals(3, searchLevelInDir.searchLevelInDir("src/main/resources").size());
    }
}
