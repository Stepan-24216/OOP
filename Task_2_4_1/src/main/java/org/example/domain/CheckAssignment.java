package org.example.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Кого проверяем и какие задачи проверяем. */
public final class CheckAssignment {

    private final List<String> studentNicks;
    private final List<String> taskIds;

    public CheckAssignment(List<String> studentNicks, List<String> taskIds) {
        this.studentNicks = new ArrayList<>(studentNicks == null ? List.of() : studentNicks);
        this.taskIds = new ArrayList<>(taskIds == null ? List.of() : taskIds);
    }

    /** @return список GitHub-ников студентов */
    public List<String> getStudentNicks() {
        return Collections.unmodifiableList(studentNicks);
    }

    /** @return список id задач */
    public List<String> getTaskIds() {
        return Collections.unmodifiableList(taskIds);
    }
}

