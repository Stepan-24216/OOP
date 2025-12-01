package org.example.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.example.academic.SubjectEntry;

/**
 * Класс студента.
 */
public class CreditBook {
    private final ArrayList<Semester> semesters;
    private final boolean budgetStudy;

    /**
     * Конструктор класса студент.
     */
    public CreditBook(boolean budgetStudy) {
        semesters = new ArrayList<>();
        this.budgetStudy = budgetStudy;
    }

    /**
     * Получение предиката обучается ли человек на бюджете.
     */
    public boolean getBudgetStudy() {
        return budgetStudy;
    }

    /**
     * Добавление семестра.
     */
    public void addSemesters(Semester newSemester) {
        semesters.add(newSemester);

        semesters.sort(Comparator.comparing(
            semester -> semester.getSession().getSessionDate(),
            Comparator.reverseOrder()
        ));
    }

    /**
     * Получение среднего балла студента.
     */
    public double getAvgAllSemesters() {
        int totalSum = 0;
        int totalCount = 0;

        for (Semester semester : semesters) {
            totalSum += semester.getSemesterScoreSum();
            totalCount += semester.getPassedSubjectsCount();
        }

        return totalCount > 0 ? (double) totalSum / totalCount : 0.0;
    }

    /**
     * Получение двух последних сессий по дате.
     */
    public List<Session> getLastTwoSessions() {
        List<Session> allSessions = new ArrayList<>();

        for (Semester semester : semesters) {
            allSessions.add(semester.getSession());
        }

        if (allSessions.size() <= 2) {
            return allSessions;
        } else {
            return allSessions.subList(0, 2);
        }
    }

    /**
     * Функция проверки возможности перевода на бюджет.
     */
    public boolean possibleStudyOnBudget() {
        if (budgetStudy) {
            return true;
        }

        List<Semester> lastTwoSemesters = getLastTwoSemesters();

        for (Semester semester : lastTwoSemesters) {
            if (!hasAllGoodGrades(semester)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Получение двух последних семестров по дате.
     */
    private List<Semester> getLastTwoSemesters() {
        return semesters.size() <= 2
            ? new ArrayList<>(semesters)
            : semesters.subList(0, 2);
    }

    /**
     * Проверяет, что все предметы семестра имеют хорошие оценки.
     */
    private boolean hasAllGoodGrades(Semester semester) {
        for (SubjectEntry discipline : semester.getDisciplines()) {
            Integer estimation = discipline.getEstimation();
            if (estimation == null || estimation <= 3) {
                return false;
            }
        }

        for (SubjectEntry exam : semester.getSession().getExams()) {
            Integer estimation = exam.getEstimation();
            if (estimation == null || estimation <= 3) {
                return false;
            }
        }

        return true;
    }

    /**
     * Функция проверки возможности получения красного диплома.
     */
    public boolean possibleGetRedDiploma(boolean qualifyingWorkIsExcellent) {
        if (!qualifyingWorkIsExcellent) {
            return false;
        }

        int countExcellentEvaluation = 0;
        int countEvaluation = 0;

        for (Semester semester : semesters) {
            countEvaluation += semester.getPassedSubjectsCount();
            if (!semester.haveAllGoodEstimation()) {
                return false;
            }
            for (SubjectEntry discipline : semester.getDisciplines()) {
                if (discipline.isPassed()) {
                    if (discipline.getEstimation() == 5) {
                        countExcellentEvaluation++;
                    }
                }
            }
            for (SubjectEntry exam : semester.getSession().getExams()) {
                if (exam.isPassed()) {
                    if (exam.getEstimation() == 5) {
                        countExcellentEvaluation++;
                    }
                }
            }
        }
        return countEvaluation > 0 && (countExcellentEvaluation * 100) / countEvaluation >= 75;
    }

    /**
     * Функция проверки возможности получения повышенной стипендии.
     */
    public boolean possibleIncreasedScholarship() {
        if (!budgetStudy) {
            return false;
        }

        List<Session> lastTwoSessions = getLastTwoSessions();

        for (Session session : lastTwoSessions) {
            for (SubjectEntry exam : session.getExams()) {
                Integer estimation = exam.getEstimation();
                if (estimation == null || estimation != 5) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Получение самого последнего семестра.
     */
    public Semester getCurrentSemester() {
        if (semesters.isEmpty()) {
            return null;
        }

        return semesters.get(0);
    }
}