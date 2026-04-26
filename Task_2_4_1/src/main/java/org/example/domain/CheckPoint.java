package org.example.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Контрольная точка для среза оценок в отчёте.
 */
public record CheckPoint(String name, LocalDate date) {

    public CheckPoint(String name, LocalDate date) {
        this.name = Objects.requireNonNullElse(name, "");
        this.date = date;
    }

    /**
     * @return название контрольной точки
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * @return дата контрольной точки
     */
    @Override
    public LocalDate date() {
        return date;
    }
}

