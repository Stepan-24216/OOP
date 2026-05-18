package org.example.config;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.io.IOException;
import java.nio.file.Path;
import org.example.dsl.CourseConfigBuilder;
import org.example.dsl.DelegatingMetaClass;

/**
 * Загружает и выполняет Groovy-конфиг.
 */
public class ConfigLoader {

    /**
     * Читает конфигурацию курса из Groovy-файла.
     *
     * @param scriptPath путь к файлу {@code checker.groovy}
     * @return готовая конфигурация курса
     */
    public CourseConfig load(Path scriptPath) {
        CourseConfigBuilder builder = new CourseConfigBuilder();
        evaluateScript(scriptPath, builder);
        return builder.build();
    }

    /**
     * Выполняет Groovy-скрипт и делегирует DSL-вызовы в билдер.
     *
     * @param scriptPath путь к Groovy-скрипту
     * @param builder    билдер, который принимает DSL-вызовы
     */
    public void evaluateScript(Path scriptPath, CourseConfigBuilder builder) {
        Binding binding = new Binding();
        binding.setVariable("importConfig", (ImportCallback) (path) -> {
            Path resolved = scriptPath.getParent().resolve(path);
            evaluateScript(resolved, builder);
        });

        GroovyShell shell = new GroovyShell(binding);
        Script script;
        try {
            script = shell.parse(scriptPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read config script: " + scriptPath, e);
        }

        script.setMetaClass(new DelegatingMetaClass(script.getMetaClass(), builder));
        script.setBinding(binding);
        script.run();
    }

    @FunctionalInterface
    private interface ImportCallback {
        void call(String path);
    }
}
