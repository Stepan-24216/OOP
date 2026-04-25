package org.example;

import org.example.config.CourseConfig;
import org.example.domain.Group;
import org.example.domain.Student;
import org.example.domain.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}

