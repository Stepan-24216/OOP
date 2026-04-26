package org.example.dsl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.example.domain.Task;

/**
 * DSL-билдер для описания задачи в Groovy-конфиге.
 */
final class TaskBuilder {

    private static final DateTimeFormatter DMY = DateTimeFormatter.ofPattern("dd-MM-uuuu");

    private String id;
    private String name;
    private int maxScore = 100;
    private LocalDate softDeadline;
    private LocalDate hardDeadline;

    /**
     * Разбирает дату из строки, поддерживая форматы {@code yyyy-MM-dd} и {@code dd-MM-yyyy}.
     */
    private static LocalDate parseDate(String raw) {
        String value = raw == null ? "" : raw.trim();
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException ignored) {
        }
        try {
            return LocalDate.parse(value, DMY);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "Неверный формат даты: '" + raw + "'. Используйте yyyy-MM-dd или dd-MM-yyyy.", e);
        }
    }

    /**
     * Устанавливает идентификатор задачи.
     */
    void id(String id) {
        this.id = id;
    }

    /**
     * Устанавливает отображаемое название задачи.
     */
    void name(String name) {
        this.name = name;
    }

    /**
     * Устанавливает максимальный балл за задачу.
     */
    void maxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * Устанавливает мягкий дедлайн из строки в формате {@code yyyy-MM-dd} или {@code dd-MM-yyyy}.
     */
    void softDeadline(String date) {
        this.softDeadline = parseDate(date);
    }

    /**
     * Устанавливает мягкий дедлайн напрямую из {@link LocalDate}.
     */
    void softDeadline(LocalDate date) {
        this.softDeadline = date;
    }

    /**
     * Устанавливает жёсткий дедлайн из строки в формате {@code yyyy-MM-dd} или {@code dd-MM-yyyy}.
     */
    void hardDeadline(String date) {
        this.hardDeadline = parseDate(date);
    }

    /**
     * Устанавливает жёсткий дедлайн напрямую из {@link LocalDate}.
     */
    void hardDeadline(LocalDate date) {
        this.hardDeadline = date;
    }

    /**
     * Собирает и возвращает объект {@link Task}.
     */
    Task build() {
        String safeId = id == null ? "task" : id;
        String safeName = name == null ? safeId : name;
        return new Task(safeId, safeName, maxScore, softDeadline, hardDeadline);
    }
}
