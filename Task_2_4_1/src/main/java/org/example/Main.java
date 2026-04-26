package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.example.config.ConfigLoader;
import org.example.config.CourseConfig;
import org.example.runner.CheckRunner;

/**
 * Точка входа приложения.
 * Загружает файл конфигурации и запускает проверку.
 */
public class Main {

    private static final String DEFAULT_SCRIPT = "checker.groovy";

    /**
     * Запускает приложение.
     *
     * @param args путь к конфигу; если не передан, используется {@value DEFAULT_SCRIPT}
     */
    public static void main(String[] args) {
        Path scriptPath = resolveScriptPath(args);

        ConfigLoader loader = new ConfigLoader();
        CourseConfig config = loader.load(scriptPath);

        CheckRunner runner = new CheckRunner(config);
        runner.run();
    }

    private static Path resolveScriptPath(String[] args) {
        if (args.length > 0) {
            Path provided = Paths.get(args[0]);
            if (provided.isAbsolute() && Files.exists(provided)) {
                return provided;
            }
            Path fromWorkDir =
                Paths.get(System.getProperty("user.dir")).resolve(args[0]).normalize();
            if (Files.exists(fromWorkDir)) {
                return fromWorkDir;
            }
            throw new IllegalArgumentException("Config script not found: " + args[0]);
        }

        Path workDir = Paths.get(System.getProperty("user.dir"));
        List<Path> candidates = List.of(
            workDir.resolve(DEFAULT_SCRIPT),
            workDir.resolve("src/main/resources/" + DEFAULT_SCRIPT),
            workDir.resolve("src/main/java/org/example/" + DEFAULT_SCRIPT)
        );

        for (Path candidate : candidates) {
            if (Files.exists(candidate)) {
                return candidate;
            }
        }

        throw new IllegalArgumentException(
            "Default config script checker.groovy not found. "
                + "Place it in project root or src/main/resources."
        );
    }
}
