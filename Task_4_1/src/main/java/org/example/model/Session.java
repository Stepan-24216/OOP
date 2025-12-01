package org.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import org.example.academic.SubjectEntry;

/**
 * Класс сессии.
 */
public class Session {
    private final String sessionName;
    private final LocalDate sessionDate;
    ArrayList<SubjectEntry> exams;

    /**
     * Конструктор сессии.
     */
    public Session(String sessionName, LocalDate sessionDate) {
        this.exams = new ArrayList<>();
        this.sessionName = sessionName;
        this.sessionDate = sessionDate;
    }

    /**
     * Добавление экзамена.
     */
    public void addExam(SubjectEntry exam) {
        this.exams.add(exam);
    }

    /**
     * Получение списка экзаменов.
     */
    public ArrayList<SubjectEntry> getExams() {
        return this.exams;
    }

    /**
     * Проверка все ли оценки хорошие.
     */
    public boolean haveAllExamGoodEstimation() {
        for (SubjectEntry discipline : exams) {
            Integer estimation = discipline.getEstimation();
            if (estimation == null || estimation <= 3) {
                return false;
            }
        }
        return true;
    }

    /**
     * Получение названия сессии.
     */
    public String getSessionName() {
        return sessionName;
    }

    /**
     * Получение даты сессии.
     */
    public LocalDate getSessionDate() {
        return sessionDate;
    }
}