package org.example;

import java.util.ArrayList;

public class Student {
    private ArrayList<Semester> semesters;
    private boolean budgetStudy;

    public Student(boolean budgetStudy) {
        semesters = new ArrayList<>();
        this.budgetStudy = budgetStudy;
    }

    public boolean getBudgetStudy() {
        return budgetStudy;
    }

    public void addSemesters(Semester semester) {
        semesters.add(semester);
    }

    public double getAvgAllSemesters() {
        int sum = 0;
        int count = 0;
        for (Semester semester : semesters) {
            for (Trio discipline : semester.getDisciplines()) {
                sum += discipline.getEstimation();
                count++;
            }
        }
        return ((double) sum) / count;
    }

    public boolean possibleStudyOnBudget() {
        boolean hasBadEstimation = false;

        if (budgetStudy) {
            return true;
        }
        if (semesters.size() >= 2) {
            for (int i = semesters.size() - 2; i < semesters.size(); i++) {
                Semester semester = semesters.get(i);

                for (Trio discipline : semester.getDisciplines()) {
                    if (discipline.getFlagExam() &&
                        (discipline.getEstimation() == 2 || discipline.getEstimation() == 3)) {
                        hasBadEstimation = true;
                    }
                }
            }
        } else {
            for (Semester semester : semesters) {
                for (Trio discipline : semester.getDisciplines()) {
                    if (discipline.getFlagExam() &&
                        (discipline.getEstimation() == 2 || discipline.getEstimation() == 3)) {
                        hasBadEstimation = true;
                    }
                }
            }
        }

        return !hasBadEstimation;
    }

    public boolean possibleGetRedDiploma(boolean qualifyingWorkIsExcellent) {
        if (!qualifyingWorkIsExcellent) {
            return false;
        }
        int countExcellentEvaluation = 0;
        int countEvaluation = 0;

        for (Semester semester : semesters) {
            for (Trio discipline : semester.getDisciplines()) {
                if (discipline.getEstimation() == 3 || discipline.getEstimation() == 2) {
                    return false;
                }
                countEvaluation++;
                if (discipline.getEstimation() == 5) {
                    countExcellentEvaluation++;
                }
            }
        }
        if ((countExcellentEvaluation * 100) / countEvaluation < 75) {
            return false;
        }
        return true;
    }

    public boolean possibleIncreasedScholarship() {
        if (!budgetStudy) {
            return false;
        }
        for (Semester semester : semesters) {
            for (Trio discipline : semester.getDisciplines()) {
                if (discipline.getEstimation() != 5) {
                    return false;
                }
            }
        }
        return true;
    }
}
