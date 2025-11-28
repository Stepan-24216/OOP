package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Предмет с историей оценок.
 */
public class SubjectEntry {
    private final Discipline subject;
    private final boolean flagExam;
    private final List<GradeRecord> gradeHistory;

    /**
     * Конструктор предмета.
     */
    public SubjectEntry(Discipline subject, boolean flagExam) {
        this.subject = subject;
        this.flagExam = flagExam;
        this.gradeHistory = new ArrayList<>();
    }

    /**
     * Добавление оценки с датой.
     */
    public void addGrade(Score score, LocalDate date) {
        gradeHistory.add(new GradeRecord(score, date));
    }

    /**
     * Получение последней положительной оценки.
     */
    public GradeRecord getLastPositiveGrade() {
        for (int i = gradeHistory.size() - 1; i >= 0; i--) {
            GradeRecord record = gradeHistory.get(i);
            if (isPositiveGrade(record.getScore())) {
                return record;
            }
        }
        return null;
    }

    /**
     * Получение последней оценки.
     */
    public GradeRecord getLastGrade() {
        if (gradeHistory.isEmpty()) {
            return null;
        }
        return gradeHistory.get(gradeHistory.size() - 1);
    }

    /**
     * Проверка, сдан ли предмет.
     */
    public boolean isPassed() {
        return getLastPositiveGrade() != null;
    }

    /**
     * Получение числового значения последней положительной оценки.
     */
    public Integer getEstimation() {
        GradeRecord lastPositive = getLastPositiveGrade();
        return lastPositive != null ? lastPositive.getScore().getNumericValue() : null;
    }

    /**
     * Получение даты последней положительной оценки.
     */
    public LocalDate getLastPositiveDate() {
        GradeRecord lastPositive = getLastPositiveGrade();
        return lastPositive != null ? lastPositive.getDate() : null;
    }

    /**
     * Получение истории оценок.
     */
    public List<GradeRecord> getGradeHistory() {
        return new ArrayList<>(gradeHistory);
    }

    /**
     * Получение дисциплины.
     */
    public Discipline getSubject() {
        return subject;
    }

    /**
     * Получение флага экзамена.
     */
    public boolean getFlagExam() {
        return flagExam;
    }

    private boolean isPositiveGrade(Score score) {
        return score != Score.FAIL
            && (score.getNumericValue() == null || score.getNumericValue() >= 3);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SubjectEntry)) {
            return false;
        }
        SubjectEntry other = (SubjectEntry) obj;
        return java.util.Objects.equals(this.subject, other.subject);
    }
}