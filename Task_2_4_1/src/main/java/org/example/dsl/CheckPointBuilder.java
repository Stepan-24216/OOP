package org.example.dsl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.example.domain.CheckPoint;

/**
 * DSL-билдер для описания контрольной точки в Groovy-конфиге.
 */
final class CheckPointBuilder {

    private static final DateTimeFormatter DMY = DateTimeFormatter.ofPattern("dd-MM-uuuu");

    private String name;
    private LocalDate date;

    private static LocalDate parseDate(String raw) {
        String value = raw == null ? "" : raw.trim();
        DateTimeParseException isoParseException;
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            isoParseException = e;
        }
        try {
            return LocalDate.parse(value, DMY);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "Неверный формат даты: '" + raw + "'. Используйте yyyy-MM-dd или dd-MM-yyyy.",
                isoParseException);
        }
    }

    /**
     * Устанавливает название контрольной точки.
     */
    void name(String name) {
        this.name = name;
    }

    /**
     * Устанавливает дату из строки в формате {@code yyyy-MM-dd} или {@code dd-MM-yyyy}.
     */
    void date(String date) {
        this.date = parseDate(date);
    }

    /**
     * Устанавливает дату напрямую из {@link LocalDate}.
     */
    void date(LocalDate date) {
        this.date = date;
    }

    /**
     * Собирает и возвращает объект {@link CheckPoint}.
     */
    CheckPoint build() {
        return new CheckPoint(name == null ? "checkpoint" : name, date);
    }
}
