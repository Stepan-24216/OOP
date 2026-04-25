package org.example;

import org.example.domain.Task;
import org.example.domain.TaskBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskBuilderTest {

    @Test
    void build_usesDefaultsWhenNameIsMissing() {
        TaskBuilder builder = new TaskBuilder();
        builder.id("Task_1_1");

        Task task = builder.build();

        assertEquals("Task_1_1", task.getId());
        assertEquals("Task_1_1", task.getName());
        assertEquals(100, task.getMaxScore());
    }

    @Test
    void deadlines_parseIsoAndDmyFormats() {
        TaskBuilder builder = new TaskBuilder();
        builder.id("Task_1_2");
        builder.maxScore(10);
        builder.softDeadline("2025-09-20");
        builder.hardDeadline("27-09-2025");

        Task task = builder.build();

        assertEquals(LocalDate.of(2025, 9, 20), task.getSoftDeadline());
        assertEquals(LocalDate.of(2025, 9, 27), task.getHardDeadline());
    }

    @Test
    void softDeadline_throwsForUnsupportedFormat() {
        TaskBuilder builder = new TaskBuilder();

        assertThrows(IllegalArgumentException.class, () -> builder.softDeadline("2025/09/20"));
    }
}

