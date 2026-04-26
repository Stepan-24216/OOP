package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.domain.Student;
import org.example.domain.StudentReport;
import org.example.domain.Task;
import org.example.domain.TaskResult;
import org.junit.jupiter.api.Test;

class StudentReportTest {

    @Test
    void report_storesStudentAndAggregatesTotalScore() {
        Student student = new Student("Stepan Efimov", "efimov", "https://example.com/repo.git");
        StudentReport report = new StudentReport(student);

        Task firstTask = new Task("Task_1_1", "Task 1.1", 10, null, null);
        Task secondTask = new Task("Task_1_2", "Task 1.2", 10, null, null);
        TaskResult firstResult = new TaskResult(student, firstTask);
        TaskResult secondResult = new TaskResult(student, secondTask);
        firstResult.setScore(6);
        secondResult.setScore(4);

        report.addTaskResult(firstResult);
        report.addTaskResult(secondResult);

        assertSame(student, report.getStudent());
        assertEquals(2, report.getTaskResults().size());
        assertEquals(10, report.getTotalScore());
    }

    @Test
    void taskResultsView_isUnmodifiable() {
        Student student = new Student("Stepan Efimov", "efimov", "https://example.com/repo.git");
        StudentReport report = new StudentReport(student);

        assertThrows(UnsupportedOperationException.class, () -> report.getTaskResults().clear());
    }
}

