package org.example.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Сводный отчёт по всем задачам одного студента.
 */
public final class StudentReport {

    private final Student student;
    private final List<TaskResult> taskResults = new ArrayList<>();

    public StudentReport(Student student) {
        this.student = student;
    }

    /**
     * @return студент, которому принадлежит отчёт
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @return неизменяемый список результатов по задачам
     */
    public List<TaskResult> getTaskResults() {
        return Collections.unmodifiableList(taskResults);
    }

    /**
     * Добавляет результат проверки задачи в отчёт.
     */
    public void addTaskResult(TaskResult taskResult) {
        taskResults.add(taskResult);
    }

    /**
     * @return сумма баллов по всем задачам
     */
    public int getTotalScore() {
        return taskResults.stream().mapToInt(TaskResult::getScore).sum();
    }
}
