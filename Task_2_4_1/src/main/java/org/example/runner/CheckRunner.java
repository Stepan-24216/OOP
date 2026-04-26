package org.example.runner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.example.config.CourseConfig;
import org.example.domain.CheckAssignment;
import org.example.domain.Student;
import org.example.domain.StudentReport;
import org.example.domain.Task;
import org.example.domain.TaskResult;
import org.example.infra.BuildRunner;
import org.example.infra.GitClient;
import org.example.infra.TestRunner;
import org.example.report.HtmlReportGenerator;

/**
 * Запускает полный цикл проверки: клон, сборка, тесты, подсчет баллов и отчет.
 */
public class CheckRunner {

    private static final Path WORK_DIR =
        Paths.get(System.getProperty("user.dir"), ".oop-checker-repos");
    private static final Path REPORT_FILE = Paths.get(
        System.getProperty("user.dir"), "build", "reports", "checker", "report.html"
    );

    private final CourseConfig config;
    private final GitClient gitClient;
    private final BuildRunner buildRunner;
    private final TestRunner testRunner;
    private final ScoreCalculator scoreCalculator;

    /**
     * Создает раннер с заданной конфигурацией курса.
     */
    public CheckRunner(CourseConfig config) {
        this.config = config;
        this.gitClient = new GitClient();
        this.buildRunner = new BuildRunner();
        this.testRunner = new TestRunner(config.getSettings().getTestTimeoutSeconds());
        this.scoreCalculator = new ScoreCalculator(config.getSettings());
    }

    /**
     * Выполняет все проверки и сохраняет HTML-отчет в файл.
     */
    public void run() {
        ensureWorkDir();
        List<StudentReport> reports = processAllAssignments();
        HtmlReportGenerator generator = new HtmlReportGenerator(config);
        String html = generator.generate(reports);
        saveReport(html);
        System.out.println("Report generated: " + REPORT_FILE);
    }

    /**
     * Обходит все задания на проверку и собирает отчеты по студентам.
     */
    private List<StudentReport> processAllAssignments() {
        Map<String, StudentReport> reportMap = new LinkedHashMap<>();

        for (CheckAssignment assignment : config.getAssignments()) {
            for (String nick : assignment.studentNicks()) {
                config.findStudent(nick).ifPresent(student -> {
                    StudentReport report = reportMap.computeIfAbsent(
                        nick, k -> new StudentReport(student));

                    Path repoDir = WORK_DIR.resolve(nick);
                    cloneStudentRepo(student, repoDir);

                    for (String taskId : assignment.taskIds()) {
                        Task task = config.getTasks().get(taskId);
                        if (task != null) {
                            TaskResult result = checkTask(student, task, repoDir);
                            report.addTaskResult(result);
                        }
                    }
                });
            }
        }

        return new ArrayList<>(reportMap.values());
    }

    /**
     * Клонирует репозиторий студента или обновляет его.
     */
    private void cloneStudentRepo(Student student, Path repoDir) {
        try {
            gitClient.cloneOrPull(student.repoUrl(), repoDir);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(
                "Failed to clone repo for " + student.githubNick() + ": " + e.getMessage());
        } catch (IOException e) {
            System.err.println(
                "Failed to clone repo for " + student.githubNick() + ": " + e.getMessage());
        }
    }

    /**
     * Проверяет одну задачу в репозитории одного студента.
     *
     * @param student студент, которого проверяем
     * @param task    задача для проверки
     * @param repoDir корневая папка локального репозитория студента
     * @return результат проверки задачи
     */
    private TaskResult checkTask(Student student, Task task, Path repoDir) {
        Path taskDir = repoDir.resolve(task.getId());
        TaskResult result = new TaskResult(student, task);

        if (!taskDir.toFile().exists()) {
            result.setErrorMessage("Task directory not found: " + task.getId());
            return result;
        }

        boolean compiled = buildRunner.compile(taskDir);
        result.setCompiled(compiled);

        if (!compiled) {
            result.setErrorMessage("Compilation failed");
            scoreCalculator.calculate(result, repoDir, task.getId());
            return result;
        }

        boolean styleOk = buildRunner.checkStyle(taskDir);
        result.setStyleOk(styleOk);

        boolean docsOk = buildRunner.generateDocs(taskDir);
        result.setDocsGenerated(docsOk);

        if (styleOk) {
            testRunner.runTests(taskDir, result);
        }

        scoreCalculator.calculate(result, repoDir, task.getId());
        return result;
    }

    /**
     * Создает рабочую папку для клонов репозиториев, если ее нет.
     */
    private void ensureWorkDir() {
        WORK_DIR.toFile().mkdirs();
    }

    /**
     * Сохраняет HTML-отчет в файл.
     */
    private void saveReport(String html) {
        try {
            Files.createDirectories(REPORT_FILE.getParent());
            Files.writeString(REPORT_FILE, html);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write report: " + REPORT_FILE, e);
        }
    }
}