package org.example;

import static org.example.Discipline.EXAM;
import static org.example.Discipline.TASK;
import static org.example.Score.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Semester.
 */
public class TestSemester {

    @Test
    void testSemesterBasic() {
        final Semester semester = new Semester(LocalDate.of(2024, 1, 1));

        assertEquals(LocalDate.of(2024, 1, 1), semester.getSemesterTime());
        assertTrue(semester.getDisciplines().isEmpty());
        assertNotNull(semester.getSession());
        assertEquals(0, semester.getSemesterScoreSum());
        assertEquals(0, semester.getPassedSubjectsCount());
    }

    @Test
    void testSemesterWithDisciplines() {
        final Semester semester = new Semester(LocalDate.of(2024, 1, 1));

        semester.addDiscipline(EXAM, true);
        semester.addDiscipline(TASK, false);
        semester.addGradeToDiscipline(EXAM, FIVE, LocalDate.of(2024, 1, 20));
        semester.addGradeToDiscipline(TASK, FOUR, LocalDate.of(2024, 1, 15));

        assertEquals(1, semester.getDisciplines().size());
        assertEquals(1, semester.getSession().getExams().size());
        assertEquals(9, semester.getSemesterScoreSum());
        assertEquals(2, semester.getPassedSubjectsCount());
    }

    @Test
    void testSemesterWithRetakes() {
        final Semester semester = new Semester(LocalDate.of(2024, 1, 1));

        semester.addDiscipline(EXAM, true);
        semester.addGradeToDiscipline(EXAM, TWO, LocalDate.of(2024, 1, 15));
        semester.addGradeToDiscipline(EXAM, THREE, LocalDate.of(2024, 1, 20));
        semester.addGradeToDiscipline(EXAM, FOUR, LocalDate.of(2024, 1, 25));

        assertEquals(1, semester.getPassedSubjectsCount());
        assertEquals(4, semester.getSemesterScoreSum());
        assertTrue(semester.getDisciplines().get(0).isPassed());
    }

    @Test
    void testEmptySemesterStatistics() {
        final Semester semester = new Semester(LocalDate.of(2024, 1, 1));

        assertEquals(0, semester.getSemesterScoreSum());
        assertEquals(0, semester.getPassedSubjectsCount());
    }
}