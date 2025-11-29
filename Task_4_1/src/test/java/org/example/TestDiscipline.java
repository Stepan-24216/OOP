package org.example;

import static org.example.model.Discipline.COLLOQUIUM;
import static org.example.model.Discipline.CONTROL;
import static org.example.model.Discipline.DIFFERENTIATED_CREDIT;
import static org.example.model.Discipline.EXAM;
import static org.example.model.Discipline.PRACTICE_REPORT;
import static org.example.model.Discipline.PROTECTION_WRK;
import static org.example.model.Discipline.TASK;
import static org.example.model.Discipline.TEST;
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