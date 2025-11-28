package org.example;

import java.time.LocalDate;

/**
 * Класс для хранения оценки с датой.
 */
public class GradeRecord {
    private final Score score;
    private final LocalDate date;

    /**
     * Конструктор записи об оценке.
     */
    public GradeRecord(Score score, LocalDate date) {
        this.score = score;
        this.date = date;
    }

    /**
     * Получение оценки.
     */
    public Score getScore() {
        return score;
    }

    /**
     * Получение даты получения оценки.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Получение числового значения оценки.
     */
    public Integer getNumericValue() {
        return score.getNumericValue();
    }
}