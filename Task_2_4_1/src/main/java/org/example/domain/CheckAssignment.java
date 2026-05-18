package org.example.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Кого проверяем и какие задачи проверяем.
 */
public record CheckAssignment(List<String> studentNicks, List<String> taskIds) {

    public CheckAssignment(List<String> studentNicks, List<String> taskIds) {
        this.studentNicks = new ArrayList<>(studentNicks == null ? List.of() : studentNicks);
        this.taskIds = new ArrayList<>(taskIds == null ? List.of() : taskIds);
    }

    /**
     * Возвращает список GitHub-ников студентов.
     *
     * @return список GitHub-ников студентов
     */
    @Override
    public List<String> studentNicks() {
        return Collections.unmodifiableList(studentNicks);
    }

    /**
     * Возвращает список идентификаторов задач.
     *
     * @return список id задач
     */
    @Override
    public List<String> taskIds() {
        return Collections.unmodifiableList(taskIds);
    }
}

