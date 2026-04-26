package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.example.domain.CheckAssignment;
import org.junit.jupiter.api.Test;

class CheckAssignmentTest {

    @Test
    void constructor_copiesSourceLists() {
        List<String> students = new ArrayList<>(List.of("efimov"));
        List<String> tasks = new ArrayList<>(List.of("Task_1_1"));

        CheckAssignment assignment = new CheckAssignment(students, tasks);
        students.add("maslova");
        tasks.add("Task_2_1");

        assertEquals(List.of("efimov"), assignment.studentNicks());
        assertEquals(List.of("Task_1_1"), assignment.taskIds());
    }

    @Test
    void constructor_handlesNullListsAsEmpty() {
        CheckAssignment assignment = new CheckAssignment(null, null);

        assertTrue(assignment.studentNicks().isEmpty());
        assertTrue(assignment.taskIds().isEmpty());
    }

    @Test
    void accessors_returnUnmodifiableViews() {
        CheckAssignment assignment = new CheckAssignment(
            List.of("efimov"),
            List.of("Task_1_1")
        );

        assertThrows(UnsupportedOperationException.class, () -> assignment.studentNicks().add("x"));
        assertThrows(UnsupportedOperationException.class, () -> assignment.taskIds().add("x"));
    }
}

