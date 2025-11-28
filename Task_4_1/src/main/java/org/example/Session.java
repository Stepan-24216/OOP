package org.example;

import java.time.LocalDate;
import java.util.ArrayList;

public class Session {
    private final String sessionName;
    private final LocalDate sessionDate;
    ArrayList<SubjectEntry> exams;

    Session(String sessionName, LocalDate sessionDate) {
        this.exams = new ArrayList<>();
        this.sessionName = sessionName;
        this.sessionDate = sessionDate;
    }

    public void addExam(SubjectEntry exam) {
        this.exams.add(exam);
    }

    public ArrayList<SubjectEntry> getExams() {
        return this.exams;
    }

    public boolean haveAllExamGoodEstimation() {
        for (SubjectEntry discipline : exams) {
            Integer estimation = discipline.getEstimation();
            if (estimation == null || estimation <= 3) {
                return false;
            }
        }
        return true;
    }

    public String getSessionName() {
        return sessionName;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }
}