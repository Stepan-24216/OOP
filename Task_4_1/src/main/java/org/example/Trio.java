package org.example;

public class Trio<Subject, Score, flagExam> {
    private Discipline Subject;
    private int Score;
    private boolean flagExam;

    Trio(Discipline Subject, int Score, boolean flagExam) {
        this.Subject = Subject;
        this.Score = Score;
        this.flagExam = flagExam;
    }

    public Discipline getName() {
        return Subject;
    }

    public int getEstimation() {
        return Score;
    }

    public boolean getFlagExam() {
        return flagExam;
    }
}
