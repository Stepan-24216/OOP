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
import static org.example.Score.TWO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
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
        final CreditBook student = new CreditBook(false);
        final Semester semester1 = new Semester(LocalDate.of(2023, 6, 1));
        semester1.addDiscipline(TASK, false);
        semester1.addDiscipline(CONTROL, false);
        semester1.addDiscipline(EXAM, true);
        semester1.addGradeToDiscipline(TASK, FOUR, LocalDate.of(2023, 6, 15));
        semester1.addGradeToDiscipline(CONTROL, FOUR, LocalDate.of(2023, 6, 16));
        semester1.addGradeToDiscipline(EXAM, FOUR, LocalDate.of(2023, 6, 20));

        final Semester semester2 = new Semester(LocalDate.of(2023, 12, 1));
        semester2.addDiscipline(TASK, false);
        semester2.addDiscipline(CONTROL, false);
        semester2.addDiscipline(EXAM, true);
        semester2.addGradeToDiscipline(TASK, FIVE, LocalDate.of(2023, 12, 15));
        semester2.addGradeToDiscipline(CONTROL, FIVE, LocalDate.of(2023, 12, 16));
        semester2.addGradeToDiscipline(EXAM, FIVE, LocalDate.of(2023, 12, 20));

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
        final CreditBook student = new CreditBook(false);
        final Semester semester1 = new Semester(LocalDate.of(2023, 1, 1));
        semester1.addDiscipline(TASK, false);
        semester1.addDiscipline(CONTROL, false);
        semester1.addDiscipline(EXAM, true);
        semester1.addGradeToDiscipline(TASK, THREE, LocalDate.of(2023, 1, 15));
        semester1.addGradeToDiscipline(CONTROL, THREE, LocalDate.of(2023, 1, 16));
        semester1.addGradeToDiscipline(EXAM, THREE, LocalDate.of(2023, 1, 20));

        final Semester semester2 = new Semester(LocalDate.of(2023, 6, 1));
        semester2.addDiscipline(TASK, false);
        semester2.addDiscipline(CONTROL, false);
        semester2.addDiscipline(EXAM, true);
        semester2.addGradeToDiscipline(TASK, FIVE, LocalDate.of(2023, 6, 15));
        semester2.addGradeToDiscipline(CONTROL, FIVE, LocalDate.of(2023, 6, 16));
        semester2.addGradeToDiscipline(EXAM, FOUR, LocalDate.of(2023, 6, 20));

        final Semester semester3 = new Semester(LocalDate.of(2023, 12, 1));
        semester3.addDiscipline(TASK, false);
        semester3.addDiscipline(CONTROL, false);
        semester3.addDiscipline(EXAM, true);
        semester3.addGradeToDiscipline(TASK, FIVE, LocalDate.of(2023, 12, 15));
        semester3.addGradeToDiscipline(CONTROL, THREE, LocalDate.of(2023, 12, 16));
        semester3.addGradeToDiscipline(EXAM, FIVE, LocalDate.of(2023, 12, 20));

        student.addSemesters(semester1);
        student.addSemesters(semester2);
        student.addSemesters(semester3);
        assertEquals(4.0, student.getAvgAllSemesters());
        assertFalse(student.possibleStudyOnBudget());
        assertFalse(student.possibleGetRedDiploma(true));
        assertFalse(student.possibleIncreasedScholarship());
    }

    /**
     * Человек учится на бюджете => перевод на бюждет всегда правда.
     */
    @Test
    void testPossibleStudyOnBudgetForBudgetStudent() {
        final CreditBook student = new CreditBook(true);
        final Semester semester1 = new Semester(LocalDate.of(2023, 6, 1));
        semester1.addDiscipline(TASK, false);
        semester1.addDiscipline(CONTROL, false);
        semester1.addDiscipline(EXAM, true);
        semester1.addGradeToDiscipline(TASK, THREE, LocalDate.of(2023, 6, 15));
        semester1.addGradeToDiscipline(CONTROL, FOUR, LocalDate.of(2023, 6, 16));
        semester1.addGradeToDiscipline(EXAM, FOUR, LocalDate.of(2023, 6, 20));

        final Semester semester2 = new Semester(LocalDate.of(2023, 12, 1));
        semester2.addDiscipline(TASK, false);
        semester2.addDiscipline(CONTROL, false);
        semester2.addDiscipline(EXAM, true);
        semester2.addGradeToDiscipline(TASK, FIVE, LocalDate.of(2023, 12, 15));
        semester2.addGradeToDiscipline(CONTROL, FIVE, LocalDate.of(2023, 12, 16));
        semester2.addGradeToDiscipline(EXAM, THREE, LocalDate.of(2023, 12, 20));

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
        final CreditBook student = new CreditBook(true);
        final Semester semester1 = new Semester(LocalDate.of(2023, 1, 1));
        semester1.addDiscipline(TASK, false);
        semester1.addDiscipline(CONTROL, false);
        semester1.addDiscipline(EXAM, true);
        semester1.addGradeToDiscipline(TASK, FIVE, LocalDate.of(2023, 1, 15));
        semester1.addGradeToDiscipline(CONTROL, FIVE, LocalDate.of(2023, 1, 16));
        semester1.addGradeToDiscipline(EXAM, FOUR, LocalDate.of(2023, 1, 20));

        final Semester semester2 = new Semester(LocalDate.of(2023, 6, 1));
        semester2.addDiscipline(TASK, false);
        semester2.addDiscipline(CONTROL, false);
        semester2.addDiscipline(EXAM, true);
        semester2.addGradeToDiscipline(TASK, FIVE, LocalDate.of(2023, 6, 15));
        semester2.addGradeToDiscipline(CONTROL, FIVE, LocalDate.of(2023, 6, 16));
        semester2.addGradeToDiscipline(EXAM, FIVE, LocalDate.of(2023, 6, 20));

        final Semester semester3 = new Semester(LocalDate.of(2023, 12, 1));
        semester3.addDiscipline(TASK, false);
        semester3.addDiscipline(CONTROL, false);
        semester3.addDiscipline(EXAM, true);
        semester3.addDiscipline(PRACTICE_REPORT, true);
        semester3.addGradeToDiscipline(TASK, FOUR, LocalDate.of(2023, 12, 15));
        semester3.addGradeToDiscipline(CONTROL, FIVE, LocalDate.of(2023, 12, 16));
        semester3.addGradeToDiscipline(EXAM, FIVE, LocalDate.of(2023, 12, 20));
        semester3.addGradeToDiscipline(PRACTICE_REPORT, FIVE, LocalDate.of(2023, 12, 25));

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
        final CreditBook student = new CreditBook(true);
        final Semester semester1 = new Semester(LocalDate.of(2023, 6, 1));
        semester1.addDiscipline(TASK, false);
        semester1.addDiscipline(CONTROL, false);
        semester1.addDiscipline(EXAM, true);
        semester1.addGradeToDiscipline(TASK, FIVE, LocalDate.of(2023, 6, 15));
        semester1.addGradeToDiscipline(CONTROL, FIVE, LocalDate.of(2023, 6, 16));
        semester1.addGradeToDiscipline(EXAM, FIVE, LocalDate.of(2023, 6, 20));

        final Semester semester2 = new Semester(LocalDate.of(2023, 12, 1));
        semester2.addDiscipline(TASK, false);
        semester2.addDiscipline(CONTROL, false);
        semester2.addDiscipline(EXAM, true);
        semester2.addGradeToDiscipline(TASK, FIVE, LocalDate.of(2023, 12, 15));
        semester2.addGradeToDiscipline(CONTROL, FIVE, LocalDate.of(2023, 12, 16));
        semester2.addGradeToDiscipline(EXAM, FIVE, LocalDate.of(2023, 12, 20));

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
        final CreditBook student = new CreditBook(true);
        final Semester semester1 = new Semester(LocalDate.of(2023, 6, 1));
        semester1.addDiscipline(TASK, false);
        semester1.addDiscipline(CONTROL, false);
        semester1.addDiscipline(EXAM, true);
        semester1.addGradeToDiscipline(TASK, FIVE, LocalDate.of(2023, 6, 15));
        semester1.addGradeToDiscipline(CONTROL, FIVE, LocalDate.of(2023, 6, 16));
        semester1.addGradeToDiscipline(EXAM, FIVE, LocalDate.of(2023, 6, 20));

        final Semester semester2 = new Semester(LocalDate.of(2023, 12, 1));
        semester2.addDiscipline(TASK, false);
        semester2.addDiscipline(CONTROL, false);
        semester2.addDiscipline(EXAM, true);
        semester2.addGradeToDiscipline(TASK, FIVE, LocalDate.of(2023, 12, 15));
        semester2.addGradeToDiscipline(CONTROL, FIVE, LocalDate.of(2023, 12, 16));
        semester2.addGradeToDiscipline(EXAM, FIVE, LocalDate.of(2023, 12, 20));

        student.addSemesters(semester1);
        student.addSemesters(semester2);
        assertEquals(5.0, student.getAvgAllSemesters());
        assertTrue(student.possibleStudyOnBudget());
        assertTrue(student.possibleGetRedDiploma(true));
        assertTrue(student.possibleIncreasedScholarship());
    }

    /**
     * Тест пересдач - учитывается только последняя положительная оценка.
     */
    @Test
    void testRetakes() {
        final CreditBook student = new CreditBook(true);
        final Semester semester = new Semester(LocalDate.of(2024, 1, 1));
        semester.addDiscipline(EXAM, true);

        semester.addGradeToDiscipline(EXAM, TWO, LocalDate.of(2024, 1, 15));
        semester.addGradeToDiscipline(EXAM, THREE, LocalDate.of(2024, 1, 20));
        semester.addGradeToDiscipline(EXAM, FOUR, LocalDate.of(2024, 1, 25));

        student.addSemesters(semester);

        assertEquals(4.0, student.getAvgAllSemesters());
        assertTrue(semester.getDisciplines().get(0).isPassed());
        assertEquals(4, semester.getDisciplines().get(0).getEstimation());
    }

    /**
     * Тест непройденного предмета.
     */
    @Test
    void testFailedSubject() {
        final CreditBook student = new CreditBook(true);
        final Semester semester = new Semester(LocalDate.of(2024, 1, 1));
        semester.addDiscipline(EXAM, true);

        semester.addGradeToDiscipline(EXAM, TWO, LocalDate.of(2024, 1, 15));

        student.addSemesters(semester);

        assertEquals(0.0, student.getAvgAllSemesters());
        assertFalse(semester.getDisciplines().get(0).isPassed());
    }

    /**
     * Тест группировки по сессиям по датам.
     */
    @Test
    void testSessionGroupingByDate() {
        final CreditBook student = new CreditBook(false);

        final Semester semester1 = new Semester(LocalDate.of(2023, 1, 1));
        semester1.addDiscipline(EXAM, true);
        semester1.addGradeToDiscipline(EXAM, THREE, LocalDate.of(2023, 1, 20));

        final Semester semester2 = new Semester(LocalDate.of(2023, 6, 1));
        semester2.addDiscipline(EXAM, true);
        semester2.addGradeToDiscipline(EXAM, FOUR, LocalDate.of(2023, 6, 20));

        final Semester semester3 = new Semester(LocalDate.of(2023, 12, 1));
        semester3.addDiscipline(EXAM, true);
        semester3.addGradeToDiscipline(EXAM, FIVE, LocalDate.of(2023, 12, 20));

        student.addSemesters(semester1);
        student.addSemesters(semester2);
        student.addSemesters(semester3);

        assertTrue(student.possibleStudyOnBudget());
    }

    /**
     * Тест истории оценок.
     */
    @Test
    void testGradeHistory() {
        final SubjectEntry subject = new SubjectEntry(EXAM, true);
        subject.addGrade(TWO, LocalDate.of(2024, 1, 15));
        subject.addGrade(THREE, LocalDate.of(2024, 1, 20));
        subject.addGrade(FOUR, LocalDate.of(2024, 1, 25));

        assertEquals(3, subject.getGradeHistory().size());
        assertEquals(TWO, subject.getGradeHistory().get(0).getScore());
        assertEquals(FOUR, subject.getLastGrade().getScore());
        assertEquals(FOUR, subject.getLastPositiveGrade().getScore());
    }

    /**
     * Тест сравнения предметов.
     */
    @Test
    void testSubjectComparison() {
        final SubjectEntry obj1 = new SubjectEntry(TASK, false);
        obj1.addGrade(FIVE, LocalDate.of(2023, 12, 25));

        final SubjectEntry obj2 = new SubjectEntry(TASK, false);
        obj2.addGrade(FIVE, LocalDate.of(2024, 12, 25));

        final SubjectEntry obj3 = new SubjectEntry(EXAM, false);
        obj3.addGrade(FIVE, LocalDate.of(2024, 12, 25));

        assertTrue(obj1.equals(obj2));
        assertFalse(obj1.equals(obj3));
    }

    /**
     * Тест функциональности класса Session.
     */
    @Test
    void testSessionFunctionality() {
        final Session session = new Session("Зимняя 2024", LocalDate.of(2024, 1, 15));

        final SubjectEntry exam1 = new SubjectEntry(EXAM, true);
        exam1.addGrade(FIVE, LocalDate.of(2024, 1, 20));
        session.addExam(exam1);

        final SubjectEntry exam2 = new SubjectEntry(TEST, false);
        exam2.addGrade(FOUR, LocalDate.of(2024, 1, 18));
        session.addExam(exam2);

        assertEquals(2, session.getExams().size());
        assertEquals("Зимняя 2024", session.getSessionName());
        assertEquals(LocalDate.of(2024, 1, 15), session.getSessionDate());
        assertTrue(session.haveAllExamGoodEstimation());
    }

    /**
     * Тест функциональности класса Semester.
     */
    @Test
    void testSemesterFunctionality() {
        final Semester semester = new Semester(LocalDate.of(2024, 1, 1));

        semester.addDiscipline(EXAM, true);
        semester.addDiscipline(TASK, false);
        semester.addGradeToDiscipline(EXAM, FIVE, LocalDate.of(2024, 1, 20));
        semester.addGradeToDiscipline(TASK, FOUR, LocalDate.of(2024, 1, 15));

        assertEquals(1, semester.getDisciplines().size());
        assertEquals(1, semester.getSession().getExams().size());
        assertEquals(LocalDate.of(2024, 1, 1), semester.getSemesterTime());
        assertEquals(9, semester.getSumSemesterScore());
        assertEquals(2, semester.getCountDiscipline());
        assertFalse(semester.haveAllFiveEstimation());
        assertTrue(semester.haveAllExamGoodEstimation());
    }

    /**
     * Тест сессии с плохими оценками.
     */
    @Test
    void testSessionWithBadGrades() {
        final Session session = new Session("Летняя 2024", LocalDate.of(2024, 6, 15));

        final SubjectEntry exam1 = new SubjectEntry(EXAM, true);
        exam1.addGrade(THREE, LocalDate.of(2024, 6, 20));
        session.addExam(exam1);

        final SubjectEntry exam2 = new SubjectEntry(TEST, false);
        exam2.addGrade(TWO, LocalDate.of(2024, 6, 18));
        session.addExam(exam2);

        assertFalse(session.haveAllExamGoodEstimation());
    }

    /**
     * Тест семестра с пересдачами.
     */
    @Test
    void testSemesterWithRetakes() {
        final Semester semester = new Semester(LocalDate.of(2024, 1, 1));

        semester.addDiscipline(EXAM, true);
        semester.addGradeToDiscipline(EXAM, TWO, LocalDate.of(2024, 1, 15));
        semester.addGradeToDiscipline(EXAM, THREE, LocalDate.of(2024, 1, 20));
        semester.addGradeToDiscipline(EXAM, FOUR, LocalDate.of(2024, 1, 25));

        assertEquals(1, semester.getCountDiscipline());
        assertEquals(4, semester.getSumSemesterScore());
        assertTrue(semester.getDisciplines().get(0).isPassed());
    }
}