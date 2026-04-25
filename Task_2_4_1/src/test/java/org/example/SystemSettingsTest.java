package org.example;

import org.example.domain.SystemSettings;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SystemSettingsTest {

    @Test
    void storesGradeThresholdsAndBonuses() {
        SystemSettings settings = new SystemSettings();
        settings.setGradeThreshold("5", 85);
        settings.setGradeThreshold("4", 65);
        settings.setBonus("efimov", "Task_1_1", 5);

        Map<String, Integer> thresholds = settings.getGradeThresholds();

        assertEquals(85, thresholds.get("5"));
        assertEquals(65, thresholds.get("4"));
        assertEquals(5, settings.getBonusFor("efimov", "Task_1_1"));
        assertEquals(0, settings.getBonusFor("efimov", "Task_1_2"));
    }
}

