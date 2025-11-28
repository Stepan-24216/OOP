package org.example;

/**
 * Енам для оценок.
 */
public enum Score {
    TWO(2, "Неудовлетворительно"),
    THREE(3, "Удовлетворительно"),
    FOUR(4, "Хорошо"),
    FIVE(5, "Отлично"),
    PASS("Зачёт"),
    FAIL("Незачёт");

    private final Integer numericValue;
    private final String description;

    Score(Integer numericValue, String description) {
        this.numericValue = numericValue;
        this.description = description;
    }

    Score(String description) {
        this.numericValue = null;
        this.description = description;
    }

    /**
     * Получить числовое значение оценки.
     */
    public Integer getNumericValue() {
        return numericValue;
    }

    /**
     * Получить текстовое описание оценки.
     */
    public String getDescription() {
        return description;
    }
}