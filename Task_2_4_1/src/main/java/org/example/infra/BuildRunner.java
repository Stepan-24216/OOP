package org.example.infra;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Запускает Gradle-задачи компиляции, проверки стиля и генерации документации.
 */
public class BuildRunner {

    /**
     * Компилирует исходный код задачи.
     *
     * @param taskDir папка с задачей студента
     * @return true, если компиляция завершилась успешно
     */
    public boolean compile(Path taskDir) {
        return runGradleTask(taskDir, "classes", false);
    }

    /**
     * Проверяет соответствие кода Google Java Style.
     *
     * @param taskDir папка с задачей студента
     * @return true, если нарушений стиля не обнаружено
     */
    public boolean checkStyle(Path taskDir) {
        return runGradleTask(taskDir, "checkstyleMain", true);
    }

    /**
     * Генерирует Javadoc-документацию.
     *
     * @param taskDir папка с задачей студента
     * @return true, если документация сгенерирована без ошибок
     */
    public boolean generateDocs(Path taskDir) {
        return runGradleTask(taskDir, "javadoc", true);
    }

    /**
     * Запускает Gradle-задачу и проверяет её результат.
     */
    private boolean runGradleTask(Path taskDir, String task, boolean allowMissingTask) {
        List<String> command = Files.isExecutable(taskDir.resolve("gradlew"))
            ? List.of("./gradlew", "--no-daemon", task)
            : List.of("gradle", task);

        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(taskDir.toFile());
            pb.redirectErrorStream(true);
            Process process = pb.start();
            String output = readAll(process.getInputStream());
            int exitCode = process.waitFor();
            if (allowMissingTask && output.contains("Task '" + task + "' not found")) {
                return true;
            }
            return exitCode == 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Считывает весь вывод из InputStream в строку.
     */
    private String readAll(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        in.transferTo(out);
        return out.toString();
    }
}
