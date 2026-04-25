package org.example.domain;

import java.time.LocalDate;
import java.util.Objects;

/** Контрольная точка для среза оценок в отчёте. */
public final class CheckPoint {

    private final String name;
    private final LocalDate date;

    public CheckPoint(String name, LocalDate date) {
        this.name = Objects.requireNonNullElse(name, "");
        this.date = date;
    }

    /** @return название контрольной точки */
    public String getName() {
        return name;
    }

    /** @return дата контрольной точки */
    public LocalDate getDate() {
        return date;
    }
}

