package org.example;

import java.util.ArrayList;

/**
 * Класс семестра.
 */
public class Semester {
    ArrayList<Trio> disciplines;
    private int semesterNumber;

    /**
     * Конструктор класса семестр.
     */
    Semester(int semesterNumber) {
        this.semesterNumber = semesterNumber;
        disciplines = new ArrayList<>();
    }

    /**
     * Добавление дисциплины.
     */
    public void addDiscipline(Discipline subject, int score, boolean flagExam) {
        Trio trio = new Trio(subject, score, flagExam);
        disciplines.add(trio);
    }

    /**
     * Получения массива дисциплин.
     */
    public ArrayList<Trio> getDisciplines() {
        return disciplines;
    }

    /**
     * Получение номера семестра.
     */
    public int getSemesterNumber() {
        return semesterNumber;
    }
}


