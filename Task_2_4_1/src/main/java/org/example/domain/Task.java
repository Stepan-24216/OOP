package org.example.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Лабораторная задача курса ООП.
 */
public final class Task {

    private final String id;
    private final String name;
    private final int maxScore;
    private final LocalDate softDeadline;
    private final LocalDate hardDeadline;

    /**
     * Создает задачу курса.
     */
    public Task(String id, String name, int maxScore, LocalDate softDeadline,
                LocalDate hardDeadline) {
        this.id = Objects.requireNonNullElse(id, "");
        this.name = Objects.requireNonNullElse(name, id);
        this.maxScore = maxScore;
        this.softDeadline = softDeadline;
        this.hardDeadline = hardDeadline;
    }

    /**
     * Возвращает идентификатор задачи.
     *
     * @return уникальный идентификатор задачи
     */
    public String getId() {
        return id;
    }

    /**
     * Возвращает отображаемое название задачи.
     *
     * @return отображаемое название задачи
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает максимальный балл за задачу.
     *
     * @return максимальный балл за задачу
     */
    public int getMaxScore() {
        return maxScore;
    }

    /**
     * Возвращает мягкий дедлайн задачи.
     *
     * @return мягкий дедлайн, после которого балл снижается вдвое
     */
    public LocalDate getSoftDeadline() {
        return softDeadline;
    }

    /**
     * Возвращает жёсткий дедлайн задачи.
     *
     * @return жёсткий дедлайн, после которого балл становится 0
     */
    public LocalDate getHardDeadline() {
        return hardDeadline;
    }
}
