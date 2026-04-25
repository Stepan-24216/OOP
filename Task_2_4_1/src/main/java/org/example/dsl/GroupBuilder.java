package org.example.dsl;

import java.util.ArrayList;
import java.util.List;
import org.example.domain.Group;
import org.example.domain.Student;

/** DSL-билдер для описания группы студентов в Groovy-конфиге. */
final class GroupBuilder {

    private String name;
    private final List<Student> students = new ArrayList<>();

    GroupBuilder(String name) {
        this.name = name;
    }

    /** Устанавливает название группы. */
    void name(String name) { this.name = name; }

    /**
     * Добавляет студента в группу.
     *
     * @param fullName   полное имя студента
     * @param githubNick GitHub-ник
     * @param repoUrl    URL репозитория
     */
    void student(String fullName, String githubNick, String repoUrl) {
        students.add(new Student(fullName, githubNick, repoUrl));
    }

    /**
     * Собирает и возвращает объект {@link Group}.
     */
    Group build() {
        return new Group(name == null ? "group" : name, students);
    }
}
