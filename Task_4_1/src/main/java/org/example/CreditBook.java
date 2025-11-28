package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
     * Получение константы обучается ли человек на бюджете.
     */
    public boolean getBudgetStudy() {
        return budgetStudy;
    }

    /**
     * Добавление семестра.
     */
    public void addSemesters(Semester semester) {
        semesters.add(semester);
    }

    /**
     * Получение среднего балла студента (только по последним положительным оценкам).
     */
    public double getAvgAllSemesters() {
        int sum = 0;
        int count = 0;

        for (Semester semester : semesters) {
            for (SubjectEntry discipline : semester.getDisciplines()) {
                Integer estimation = discipline.getEstimation();
                if (estimation != null) {
                    sum += estimation;
                    count++;
                }
            }
            for (SubjectEntry exam : semester.getSession().getExams()) {
                Integer estimation = exam.getEstimation();
                if (estimation != null) {
                    sum += estimation;
                    count++;
                }
            }
        }

        return count > 0 ? (double) sum / count : 0.0;
    }

    /**
     * Получение двух последних сессий по дате.
     */
    public List<Session> getLastTwoSessions() {
        return semesters.stream()
            .map(Semester::getSession)
            .sorted(Comparator.comparing(Session::getSessionDate).reversed())
            .limit(2)
            .collect(Collectors.toList());
    }

    /**
     * Функция проверки возможности перевода на бюджет.
     */
    public boolean possibleStudyOnBudget() {
        if (budgetStudy) {
            return true;
        }

        List<Session> lastTwoSessions = getLastTwoSessions();

        for (Session session : lastTwoSessions) {
            for (SubjectEntry exam : session.getExams()) {
                Integer estimation = exam.getEstimation();
                if (estimation == null || estimation <= 3) {
                    return false;
                }
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
            for (SubjectEntry discipline : semester.getDisciplines()) {
                if (discipline.isPassed()) {
                    Integer estimation = discipline.getEstimation();
                    if (estimation == 3 || estimation == 2) {
                        return false;
                    }
                    countEvaluation++;
                    if (estimation == 5) {
                        countExcellentEvaluation++;
                    }
                }
            }
            for (SubjectEntry exam : semester.getSession().getExams()) {
                if (exam.isPassed()) {
                    Integer estimation = exam.getEstimation();
                    if (estimation == 3 || estimation == 2) {
                        return false;
                    }
                    countEvaluation++;
                    if (estimation == 5) {
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
}