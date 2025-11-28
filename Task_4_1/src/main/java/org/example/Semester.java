package org.example;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Класс семестра.
 */
public class Semester {
    private final LocalDate semesterNumber;
    ArrayList<SubjectEntry> disciplines;
    Session session;

    /**
     * Конструктор класса семестр.
     */
    Semester(LocalDate semesterNumber) {
        this.semesterNumber = semesterNumber;
        disciplines = new ArrayList<>();
        this.session = new Session("Сессия " + semesterNumber, semesterNumber);
    }

    /**
     * Добавление дисциплины.
     */
    public void addDiscipline(Discipline subject, boolean flagExam) {
        SubjectEntry subjectEntry = new SubjectEntry(subject, flagExam);
        if (flagExam) {
            disciplines.add(subjectEntry);
        } else {
            session.addExam(subjectEntry);
        }
    }

    /**
     * Добавление оценки к дисциплине.
     */
    public void addGradeToDiscipline(Discipline subject, Score score, LocalDate date) {
        for (SubjectEntry discipline : disciplines) {
            if (discipline.getSubject() == subject) {
                discipline.addGrade(score, date);
                return;
            }
        }
        for (SubjectEntry exam : session.getExams()) {
            if (exam.getSubject() == subject) {
                exam.addGrade(score, date);
                return;
            }
        }
    }

    /**
     * Получения массива дисциплин.
     */
    public ArrayList<SubjectEntry> getDisciplines() {
        return disciplines;
    }

    /**
     * Получение номера семестра.
     */
    public LocalDate getSemesterTime() {
        return semesterNumber;
    }

    /**
     * Получение оценки за семестр.
     */
    public int getSumSemesterScore() {
        int sum = 0;
        int count = 0;

        for (SubjectEntry discipline : disciplines) {
            Integer estimation = discipline.getEstimation();
            if (estimation != null) {
                sum += estimation;
                count++;
            }
        }
        for (SubjectEntry discipline : session.getExams()) {
            Integer estimation = discipline.getEstimation();
            if (estimation != null) {
                sum += estimation;
                count++;
            }
        }

        return count > 0 ? sum : 0;
    }

    /**
     * Получение количества дисциплин.
     */
    public int getCountDiscipline() {
        int count = 0;
        for (SubjectEntry discipline : disciplines) {
            if (discipline.isPassed()) {
                count++;
            }
        }
        for (SubjectEntry discipline : session.getExams()) {
            if (discipline.isPassed()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Все пятёрки.
     */
    public boolean haveAllFiveEstimation() {
        for (SubjectEntry discipline : disciplines) {
            Integer estimation = discipline.getEstimation();
            if (estimation == null || estimation != 5) {
                return false;
            }
        }
        for (SubjectEntry discipline : session.getExams()) {
            Integer estimation = discipline.getEstimation();
            if (estimation == null || estimation != 5) {
                return false;
            }
        }
        return true;
    }

    /**
     * Все оценки хорошие.
     */
    public boolean haveAllExamGoodEstimation() {
        return session.haveAllExamGoodEstimation();
    }

    public Session getSession() {
        return session;
    }
}