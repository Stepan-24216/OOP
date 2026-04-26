package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.domain.Student;
import org.example.domain.Task;
import org.example.domain.TaskResult;
import org.junit.jupiter.api.Test;

class TaskResultTest {

    @Test
    void gettersAndSetters_workAsExpected() {
        Student student = new Student("Stepan Efimov", "efimov", "https://example.com/repo.git");
        Task task = new Task("Task_1_1", "Task 1.1", 10, null, null);
        TaskResult result = new TaskResult(student, task);

        assertSame(student, result.getStudent());
        assertSame(task, result.getTask());
        assertFalse(result.isCompiled());
        assertFalse(result.isStyleOk());
        assertFalse(result.isDocsGenerated());
        assertEquals(0, result.getTestsPassed());
        assertEquals(0, result.getTestsFailed());
        assertEquals(0, result.getTestsSkipped());
        assertEquals(0, result.getScore());
        assertNull(result.getErrorMessage());

        result.setCompiled(true);
        result.setStyleOk(true);
        result.setDocsGenerated(true);
        result.setTestsPassed(7);
        result.setTestsFailed(2);
        result.setTestsSkipped(1);
        result.setScore(8);
        result.setErrorMessage("boom");

        assertTrue(result.isCompiled());
        assertTrue(result.isStyleOk());
        assertTrue(result.isDocsGenerated());
        assertEquals(7, result.getTestsPassed());
        assertEquals(2, result.getTestsFailed());
        assertEquals(1, result.getTestsSkipped());
        assertEquals(8, result.getScore());
        assertEquals("boom", result.getErrorMessage());
    }
}

