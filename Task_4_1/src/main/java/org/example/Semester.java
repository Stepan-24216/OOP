package org.example;

import java.util.ArrayList;

public class Semester {
    private final int semesterNumber;
    private ArrayList<Discipline> disciplines;

    public Semester(int semesterNumber) {
        this.semesterNumber = semesterNumber;
        disciplines = new ArrayList<>();
    }

    public void addDiscipline(Discipline discipline){
        disciplines.add(discipline);
    }

    public ArrayList<Discipline> getDisciplines(){
        return disciplines;
    }
}
