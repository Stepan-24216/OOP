package org.example.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Системные настройки: таймауты, пороги оценок, бонусные баллы. */
public final class SystemSettings {

    private int testTimeoutSeconds = 120;
    private final Map<String, Integer> gradeThresholds = new LinkedHashMap<>();
    private final Map<String, Map<String, Integer>> bonuses = new LinkedHashMap<>();

    /** @return максимальное время выполнения одного теста в секундах */
    public int getTestTimeoutSeconds() { return testTimeoutSeconds; }

    /** Устанавливает таймаут выполнения тестов. */
    public void setTestTimeoutSeconds(int testTimeoutSeconds) {
        this.testTimeoutSeconds = testTimeoutSeconds;
    }

    /**
     * Задаёт минимальный балл для получения указанной оценки.
     *
     * @param grade     метка оценки, например "5"
     * @param threshold минимальный балл
     */
    public void setGradeThreshold(String grade, int threshold) {
        gradeThresholds.put(grade, threshold);
    }

    /** @return неизменяемая карта меток оценок и их порогов */
    public Map<String, Integer> getGradeThresholds() {
        return Collections.unmodifiableMap(gradeThresholds);
    }

    /**
     * Добавляет бонусные баллы студенту за конкретную задачу.
     *
     * @param githubNick GitHub-ник студента
     * @param taskId     идентификатор задачи
     * @param points     количество бонусных баллов
     */
    public void setBonus(String githubNick, String taskId, int points) {
        bonuses.computeIfAbsent(githubNick, k -> new LinkedHashMap<>()).put(taskId, points);
    }

    /**
     * Возвращает бонусные баллы студента за задачу, или 0 если их нет.
     *
     * @param githubNick GitHub-ник студента
     * @param taskId     идентификатор задачи
     */
    public int getBonusFor(String githubNick, String taskId) {
        return bonuses.getOrDefault(githubNick, Collections.emptyMap()).getOrDefault(taskId, 0);
    }
}
