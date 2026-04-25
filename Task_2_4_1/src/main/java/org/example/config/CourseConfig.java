package org.example.config;

import java.util.*;
import org.example.domain.CheckAssignment;
import org.example.domain.CheckPoint;
import org.example.domain.Group;
import org.example.domain.Student;
import org.example.domain.SystemSettings;
import org.example.domain.Task;

/**
 * Главный объект конфигурации курса.
 * Хранит задачи, группы, проверки, чекпоинты и настройки.
 */
public class CourseConfig {

    private final Map<String, Task> tasks = new LinkedHashMap<>();
    private final Map<String, Group> groups = new LinkedHashMap<>();
    private final List<CheckAssignment> assignments = new ArrayList<>();
    private final List<CheckPoint> checkPoints = new ArrayList<>();
    private SystemSettings settings = new SystemSettings();

    /** Добавляет задачу в конфигурацию. */
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    /** Добавляет группу в конфигурацию. */
    public void addGroup(Group group) {
        groups.put(group.getName(), group);
    }

    /** Добавляет запись, кого и какие задачи проверять. */
    public void addAssignment(CheckAssignment assignment) {
        assignments.add(assignment);
    }

    /** Добавляет чекпоинт для промежуточной оценки. */
    public void addCheckPoint(CheckPoint checkPoint) {
        checkPoints.add(checkPoint);
    }

    /** Обновляет системные настройки. */
    public void setSettings(SystemSettings settings) {
        this.settings = settings;
    }

    /** @return карта задач по id */
    public Map<String, Task> getTasks() { return Collections.unmodifiableMap(tasks); }

    /** @return карта групп по имени */
    public Map<String, Group> getGroups() { return Collections.unmodifiableMap(groups); }

    /** @return список всех проверок */
    public List<CheckAssignment> getAssignments() { return Collections.unmodifiableList(assignments); }

    /** @return список чекпоинтов по порядку */
    public List<CheckPoint> getCheckPoints() { return Collections.unmodifiableList(checkPoints); }

    /** @return текущие системные настройки */
    public SystemSettings getSettings() { return settings; }

    /**
     * Ищет студента по GitHub-нику среди всех групп.
     *
     * @param nick GitHub-ник студента
     * @return найденный студент или пустой результат
     */
    public Optional<Student> findStudent(String nick) {
        return groups.values().stream()
                .flatMap(g -> g.getStudents().stream())
                .filter(s -> s.getGithubNick().equals(nick))
                .findFirst();
    }
}
