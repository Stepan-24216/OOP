package org.example;

import static org.example.model.Discipline.EXAM;
import static org.example.model.Discipline.TEST;
import static org.example.grading.Score.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.example.academic.SubjectEntry;
import org.example.model.Session;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса Session.
 */
public class TestSession {

    @Test
    void testSessionBasic() {
        final Session session = new Session("Зимняя 2024", LocalDate.of(2024, 1, 15));

        assertEquals("Зимняя 2024", session.getSessionName());
        assertEquals(LocalDate.of(2024, 1, 15), session.getSessionDate());
        assertTrue(session.getExams().isEmpty());
        assertTrue(session.haveAllExamGoodEstimation());
    }

    @Test
    void testSessionWithExams() {
        final Session session = new Session("Летняя 2024", LocalDate.of(2024, 6, 15));

        final SubjectEntry exam1 = new SubjectEntry(EXAM, true);
        exam1.addGrade(FIVE, LocalDate.of(2024, 6, 20));
        session.addExam(exam1);

        final SubjectEntry exam2 = new SubjectEntry(TEST, false);
        exam2.addGrade(FOUR, LocalDate.of(2024, 6, 18));
        session.addExam(exam2);

        assertEquals(2, session.getExams().size());
        assertTrue(session.haveAllExamGoodEstimation());
    }

    @Test
    void testSessionWithBadGrades() {
        final Session session = new Session("Зимняя 2024", LocalDate.of(2024, 1, 15));

        final SubjectEntry exam1 = new SubjectEntry(EXAM, true);
        exam1.addGrade(THREE, LocalDate.of(2024, 1, 20));
        session.addExam(exam1);

        final SubjectEntry exam2 = new SubjectEntry(TEST, false);
        exam2.addGrade(TWO, LocalDate.of(2024, 1, 18));
        session.addExam(exam2);

        assertFalse(session.haveAllExamGoodEstimation());
    }
}