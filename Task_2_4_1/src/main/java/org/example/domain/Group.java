package org.example.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Группа студентов курса.
 */
public record Group(String name, List<Student> students) {

    public Group(String name, List<Student> students) {
        this.name = Objects.requireNonNullElse(name, "");
        this.students = new ArrayList<>(students == null ? List.of() : students);
    }

    /**
     * Возвращает название группы.
     *
     * @return название группы
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * Возвращает список студентов группы.
     *
     * @return студенты группы
     */
    @Override
    public List<Student> students() {
        return Collections.unmodifiableList(students);
    }
}

