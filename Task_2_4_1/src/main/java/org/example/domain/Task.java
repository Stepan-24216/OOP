package org.example.domain;

import java.time.LocalDate;
import java.util.Objects;

/** Лабораторная задача курса ООП. */
public final class Task {

    private final String id;
    private final String name;
    private final int maxScore;
    private final LocalDate softDeadline;
    private final LocalDate hardDeadline;

    public Task(String id, String name, int maxScore, LocalDate softDeadline, LocalDate hardDeadline) {
        this.id = Objects.requireNonNullElse(id, "");
        this.name = Objects.requireNonNullElse(name, id);
        this.maxScore = maxScore;
        this.softDeadline = softDeadline;
        this.hardDeadline = hardDeadline;
    }

    /** @return уникальный идентификатор задачи */
    public String getId() { return id; }

    /** @return отображаемое название задачи */
    public String getName() { return name; }

    /** @return максимальный балл за задачу */
    public int getMaxScore() { return maxScore; }

    /** @return мягкий дедлайн, после которого балл снижается вдвое */
    public LocalDate getSoftDeadline() { return softDeadline; }

    /** @return жёсткий дедлайн, после которого балл становится 0 */
    public LocalDate getHardDeadline() { return hardDeadline; }
}
