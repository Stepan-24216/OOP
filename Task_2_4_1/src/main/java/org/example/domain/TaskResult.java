package org.example.domain;

/**
 * Результат проверки одной задачи у одного студента.
 */
public final class TaskResult {

    private final Student student;
    private final Task task;
    private boolean compiled;
    private boolean styleOk;
    private boolean docsGenerated;
    private int testsPassed;
    private int testsFailed;
    private int testsSkipped;
    private int score;
    private String errorMessage;

    public TaskResult(Student student, Task task) {
        this.student = student;
        this.task = task;
    }

    /**
     * @return студент, у которого проверялась задача
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @return проверяемая задача
     */
    public Task getTask() {
        return task;
    }

    /**
     * @return true, если компиляция прошла успешно
     */
    public boolean isCompiled() {
        return compiled;
    }

    /**
     * Устанавливает результат компиляции.
     */
    public void setCompiled(boolean compiled) {
        this.compiled = compiled;
    }

    /**
     * @return true, если проверка стиля прошла успешно
     */
    public boolean isStyleOk() {
        return styleOk;
    }

    /**
     * Устанавливает результат проверки стиля.
     */
    public void setStyleOk(boolean styleOk) {
        this.styleOk = styleOk;
    }

    /**
     * @return true, если документация была успешно сгенерирована
     */
    public boolean isDocsGenerated() {
        return docsGenerated;
    }

    /**
     * Устанавливает результат генерации документации.
     */
    public void setDocsGenerated(boolean docsGenerated) {
        this.docsGenerated = docsGenerated;
    }

    /**
     * @return количество пройденных тестов
     */
    public int getTestsPassed() {
        return testsPassed;
    }

    /**
     * Устанавливает количество пройденных тестов.
     */
    public void setTestsPassed(int testsPassed) {
        this.testsPassed = testsPassed;
    }

    /**
     * @return количество упавших тестов
     */
    public int getTestsFailed() {
        return testsFailed;
    }

    /**
     * Устанавливает количество упавших тестов.
     */
    public void setTestsFailed(int testsFailed) {
        this.testsFailed = testsFailed;
    }

    /**
     * @return количество пропущенных тестов
     */
    public int getTestsSkipped() {
        return testsSkipped;
    }

    /**
     * Устанавливает количество пропущенных тестов.
     */
    public void setTestsSkipped(int testsSkipped) {
        this.testsSkipped = testsSkipped;
    }

    /**
     * @return итоговый балл за задачу
     */
    public int getScore() {
        return score;
    }

    /**
     * Устанавливает итоговый балл за задачу.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return сообщение об ошибке, если какой-либо шаг завершился неудачей
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Устанавливает сообщение об ошибке.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
