package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import org.example.domain.Student;
import org.example.domain.SystemSettings;
import org.example.domain.Task;
import org.example.domain.TaskResult;
import org.example.runner.ScoreCalculator;
import org.junit.jupiter.api.Test;


class ScoreCalculatorTest {

    @Test
    void calculate_setsZeroForNotCompiledTask() {
        SystemSettings settings = new SystemSettings();
        ScoreCalculator calculator = new ScoreCalculator(settings);

        Task task = new Task("Task_1_1", "Task 1.1", 10, null, null);
        Student student = new Student("Stepan Efimov", "efimov", "https://example.com/repo.git");
        TaskResult result = new TaskResult(student, task);
        result.setCompiled(false);

        calculator.calculate(result, Path.of("/tmp/not-a-repo"), task.getId());

        assertEquals(0, result.getScore());
    }

    @Test
    void calculate_usesPassedRatioWhenTestsExist() {
        SystemSettings settings = new SystemSettings();

        Task task = new Task("Task_1_2", "Task 1.2", 10, null, null);
        Student student = new Student("Stepan Efimov", "efimov", "https://example.com/repo.git");
        TaskResult result = new TaskResult(student, task);
        result.setCompiled(true);
        result.setStyleOk(true);
        result.setTestsPassed(6);
        result.setTestsFailed(2);
        result.setTestsSkipped(2);
        ScoreCalculator calculator = new ScoreCalculator(settings);

        calculator.calculate(result, Path.of("/tmp/not-a-repo"), task.getId());

        assertEquals(6, result.getScore());
    }

    @Test
    void calculate_appliesBonusAndCapsByMaxPlusBonus() {
        SystemSettings settings = new SystemSettings();
        settings.setBonus("efimov", "Task_1_3", 5);

        Task task = new Task("Task_1_3", "Task 1.3", 10, null, null);
        Student student = new Student("Stepan Efimov", "efimov", "https://example.com/repo.git");
        TaskResult result = new TaskResult(student, task);
        result.setCompiled(true);
        result.setTestsPassed(10);
        result.setTestsFailed(0);
        result.setTestsSkipped(0);
        ScoreCalculator calculator = new ScoreCalculator(settings);

        calculator.calculate(result, Path.of("/tmp/not-a-repo"), task.getId());

        assertEquals(15, result.getScore());
    }

    @Test
    void toGrade_usesConfiguredThresholds() {
        SystemSettings settings = new SystemSettings();
        settings.setGradeThreshold("5", 8);
        settings.setGradeThreshold("4", 6);
        settings.setGradeThreshold("3", 4);

        ScoreCalculator calculator = new ScoreCalculator(settings);

        assertEquals("5", calculator.toGrade(9));
        assertEquals("4", calculator.toGrade(7));
        assertEquals("3", calculator.toGrade(4));
        assertEquals("2", calculator.toGrade(3));
    }

}

