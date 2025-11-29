package org.example;

import static org.example.grading.Score.FIVE;
import static org.example.grading.Score.FOUR;
import static org.example.grading.Score.THREE;
import static org.example.model.Discipline.EXAM;
import static org.example.model.Discipline.TASK;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import org.example.model.CreditBook;
import org.example.model.Semester;
import org.example.model.Session;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса CreditBook.
 */
public class TestCreditBook {

    @Test
    void testCreditBookBasic() {
        final CreditBook student = new CreditBook(true);

        assertTrue(student.getBudgetStudy());
        assertEquals(0.0, student.getAvgAllSemesters());
        assertTrue(student.getLastTwoSessions().isEmpty());
        assertNull(student.getCurrentSemester());
    }

    @Test
    void testCreditBookWithSemesters() {
        final CreditBook student = new CreditBook(false);

        final Semester semester1 = new Semester(LocalDate.of(2023, 1, 1));
        semester1.addDiscipline(EXAM, true);
        semester1.addGradeToDiscipline(EXAM, FOUR, LocalDate.of(2023, 1, 20));

        final Semester semester2 = new Semester(LocalDate.of(2023, 6, 1));
        semester2.addDiscipline(TASK, false);
        semester2.addGradeToDiscipline(TASK, FIVE, LocalDate.of(2023, 6, 15));

        student.addSemesters(semester1);
        student.addSemesters(semester2);

        assertEquals(2, student.getLastTwoSessions().size());
        assertEquals(4.5, student.getAvgAllSemesters());
        assertEquals(semester2, student.getCurrentSemester());
    }

    @Test
    void testPossibleStudyOnBudget() {
        final CreditBook student = new CreditBook(false);

        final Semester semester1 = new Semester(LocalDate.of(2023, 1, 1));
        semester1.addDiscipline(EXAM, true);
        semester1.addGradeToDiscipline(EXAM, FOUR, LocalDate.of(2023, 1, 20));

        final Semester semester2 = new Semester(LocalDate.of(2023, 6, 1));
        semester2.addDiscipline(TASK, false);
        semester2.addGradeToDiscipline(TASK, FIVE, LocalDate.of(2023, 6, 15));

        student.addSemesters(semester1);
        student.addSemesters(semester2);

        assertTrue(student.possibleStudyOnBudget());
    }

    @Test
    void testPossibleStudyOnBudgetWithBadGrades() {
        final CreditBook student = new CreditBook(false);

        final Semester semester1 = new Semester(LocalDate.of(2023, 1, 1));
        semester1.addDiscipline(EXAM, true);
        semester1.addGradeToDiscipline(EXAM, THREE, LocalDate.of(2023, 1, 20));

        final Semester semester2 = new Semester(LocalDate.of(2023, 6, 1));
        semester2.addDiscipline(TASK, false);
        semester2.addGradeToDiscipline(TASK, FIVE, LocalDate.of(2023, 6, 15));

        student.addSemesters(semester1);
        student.addSemesters(semester2);

        assertFalse(student.possibleStudyOnBudget());
    }

    @Test
    void testPossibleGetRedDiploma() {
        final CreditBook student = new CreditBook(true);

        final Semester semester = new Semester(LocalDate.of(2023, 1, 1));
        semester.addDiscipline(EXAM, true);
        semester.addGradeToDiscipline(EXAM, FIVE, LocalDate.of(2023, 1, 20));
        semester.addDiscipline(TASK, false);
        semester.addGradeToDiscipline(TASK, FIVE, LocalDate.of(2023, 1, 15));

        student.addSemesters(semester);

        assertTrue(student.possibleGetRedDiploma(true));
        assertFalse(student.possibleGetRedDiploma(false));
    }

    @Test
    void testPossibleIncreasedScholarship() {
        final CreditBook student = new CreditBook(true);

        final Semester semester1 = new Semester(LocalDate.of(2023, 1, 1));
        semester1.addDiscipline(EXAM, true);
        semester1.addGradeToDiscipline(EXAM, FIVE, LocalDate.of(2023, 1, 20));

        final Semester semester2 = new Semester(LocalDate.of(2023, 6, 1));
        semester2.addDiscipline(TASK, false);
        semester2.addGradeToDiscipline(TASK, FIVE, LocalDate.of(2023, 6, 15));

        student.addSemesters(semester1);
        student.addSemesters(semester2);

        assertTrue(student.possibleIncreasedScholarship());
    }

    @Test
    void testPossibleIncreasedScholarshipNotBudget() {
        final CreditBook student = new CreditBook(false);

        final Semester semester = new Semester(LocalDate.of(2023, 1, 1));
        semester.addDiscipline(EXAM, true);
        semester.addGradeToDiscipline(EXAM, FIVE, LocalDate.of(2023, 1, 20));

        student.addSemesters(semester);

        assertFalse(student.possibleIncreasedScholarship());
    }

    @Test
    void testLastTwoSessionsOrder() {
        final CreditBook student = new CreditBook(true);

        final Semester semester1 = new Semester(LocalDate.of(2023, 1, 1));
        final Semester semester2 = new Semester(LocalDate.of(2023, 6, 1));
        final Semester semester3 = new Semester(LocalDate.of(2023, 12, 1));

        student.addSemesters(semester1);
        student.addSemesters(semester2);
        student.addSemesters(semester3);

        final List<Session> lastSessions = student.getLastTwoSessions();

        assertEquals(2, lastSessions.size());
        assertEquals(semester3.getSession(), lastSessions.get(0));
        assertEquals(semester2.getSession(), lastSessions.get(1));
    }

    @Test
    void testCreditBookEmptyLastTwoSessions() {
        final CreditBook student = new CreditBook(true);

        assertTrue(student.getLastTwoSessions().isEmpty());
    }

    @Test
    void testCreditBookSingleSemester() {
        final CreditBook student = new CreditBook(true);
        final Semester semester = new Semester(LocalDate.of(2023, 1, 1));

        student.addSemesters(semester);

        assertEquals(1, student.getLastTwoSessions().size());
        assertEquals(semester, student.getCurrentSemester());
    }
}