package org.example;

import static org.example.model.Discipline.EXAM;
import static org.example.model.Discipline.TASK;
import static org.example.grading.Score.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.example.academic.SubjectEntry;
import org.junit.jupiter.api.Test;

/**
 * Тесты для класса SubjectEntry.
 */
public class TestSubjectEntry {

    @Test
    void testSubjectEntryBasic() {
        final SubjectEntry subject = new SubjectEntry(EXAM, true);

        assertEquals(EXAM, subject.getSubject());
        assertTrue(subject.getFlagExam());
        assertFalse(subject.isPassed());
        assertNull(subject.getEstimation());
    }

    @Test
    void testSubjectEntryWithGrades() {
        final SubjectEntry subject = new SubjectEntry(EXAM, true);

        subject.addGrade(TWO, LocalDate.of(2024, 1, 15));
        subject.addGrade(THREE, LocalDate.of(2024, 1, 20));
        subject.addGrade(FOUR, LocalDate.of(2024, 1, 25));

        assertEquals(3, subject.getGradeHistory().size());
        assertTrue(subject.isPassed());
        assertEquals(4, subject.getEstimation());
        assertEquals(FOUR, subject.getLastPositiveGrade().getScore());
    }

    @Test
    void testSubjectEntryFailed() {
        final SubjectEntry subject = new SubjectEntry(EXAM, true);

        subject.addGrade(TWO, LocalDate.of(2024, 1, 15));
        subject.addGrade(FAIL, LocalDate.of(2024, 1, 20));

        assertFalse(subject.isPassed());
        assertNull(subject.getEstimation());
        assertNull(subject.getLastPositiveGrade());
    }

    @Test
    void testSubjectEntryComparison() {
        final SubjectEntry obj1 = new SubjectEntry(TASK, false);
        final SubjectEntry obj2 = new SubjectEntry(TASK, false);
        final SubjectEntry obj3 = new SubjectEntry(EXAM, false);

        assertTrue(obj1.equals(obj2));
        assertFalse(obj1.equals(obj3));
    }

    @Test
    void testSubjectEntryPassFail() {
        final SubjectEntry subject = new SubjectEntry(TASK, false);

        subject.addGrade(FAIL, LocalDate.of(2024, 1, 15));
        subject.addGrade(PASS, LocalDate.of(2024, 1, 20));

        assertTrue(subject.isPassed());
        assertNull(subject.getEstimation());
    }
}