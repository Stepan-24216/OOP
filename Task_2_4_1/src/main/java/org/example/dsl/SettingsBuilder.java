package org.example.dsl;

import org.example.domain.SystemSettings;

/** DSL-билдер для описания системных настроек в Groovy-конфиге. */
final class SettingsBuilder {

    private final SystemSettings settings = new SystemSettings();

    /** Устанавливает максимальное время выполнения тестов в секундах. */
    void testTimeout(int seconds) { settings.setTestTimeoutSeconds(seconds); }

    /**
     * Задаёт минимальный балл для получения указанной оценки.
     *
     * @param grade     метка оценки, например {@code "5"}
     * @param threshold минимальный балл включительно
     */
    void gradeThreshold(String grade, int threshold) { settings.setGradeThreshold(grade, threshold); }

    /**
     * Начисляет бонусные баллы студенту за конкретную задачу.
     *
     * @param githubNick GitHub-ник студента
     * @param taskId     идентификатор задачи
     * @param points     количество бонусных баллов
     */
    void bonus(String githubNick, String taskId, int points) { settings.setBonus(githubNick, taskId, points); }

    /**
     * Собирает и возвращает объект {@link SystemSettings}.
     */
    SystemSettings build() { return settings; }
}
