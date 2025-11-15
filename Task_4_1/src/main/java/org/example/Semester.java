package org.example;

import java.util.ArrayList;

public class Semester {
    ArrayList<Trio> disciplines;
    private int semesterNumber;

    Semester(int semesterNumber) {
        this.semesterNumber = semesterNumber;
        disciplines = new ArrayList<>();
    }

    public void addDiscipline(Discipline Subject, int Score, boolean flagExam) {
        Trio trio = new Trio(Subject, Score, flagExam);
        disciplines.add(trio);
    }

    public ArrayList<Trio> getDisciplines() {
        return disciplines;
    }

    public int getSemesterNumber() {
        return semesterNumber;
    }
}


