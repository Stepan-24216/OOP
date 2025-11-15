package org.example;

/**
 * Енам для дисциплин.
 */
public enum Discipline {
    /**
     * Задание.
     */
    TASK("Задание"),

    /**
     * Контрольная работа.
     */
    CONTROL("Контрольная работа"),

    /**
     * Коллоквиум.
     */
    COLLOQUIUM("Коллоквиум"),

    /**
     * Экзамен.
     */
    EXAM("Экзамен"),

    /**
     * Дифференцированный зачет.
     */
    DIFFERENTIATED_CREDIT("Дифференцированный зачет"),

    /**
     * Зачет.
     */
    TEST("Зачет"),

    /**
     * Отчет по практике.
     */
    PRACTICE_REPORT("Отчет по практике"),

    /**
     * Защита ВКР.
     */
    PROTECTION_WRK("Защита ВКР");

    private final String disciplineName;

    /**
     * Конструктор для дисциплины.
     */
    Discipline(String string) {
        this.disciplineName = string;
    }

    /**
     * Получение имени.
     */
    public String getDisciplineName() {
        return disciplineName;
    }
}
