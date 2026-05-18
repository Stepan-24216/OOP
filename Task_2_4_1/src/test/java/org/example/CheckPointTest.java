package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import org.example.domain.CheckPoint;
import org.junit.jupiter.api.Test;

class CheckPointTest {

    @Test
    void constructor_normalizesNullNameToEmptyString() {
        CheckPoint checkPoint = new CheckPoint(null, LocalDate.of(2026, 3, 1));

        assertEquals("", checkPoint.name());
        assertEquals(LocalDate.of(2026, 3, 1), checkPoint.date());
    }

    @Test
    void constructor_keepsNullDate() {
        CheckPoint checkPoint = new CheckPoint("CP", null);

        assertEquals("CP", checkPoint.name());
        assertNull(checkPoint.date());
    }
}

