package org.example;

/**
 * Енам для дисциплин.
 */
public enum Discipline {
    TASK("Задание"),
    CONTROL("Контрольная работа"),
    COLLOQUIUM("Коллоквиум"),
    EXAM("Экзамен"),
    DIFFERENTIATED_CREDIT("Дифференцированный зачет"),
    TEST("Зачет"),
    PRACTICE_REPORT("Отчет по практике"),
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
