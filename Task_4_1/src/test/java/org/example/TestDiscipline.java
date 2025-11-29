package org.example;

import static org.example.model.Discipline.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Discipline.
 */
public class TestDiscipline {

    @Test
    void testDisciplineNames() {
        assertEquals("Задание", TASK.getDisciplineName());
        assertEquals("Контрольная работа", CONTROL.getDisciplineName());
        assertEquals("Коллоквиум", COLLOQUIUM.getDisciplineName());
        assertEquals("Экзамен", EXAM.getDisciplineName());
        assertEquals("Дифференцированный зачет", DIFFERENTIATED_CREDIT.getDisciplineName());
        assertEquals("Зачет", TEST.getDisciplineName());
        assertEquals("Отчет по практике", PRACTICE_REPORT.getDisciplineName());
        assertEquals("Защита ВКР", PROTECTION_WRK.getDisciplineName());
    }
}