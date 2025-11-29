package org.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import org.example.academic.SubjectEntry;
import org.example.grading.Score;

/**
 * Класс семестра.
 */
public class Semester {
    private final LocalDate semesterDate;
    ArrayList<SubjectEntry> disciplines;
    Session session;

    /**
     * Конструктор класса семестр.
     */
    public Semester(LocalDate semesterDate) {
        this.semesterDate = semesterDate;
        disciplines = new ArrayList<>();
        this.session = new Session("Сессия " + semesterDate, semesterDate);
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

    public Session getSession() {
        return session;
    }

    public LocalDate getSemesterTime() {
        return semesterDate;
    }

    /**
     * Получение суммы баллов за семестр.
     */
    public int getSemesterScoreSum() {
        int sum = 0;

        for (SubjectEntry discipline : disciplines) {
            Integer estimation = discipline.getEstimation();
            if (estimation != null) {
                sum += estimation;
            }
        }

        for (SubjectEntry exam : session.getExams()) {
            Integer estimation = exam.getEstimation();
            if (estimation != null) {
                sum += estimation;
            }
        }

        return sum;
    }

    /**
     * Получение количества сданных предметов за семестр.
     */
    public int getPassedSubjectsCount() {
        int count = 0;

        for (SubjectEntry discipline : disciplines) {
            if (discipline.isPassed()) {
                count++;
            }
        }

        for (SubjectEntry exam : session.getExams()) {
            if (exam.isPassed()) {
                count++;
            }
        }

        return count;
    }
}