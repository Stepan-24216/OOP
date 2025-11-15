package org.example;

import static org.example.Discipline.COLLOQUIUM;
import static org.example.Discipline.CONTROL;
import static org.example.Discipline.DIFFERENTIATED_CREDIT;
import static org.example.Discipline.EXAM;
import static org.example.Discipline.PRACTICE_REPORT;
import static org.example.Discipline.PROTECTION_WRK;
import static org.example.Discipline.TASK;
import static org.example.Discipline.TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestCreditCard {
    @Test
    void testDiscipline() {
        assertEquals("Задание", TASK.getDisciplineName());
        assertEquals("Контрольная работа", CONTROL.getDisciplineName());
        assertEquals("Коллоквиум", COLLOQUIUM.getDisciplineName());
        assertEquals("Экзамен", EXAM.getDisciplineName());
        assertEquals("Дифференцированный зачет", DIFFERENTIATED_CREDIT.getDisciplineName());
        assertEquals("Зачет", TEST.getDisciplineName());
        assertEquals("Отчет по практике", PRACTICE_REPORT.getDisciplineName());
        assertEquals("Защита ВКР", PROTECTION_WRK.getDisciplineName());
    }

    @Test
    void testPossibleStudyOnBudget() {
        Student student = new Student(
            false); // человек учится на платке и имеет возможность перевестись на бюджет
        Semester semester1 = new Semester(1);
        semester1.addDiscipline(TASK, 4, false);
        semester1.addDiscipline(CONTROL, 4, false);
        semester1.addDiscipline(EXAM, 4, true);
        Semester semester2 = new Semester(2);
        semester2.addDiscipline(TASK, 5, false);
        semester2.addDiscipline(CONTROL, 5, false);
        semester2.addDiscipline(EXAM, 5, true);
        student.addSemesters(semester1);
        student.addSemesters(semester2);
        assertEquals(4.5, student.getAvgAllSemesters());
        assertTrue(student.possibleStudyOnBudget());
        assertFalse(student.possibleGetRedDiploma(true));
        assertFalse(student.possibleIncreasedScholarship());
    }

    @Test
    void testPossibleStudyOnBudgetMoreTwoSemesters() {
        Student student = new Student(false);
        Semester semester1 = new Semester(1);
        semester1.addDiscipline(TASK, 3, false);
        semester1.addDiscipline(CONTROL, 3, false);
        semester1.addDiscipline(EXAM, 3, true);
        Semester semester2 = new Semester(2);
        semester2.addDiscipline(TASK, 5, false);
        semester2.addDiscipline(CONTROL, 5, false);
        semester2.addDiscipline(EXAM, 4, true);
        Semester semester3 = new Semester(3);
        semester3.addDiscipline(TASK, 5, false);
        semester3.addDiscipline(CONTROL, 3, false);
        semester3.addDiscipline(EXAM, 5, true);
        student.addSemesters(semester1);
        student.addSemesters(semester2);
        student.addSemesters(semester3);
        assertEquals(4.0, student.getAvgAllSemesters());
        assertTrue(student.possibleStudyOnBudget());
        assertFalse(student.possibleGetRedDiploma(true));
        assertFalse(student.possibleIncreasedScholarship());
    }

    @Test
    void testPossibleStudyOnBudgetForBudgetStudent() {
        Student student =
            new Student(true); // человек учится на бюджете => перевод на бюждет всегда правда
        Semester semester1 = new Semester(1);
        semester1.addDiscipline(TASK, 3, false);
        semester1.addDiscipline(CONTROL, 4, false);
        semester1.addDiscipline(EXAM, 4, true);
        Semester semester2 = new Semester(2);
        semester2.addDiscipline(TASK, 5, false);
        semester2.addDiscipline(CONTROL, 5, false);
        semester2.addDiscipline(EXAM, 3, true);
        student.addSemesters(semester1);
        student.addSemesters(semester2);
        assertEquals(4.0, student.getAvgAllSemesters());
        assertTrue(student.possibleStudyOnBudget());
        assertFalse(student.possibleGetRedDiploma(true));
        assertFalse(student.possibleIncreasedScholarship());
    }

    @Test
    void testPossibleGetRedDiploma() {
        Student student = new Student(true);
        Semester semester1 = new Semester(1);
        semester1.addDiscipline(TASK, 5, false);
        semester1.addDiscipline(CONTROL, 5, false);
        semester1.addDiscipline(EXAM, 4, true);
        Semester semester2 = new Semester(2);
        semester2.addDiscipline(TASK, 5, false);
        semester2.addDiscipline(CONTROL, 5, false);
        semester2.addDiscipline(EXAM, 5, true);
        Semester semester3 = new Semester(3);
        semester3.addDiscipline(TASK, 4, false);
        semester3.addDiscipline(CONTROL, 5, false);
        semester3.addDiscipline(EXAM, 5, true);
        semester3.addDiscipline(PRACTICE_REPORT, 5, true);
        student.addSemesters(semester1);
        student.addSemesters(semester2);
        student.addSemesters(semester3);
        assertEquals(4.8, student.getAvgAllSemesters());
        assertTrue(student.possibleStudyOnBudget());
        assertTrue(student.possibleGetRedDiploma(true));
        assertFalse(student.possibleIncreasedScholarship());
    }

    @Test
    void testPossibleIncreasedScholarship() {
        Student student = new Student(true);
        Semester semester1 = new Semester(1);
        semester1.addDiscipline(TASK, 5, false);
        semester1.addDiscipline(CONTROL, 5, false);
        semester1.addDiscipline(EXAM, 5, true);
        Semester semester2 = new Semester(2);
        semester2.addDiscipline(TASK, 5, false);
        semester2.addDiscipline(CONTROL, 5, false);
        semester2.addDiscipline(EXAM, 5, true);
        student.addSemesters(semester1);
        student.addSemesters(semester2);
        assertEquals(5.0, student.getAvgAllSemesters());
        assertTrue(student.possibleStudyOnBudget());
        assertTrue(student.possibleGetRedDiploma(true));
        assertTrue(student.possibleIncreasedScholarship());
    }

    @Test
    void testPossibleIncreasedScholarshipWithoutQualifyingWork() {
        Student student = new Student(true);
        Semester semester1 = new Semester(1);
        semester1.addDiscipline(TASK, 5, false);
        semester1.addDiscipline(CONTROL, 5, false);
        semester1.addDiscipline(EXAM, 5, true);
        Semester semester2 = new Semester(2);
        semester2.addDiscipline(TASK, 5, false);
        semester2.addDiscipline(CONTROL, 5, false);
        semester2.addDiscipline(EXAM, 5, true);
        student.addSemesters(semester1);
        student.addSemesters(semester2);
        assertEquals(5.0, student.getAvgAllSemesters());
        assertTrue(student.possibleStudyOnBudget());
        assertFalse(student.possibleGetRedDiploma(false));
        assertTrue(student.possibleIncreasedScholarship());
    }
}
