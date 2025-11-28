package org.example;

import java.time.LocalDate;

/**
 * Класс для хранения оценки с датой.
 */
public class GradeRecord {
    private final Score score;
    private final LocalDate date;

    public GradeRecord(Score score, LocalDate date) {
        this.score = score;
        this.date = date;
    }

    public Score getScore() {
        return score;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getNumericValue() {
        return score.getNumericValue();
    }
}