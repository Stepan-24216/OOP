package org.example;

import static org.example.Discipline.COLLOQUIUM;
import static org.example.Discipline.CONTROL;
import static org.example.Discipline.DIFFERENTIATED_CREDIT;
import static org.example.Discipline.EXAM;
import static org.example.Discipline.PRACTICE_REPORT;
import static org.example.Discipline.PROTECTION_WRK;
import static org.example.Discipline.TASK;
import static org.example.Discipline.TEST;
import static org.example.Score.FIVE;
import static org.example.Score.FOUR;
import static org.example.Score.THREE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Тестовый класс для проверки функциональности.
 */
public class TestCreditCard {

    /**
     * Тестирование дисциплин.
     */
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

    /**
     * Человек учится на платке и имеет возможность перевестись на бюджет.
     */
    @Test
    void testPossibleStudyOnBudget() {
        Student student = new Student(
            false);
        Semester semester1 = new Semester(1);
        semester1.addDiscipline(TASK, FOUR, false);
        semester1.addDiscipline(CONTROL, FOUR, false);
        semester1.addDiscipline(EXAM, FOUR, true);
        Semester semester2 = new Semester(2);
        semester2.addDiscipline(TASK, FIVE, false);
        semester2.addDiscipline(CONTROL, FIVE, false);
        semester2.addDiscipline(EXAM, FIVE, true);
        student.addSemesters(semester1);
        student.addSemesters(semester2);
        assertEquals(4.5, student.getAvgAllSemesters());
        assertTrue(student.possibleStudyOnBudget());
        assertFalse(student.possibleGetRedDiploma(true));
        assertFalse(student.possibleIncreasedScholarship());
    }

    /**
     * Человек учится на платке более трёх семестров и имеет возможность перевестись на бюджет.
     */
    @Test
    void testPossibleStudyOnBudgetMoreTwoSemesters() {
        Student student = new Student(false);
        Semester semester1 = new Semester(1);
        semester1.addDiscipline(TASK, THREE, false);
        semester1.addDiscipline(CONTROL, THREE, false);
        semester1.addDiscipline(EXAM, THREE, true);
        Semester semester2 = new Semester(2);
        semester2.addDiscipline(TASK, FIVE, false);
        semester2.addDiscipline(CONTROL, FIVE, false);
        semester2.addDiscipline(EXAM, FOUR, true);
        Semester semester3 = new Semester(3);
        semester3.addDiscipline(TASK, FIVE, false);
        semester3.addDiscipline(CONTROL, THREE, false);
        semester3.addDiscipline(EXAM, FIVE, true);
        student.addSemesters(semester1);
        student.addSemesters(semester2);
        student.addSemesters(semester3);
        assertEquals(4.0, student.getAvgAllSemesters());
        assertTrue(student.possibleStudyOnBudget());
        assertFalse(student.possibleGetRedDiploma(true));
        assertFalse(student.possibleIncreasedScholarship());
    }

    /**
     * Человек учится на бюджете => перевод на бюждет всегда правда.
     */
    @Test
    void testPossibleStudyOnBudgetForBudgetStudent() {
        Student student =
            new Student(true);
        Semester semester1 = new Semester(1);
        semester1.addDiscipline(TASK, THREE, false);
        semester1.addDiscipline(CONTROL, FOUR, false);
        semester1.addDiscipline(EXAM, FOUR, true);
        Semester semester2 = new Semester(2);
        semester2.addDiscipline(TASK, FIVE, false);
        semester2.addDiscipline(CONTROL, FIVE, false);
        semester2.addDiscipline(EXAM, THREE, true);
        student.addSemesters(semester1);
        student.addSemesters(semester2);
        assertEquals(4.0, student.getAvgAllSemesters());
        assertTrue(student.possibleStudyOnBudget());
        assertFalse(student.possibleGetRedDiploma(true));
        assertFalse(student.possibleIncreasedScholarship());
    }

    /**
     * Проверка, что человек может получить диплом если он имеет квалификационную работу.
     */
    @Test
    void testPossibleGetRedDiploma() {
        Student student = new Student(true);
        Semester semester1 = new Semester(1);
        semester1.addDiscipline(TASK, FIVE, false);
        semester1.addDiscipline(CONTROL, FIVE, false);
        semester1.addDiscipline(EXAM, FOUR, true);
        Semester semester2 = new Semester(2);
        semester2.addDiscipline(TASK, FIVE, false);
        semester2.addDiscipline(CONTROL, FIVE, false);
        semester2.addDiscipline(EXAM, FIVE, true);
        Semester semester3 = new Semester(3);
        semester3.addDiscipline(TASK, FOUR, false);
        semester3.addDiscipline(CONTROL, FIVE, false);
        semester3.addDiscipline(EXAM, FIVE, true);
        semester3.addDiscipline(PRACTICE_REPORT, FIVE, true);
        student.addSemesters(semester1);
        student.addSemesters(semester2);
        student.addSemesters(semester3);
        assertEquals(4.8, student.getAvgAllSemesters());
        assertTrue(student.possibleStudyOnBudget());
        assertTrue(student.possibleGetRedDiploma(true));
        assertFalse(student.possibleIncreasedScholarship());
    }

    /**
     * Проверка, что человек не может получить диплом если он не имеет квалификационную работу.
     */
    @Test
    void testPossibleGetRedDiplomaWithoutQualifyingWork() {
        Student student = new Student(true);
        Semester semester1 = new Semester(1);
        semester1.addDiscipline(TASK, FIVE, false);
        semester1.addDiscipline(CONTROL, FIVE, false);
        semester1.addDiscipline(EXAM, FIVE, true);
        Semester semester2 = new Semester(2);
        semester2.addDiscipline(TASK, FIVE, false);
        semester2.addDiscipline(CONTROL, FIVE, false);
        semester2.addDiscipline(EXAM, FIVE, true);
        student.addSemesters(semester1);
        student.addSemesters(semester2);
        assertEquals(5.0, student.getAvgAllSemesters());
        assertTrue(student.possibleStudyOnBudget());
        assertFalse(student.possibleGetRedDiploma(false));
        assertTrue(student.possibleIncreasedScholarship());
    }

    /**
     * Проверка, что человек может получать повышенную стипендию если учится на одни пятёрки.
     */
    @Test
    void testPossibleIncreasedScholarship() {
        Student student = new Student(true);
        Semester semester1 = new Semester(1);
        semester1.addDiscipline(TASK, FIVE, false);
        semester1.addDiscipline(CONTROL, FIVE, false);
        semester1.addDiscipline(EXAM, FIVE, true);
        Semester semester2 = new Semester(2);
        semester2.addDiscipline(TASK, FIVE, false);
        semester2.addDiscipline(CONTROL, FIVE, false);
        semester2.addDiscipline(EXAM, FIVE, true);
        student.addSemesters(semester1);
        student.addSemesters(semester2);
        assertEquals(5.0, student.getAvgAllSemesters());
        assertTrue(student.possibleStudyOnBudget());
        assertTrue(student.possibleGetRedDiploma(true));
        assertTrue(student.possibleIncreasedScholarship());
    }
}
