package org.example;

/**
 * Класс Trio представляет тройку значений: дисциплина, оценка и флаг экзамена.
 */
public class Trio {
    private Discipline Subject;
    private int Score;
    private boolean flagExam;

    /**
     * Конструктор тройки.
     */
    Trio(Discipline Subject, int Score, boolean flagExam) {
        this.Subject = Subject;
        this.Score = Score;
        this.flagExam = flagExam;
    }

    /**
     * Получение имени дисциплины.
     */
    public Discipline getName() {
        return Subject;
    }

    /**
     * Получение оценки.
     */
    public int getEstimation() {
        return Score;
    }

    /**
     * Поулчение флага экзамен ли наша дисциплина.
     */
    public boolean getFlagExam() {
        return flagExam;
    }
}
