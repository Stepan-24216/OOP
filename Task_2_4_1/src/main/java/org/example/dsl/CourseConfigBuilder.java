package org.example.dsl;

import groovy.lang.Closure;

import java.util.List;
import org.example.config.CourseConfig;
import org.example.domain.CheckAssignment;

/**
 * Главный DSL-билдер для Groovy-конфига.
 * Обрабатывает блоки {@code tasks}, {@code groups}, {@code check}, {@code checkPoints} и {@code
 * settings}.
 */
public class CourseConfigBuilder {

    private final CourseConfig config = new CourseConfig();

    /**
     * Открывает блок со списком задач.
     *
     * @param closure замыкание с одним или несколькими блоками {@code task}
     */
    public void tasks(Closure<?> closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    /**
     * Описывает одну задачу внутри блока {@code tasks}.
     *
     * @param closure замыкание, которое настраивает {@link TaskBuilder}
     */
    public void task(Closure<?> closure) {
        TaskBuilder builder = new TaskBuilder();
        closure.setDelegate(builder);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        config.addTask(builder.build());
    }

    /**
     * Открывает блок со списком групп.
     *
     * @param closure замыкание с одним или несколькими блоками {@code group}
     */
    public void groups(Closure<?> closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    /**
     * Описывает одну группу внутри блока {@code groups}.
     *
     * @param closure замыкание, которое настраивает {@link GroupBuilder}
     */
    public void group(Closure<?> closure) {
        GroupBuilder builder = new GroupBuilder("");
        closure.setDelegate(builder);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        config.addGroup(builder.build());
    }

    /**
     * Задает, кому какие задачи проверять.
     *
     * @param studentNicks GitHub-ники студентов
     * @param taskIds      id задач для проверки
     */
    public void check(List<String> studentNicks, List<String> taskIds) {
        config.addAssignment(new CheckAssignment(studentNicks, taskIds));
    }

    /**
     * Открывает блок чекпоинтов.
     *
     * @param closure замыкание с одним или несколькими блоками {@code point}
     */
    public void checkPoints(Closure<?> closure) {
        closure.setDelegate(this);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }

    /**
     * Описывает один чекпоинт внутри блока {@code checkPoints}.
     *
     * @param closure замыкание, которое настраивает {@link CheckPointBuilder}
     */
    public void point(Closure<?> closure) {
        CheckPointBuilder builder = new CheckPointBuilder();
        closure.setDelegate(builder);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        config.addCheckPoint(builder.build());
    }

    /**
     * Открывает блок системных настроек.
     *
     * @param closure замыкание, которое настраивает {@link SettingsBuilder}
     */
    public void settings(Closure<?> closure) {
        SettingsBuilder builder = new SettingsBuilder();
        closure.setDelegate(builder);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        config.setSettings(builder.build());
    }

    /**
     * Возвращает готовую конфигурацию курса.
     */
    public CourseConfig build() {
        return config;
    }
}
