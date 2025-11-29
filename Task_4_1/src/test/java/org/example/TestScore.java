package org.example;

import static org.example.Score.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Score.
 */
public class TestScore {

    @Test
    void testScoreValues() {
        assertEquals(2, TWO.getNumericValue());
        assertEquals(3, THREE.getNumericValue());
        assertEquals(4, FOUR.getNumericValue());
        assertEquals(5, FIVE.getNumericValue());
        assertNull(PASS.getNumericValue());
        assertNull(FAIL.getNumericValue());
    }

    @Test
    void testScoreDescriptions() {
        assertEquals("Неудовлетворительно", TWO.getDescription());
        assertEquals("Удовлетворительно", THREE.getDescription());
        assertEquals("Хорошо", FOUR.getDescription());
        assertEquals("Отлично", FIVE.getDescription());
        assertEquals("Зачёт", PASS.getDescription());
        assertEquals("Незачёт", FAIL.getDescription());
    }
}