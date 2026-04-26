package org.example.runner;


import java.nio.file.Path;
import java.time.LocalDate;
import org.example.domain.SystemSettings;
import org.example.domain.Task;
import org.example.domain.TaskResult;

/**
 * Считает итоговый балл за задачу.
 */
public class ScoreCalculator {

    private final SystemSettings settings;

    /**
     * Создает калькулятор с заданными настройками.
     */
    public ScoreCalculator(SystemSettings settings) {
        this.settings = settings;
    }

    /**
     * Считает и записывает балл в результат задачи.
     *
     * @param result  результат проверки задачи
     * @param repoDir корень локального git-репозитория студента
     * @param taskId  идентификатор задачи — подпапка внутри репозитория
     */
    public void calculate(TaskResult result, Path repoDir, String taskId) {
        if (!result.isCompiled()) {
            result.setScore(0);
            return;
        }

        Task task = result.getTask();
        int base = computeBaseScore(result, task.getMaxScore());

        int bonus = settings.getBonusFor(result.getStudent().githubNick(), task.getId());
        LocalDate submissionDate = getLastCommitDate(repoDir, taskId);
        int finalScore = applyDeadlinePenalty(base + bonus, task, submissionDate);

        result.setScore(Math.max(0, Math.min(finalScore, task.getMaxScore() + bonus)));
    }

    /**
     * Считает базовый балл по доле пройденных тестов.
     * Если тесты не запускались, даёт половину за компиляцию
     * и дополнительно половину от оставшегося за прохождение стиля.
     */
    private int computeBaseScore(TaskResult result, int maxScore) {
        int total = result.getTestsPassed() + result.getTestsFailed() + result.getTestsSkipped();
        if (total == 0) {
            int base = maxScore / 2;
            return result.isStyleOk() ? base + (maxScore - base) / 2 : base;
        }
        double ratio = (double) result.getTestsPassed() / total;
        return (int) Math.round(ratio * maxScore);
    }

    /**
     * Применяет штраф за просрочку.
     */
    private int applyDeadlinePenalty(int score, Task task, LocalDate submissionDate) {
        if (submissionDate == null) {
            return score;
        }
        if (task.getHardDeadline() != null && submissionDate.isAfter(task.getHardDeadline())) {
            return 0;
        }
        if (task.getSoftDeadline() != null && submissionDate.isAfter(task.getSoftDeadline())) {
            return score / 2;
        }
        return score;
    }

    /**
     * Берет дату последнего git-коммита, затрагивавшего подпапку задачи.
     * Запускает git из корня репозитория и фильтрует историю по пути задачи,
     * чтобы не захватывать коммиты других задач.
     *
     * @param repoDir корень git-репозитория студента
     * @param taskId  подпапка задачи внутри репозитория
     * @return дата последнего релевантного коммита, или {@code null} если определить не удалось
     */
    private LocalDate getLastCommitDate(Path repoDir, String taskId) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                "git", "log", "-1", "--format=%cs", "--", taskId
            );
            pb.directory(repoDir.toFile());
            Process process = pb.start();
            String output = new String(process.getInputStream().readAllBytes()).trim();
            process.waitFor();
            if (output.isEmpty()) {
                return null;
            }
            return LocalDate.parse(output);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Переводит сумму баллов в итоговую оценку.
     */
    public String toGrade(int totalScore) {
        return settings.getGradeThresholds().entrySet().stream()
            .filter(e -> totalScore >= e.getValue())
            .max((a, b) -> Integer.compare(a.getValue(), b.getValue()))
            .map(e -> e.getKey())
            .orElse("2");
    }
}