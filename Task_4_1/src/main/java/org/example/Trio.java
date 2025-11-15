package org.example;

/**
 * Класс Trio представляет тройку значений: дисциплина, оценка и флаг экзамена.
 */
public class Trio {
    private final Discipline subject;
    private final int score;
    private final boolean flagExam;

    /**
     * Конструктор тройки.
     *
     * @param subject дисциплина
     * @param score оценка
     * @param flagExam флаг экзамена
     */
    public Trio(Discipline subject, int score, boolean flagExam) {
        this.subject = subject;
        this.score = score;
        this.flagExam = flagExam;
    }

    /**
     * Получение дисциплины.
     *
     * @return дисциплина
     */
    public Discipline getSubject() {
        return subject;
    }

    /**
     * Получение оценки.
     *
     * @return оценка
     */
    public int getEstimation() {
        return score;
    }

    /**
     * Получение флага экзамена.
     *
     * @return флаг экзамена
     */
    public boolean getFlagExam() {
        return flagExam;
    }
}
