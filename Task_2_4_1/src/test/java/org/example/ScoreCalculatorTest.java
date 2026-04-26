package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import org.example.domain.Student;
import org.example.domain.SystemSettings;
import org.example.domain.Task;
import org.example.domain.TaskResult;
import org.example.runner.ScoreCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ScoreCalculatorTest {

    private static final Student STUDENT =
        new Student("Stepan Efimov", "efimov", "https://example.com/repo.git");

    private static void run(Path workDir, String... command) throws IOException, InterruptedException {
        Process process = new ProcessBuilder(List.of(command))
            .directory(workDir.toFile())
            .redirectErrorStream(true)
            .start();
        String output = new String(process.getInputStream().readAllBytes());
        int code = process.waitFor();
        if (code != 0) {
            throw new IOException("Command failed (" + code + "): " + String.join(" ", command)
                + "\n" + output);
        }
    }

    private static void gitCommit(Path repoDir, String message, String date)
        throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
            "git", "commit", "-m", message, "--date", date
        );
        pb.directory(repoDir.toFile());
        pb.redirectErrorStream(true);
        pb.environment().put("GIT_AUTHOR_DATE", date + "T12:00:00");
        pb.environment().put("GIT_COMMITTER_DATE", date + "T12:00:00");
        Process process = pb.start();
        String output = new String(process.getInputStream().readAllBytes());
        int code = process.waitFor();
        if (code != 0) {
            throw new IOException("git commit failed: " + output);
        }
    }

    private static Path initRepoWithTaskCommit(Path baseDir, String taskId, String date)
        throws IOException, InterruptedException {
        run(baseDir, "git", "init");
        run(baseDir, "git", "config", "user.name", "Test User");
        run(baseDir, "git", "config", "user.email", "test@example.com");

        Path taskDir = baseDir.resolve(taskId);
        Files.createDirectories(taskDir);
        Files.writeString(taskDir.resolve("README.md"), "hello");

        run(baseDir, "git", "add", ".");
        gitCommit(baseDir, "task commit", date);
        return baseDir;
    }

    @Test
    void calculate_setsZeroForNotCompiledTask() {
        SystemSettings settings = new SystemSettings();
        ScoreCalculator calculator = new ScoreCalculator(settings);

        Task task = new Task("Task_1_1", "Task 1.1", 10, null, null);
        TaskResult result = new TaskResult(STUDENT, task);
        result.setCompiled(false);

        calculator.calculate(result, Path.of("/tmp/not-a-repo"), task.getId());

        assertEquals(0, result.getScore());
    }

    @Test
    void calculate_usesPassedRatioWhenTestsExist() {
        SystemSettings settings = new SystemSettings();

        Task task = new Task("Task_1_2", "Task 1.2", 10, null, null);
        TaskResult result = new TaskResult(STUDENT, task);
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
        TaskResult result = new TaskResult(STUDENT, task);
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

    @Test
    void calculate_withoutTestsAndWithoutStyle_givesHalfScore() {
        ScoreCalculator calculator = new ScoreCalculator(new SystemSettings());
        Task task = new Task("Task_2_1", "Task 2.1", 10, null, null);
        TaskResult result = new TaskResult(STUDENT, task);
        result.setCompiled(true);
        result.setStyleOk(false);

        calculator.calculate(result, Path.of("/tmp/not-a-repo"), task.getId());

        assertEquals(5, result.getScore());
    }

    @Test
    void calculate_withoutTestsButWithStyle_givesThreeQuartersScore() {
        ScoreCalculator calculator = new ScoreCalculator(new SystemSettings());
        Task task = new Task("Task_2_2", "Task 2.2", 10, null, null);
        TaskResult result = new TaskResult(STUDENT, task);
        result.setCompiled(true);
        result.setStyleOk(true);

        calculator.calculate(result, Path.of("/tmp/not-a-repo"), task.getId());

        assertEquals(7, result.getScore());
    }

    @Test
    void calculate_appliesSoftDeadlinePenalty(@TempDir Path tempDir) throws Exception {
        String taskId = "Task_soft";
        Path repo = initRepoWithTaskCommit(tempDir, taskId, "2026-04-10");

        ScoreCalculator calculator = new ScoreCalculator(new SystemSettings());
        Task task = new Task(
            taskId,
            "Task soft",
            10,
            LocalDate.of(2026, 4, 1),
            LocalDate.of(2026, 4, 30)
        );
        TaskResult result = new TaskResult(STUDENT, task);
        result.setCompiled(true);
        result.setTestsPassed(10);

        calculator.calculate(result, repo, taskId);

        assertEquals(5, result.getScore());
    }

    @Test
    void calculate_appliesHardDeadlinePenalty(@TempDir Path tempDir) throws Exception {
        String taskId = "Task_hard";
        Path repo = initRepoWithTaskCommit(tempDir, taskId, "2026-05-10");

        ScoreCalculator calculator = new ScoreCalculator(new SystemSettings());
        Task task = new Task(
            taskId,
            "Task hard",
            10,
            LocalDate.of(2026, 4, 1),
            LocalDate.of(2026, 4, 30)
        );
        TaskResult result = new TaskResult(STUDENT, task);
        result.setCompiled(true);
        result.setTestsPassed(10);

        calculator.calculate(result, repo, taskId);

        assertEquals(0, result.getScore());
    }

}

