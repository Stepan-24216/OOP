package org.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.example.config.CourseConfig;
import org.example.domain.CheckAssignment;
import org.example.domain.CheckPoint;
import org.example.domain.Group;
import org.example.domain.Student;
import org.example.domain.SystemSettings;
import org.example.domain.Task;
import org.junit.jupiter.api.Test;


class CourseConfigTest {

    @Test
    void findStudent_returnsStudentFromAnyGroup() {
        CourseConfig config = new CourseConfig();

        Student first = new Student("Stepan Efimov", "efimov", "https://github.com/Stepan-24216/OOP");
        Student second = new Student("Alina Maslova", "maslova", "https://github.com/24216-Maslova-Alina/OOP");

        config.addGroup(new Group("G1", List.of(first)));
        config.addGroup(new Group("G2", List.of(second)));
        config.addTask(new Task("Task_1_1", "Task 1.1", 10, LocalDate.now(), LocalDate.now()));

        assertTrue(config.findStudent("efimov").isPresent());
        assertTrue(config.findStudent("maslova").isPresent());
        assertFalse(config.findStudent("unknown").isPresent());
    }

    @Test
    void collectionsAreFilledAndReadOnly() {
        CourseConfig config = new CourseConfig();
        Student student = new Student("Stepan Efimov", "efimov", "https://github.com/Stepan-24216/OOP");

        Task task = new Task("Task_1_1", "Task 1.1", 10, LocalDate.now(), LocalDate.now());
        Group group = new Group("G1", List.of(student));
        CheckAssignment assignment = new CheckAssignment(List.of("efimov"), List.of("Task_1_1"));
        CheckPoint checkPoint = new CheckPoint("CP1", LocalDate.now());

        config.addTask(task);
        config.addGroup(group);
        config.addAssignment(assignment);
        config.addCheckPoint(checkPoint);

        assertTrue(config.getTasks().containsKey("Task_1_1"));
        assertTrue(config.getGroups().containsKey("G1"));
        assertTrue(config.getAssignments().contains(assignment));
        assertTrue(config.getCheckPoints().contains(checkPoint));

        assertThrows(UnsupportedOperationException.class, () -> config.getTasks().put("x", task));
        assertThrows(UnsupportedOperationException.class, () -> config.getGroups().clear());
        assertThrows(UnsupportedOperationException.class, () -> config.getAssignments().add(assignment));
        assertThrows(UnsupportedOperationException.class, () -> config.getCheckPoints().add(checkPoint));
    }

    @Test
    void settingsCanBeReplaced() {
        CourseConfig config = new CourseConfig();
        SystemSettings custom = new SystemSettings();
        custom.setTestTimeoutSeconds(30);

        config.setSettings(custom);

        assertSame(custom, config.getSettings());
    }
}

