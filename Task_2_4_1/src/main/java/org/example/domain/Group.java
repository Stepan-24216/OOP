package org.example.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Группа студентов курса. */
public final class Group {

    private final String name;
    private final List<Student> students;

    public Group(String name, List<Student> students) {
        this.name = Objects.requireNonNullElse(name, "");
        this.students = new ArrayList<>(students == null ? List.of() : students);
    }

    /** @return название группы */
    public String getName() {
        return name;
    }

    /** @return студенты группы */
    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }
}

