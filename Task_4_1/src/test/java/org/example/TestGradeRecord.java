package org.example;

import static org.example.Score.FIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса GradeRecord.
 */
public class TestGradeRecord {

    @Test
    void testGradeRecordCreation() {
        final GradeRecord record = new GradeRecord(FIVE, LocalDate.of(2024, 1, 15));

        assertEquals(FIVE, record.getScore());
        assertEquals(LocalDate.of(2024, 1, 15), record.getDate());
        assertEquals(5, record.getNumericValue());
    }
}