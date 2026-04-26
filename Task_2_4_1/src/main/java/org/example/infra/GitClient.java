package org.example.infra;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Обёртка над консольным git-клиентом для клонирования и обновления репозиториев.
 */
public final class GitClient {

    /**
     * Клонирует репозиторий или обновляет его, если папка уже существует.
     *
     * @param repoUrl URL удалённого репозитория
     * @param repoDir локальная папка для клона
     * @throws IOException          если git-процесс не удалось запустить или он завершился с
     * ошибкой
     * @throws InterruptedException если поток был прерван во время ожидания
     */
    public void cloneOrPull(String repoUrl, Path repoDir) throws IOException, InterruptedException {
        if (Files.isDirectory(repoDir.resolve(".git"))) {
            execute(repoDir.getParent(),
                List.of("git", "-C", repoDir.toString(), "pull", "--ff-only"));
        } else {
            Files.createDirectories(repoDir.getParent());
            execute(repoDir.getParent(), List.of("git", "clone", repoUrl, repoDir.toString()));
        }
    }


    private void execute(Path workDir, List<String> command)
        throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(workDir.toFile());
        pb.environment().put("GIT_TERMINAL_PROMPT", "0");
        pb.redirectErrorStream(true);

        Process process = pb.start();
        String output = readAll(process.getInputStream());
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new IOException("Command failed: " + String.join(" ", command) + "\n" + output);
        }
    }

    private String readAll(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        in.transferTo(out);
        return out.toString(StandardCharsets.UTF_8);
    }
}
