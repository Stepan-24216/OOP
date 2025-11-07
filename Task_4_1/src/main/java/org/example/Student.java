package org.example;

import java.util.ArrayList;

public class Student {
    private ArrayList<Semester> semesters;

    public Student() {
        semesters = new ArrayList<>();
    }

    public void addSemesters(Semester semester) {
        semesters.add(semester);
    }

    public void getEstimation() {
        for (Semester semester: semesters) {
            for (Discipline discipline: semester.getDisciplines()) {
                System.out.println(discipline.getName()+ " " +discipline.getEstimation());
            }
        }
    }

//    public void getAvgEstimation() {
//        for (Discipline discipline: semesters[0]) {
//
//        }
//    }
}
