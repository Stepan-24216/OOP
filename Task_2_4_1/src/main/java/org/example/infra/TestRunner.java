package org.example.infra;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.example.domain.TaskResult;

/** Запускает тесты и собирает статистику из XML-отчётов JUnit. */
final class TestRunner {

    private final int timeoutSeconds;

    TestRunner(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    /**
     * Запускает тесты задачи и записывает результаты в {@code result}.
     *
     * @param taskDir папка с задачей студента
     * @param result  объект результата для заполнения
     */
    void runTests(Path taskDir, TaskResult result) {
        List<String> command = Files.isExecutable(taskDir.resolve("gradlew"))
                ? List.of("./gradlew", "--no-daemon", "test")
                : List.of("gradle", "test");

        boolean finished = runWithTimeout(taskDir, command);
        if (!finished) {
            result.setErrorMessage("Test execution timed out after " + timeoutSeconds + "s");
            result.setTestsPassed(0);
            result.setTestsFailed(0);
            result.setTestsSkipped(0);
            return;
        }

        Path testResultDir = taskDir.resolve("build/test-results/test");
        int[] counters = collectCounters(testResultDir);
        result.setTestsPassed(counters[0]);
        result.setTestsFailed(counters[1]);
        result.setTestsSkipped(counters[2]);

        if (counters[1] > 0 && result.getErrorMessage() == null) {
            result.setErrorMessage("Tests failed: " + counters[1]);
        }
    }

    /**
     * Запускает команду с ограничением по времени.
     *
     * @param dir     рабочая папка процесса
     * @param command команда для выполнения
     * @return true, если процесс завершился в отведённое время
     */
    private boolean runWithTimeout(Path dir, List<String> command) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executor.submit(() -> {
            try {
                ProcessBuilder pb = new ProcessBuilder(command);
                pb.directory(dir.toFile());
                pb.redirectErrorStream(true);
                Process process = pb.start();
                process.getInputStream().transferTo(OutputStream.nullOutputStream());
                return process.waitFor() == 0 || true;
            } catch (IOException | InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        });

        try {
            return future.get(timeoutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(true);
            return false;
        } finally {
            executor.shutdownNow();
        }
    }

    private int[] collectCounters(Path testResultDir) {
        if (!Files.isDirectory(testResultDir)) {
            return new int[]{0, 0, 0};
        }

        int tests = 0, failures = 0, skipped = 0;
        try (var stream = Files.list(testResultDir)) {
            for (Path file : stream.filter(p -> p.getFileName().toString().endsWith(".xml")).toList()) {
                String xml = Files.readString(file);
                tests    += extractInt(xml, "tests");
                failures += extractInt(xml, "failures");
                skipped  += extractInt(xml, "skipped");
            }
        } catch (IOException ignored) {
            return new int[]{0, 0, 0};
        }

        return new int[]{Math.max(0, tests - failures - skipped), failures, skipped};
    }

    private int extractInt(String xml, String attribute) {
        String needle = attribute + "=\"";
        int start = xml.indexOf(needle);
        if (start < 0) return 0;
        int valueStart = start + needle.length();
        int valueEnd = xml.indexOf('"', valueStart);
        if (valueEnd < 0) return 0;
        try {
            return Integer.parseInt(xml.substring(valueStart, valueEnd));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
