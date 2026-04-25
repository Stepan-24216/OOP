package org.example.report;


import java.time.LocalDate;
import java.util.*;
import org.example.config.CourseConfig;
import org.example.domain.CheckPoint;
import org.example.domain.Group;
import org.example.domain.Student;
import org.example.domain.StudentReport;
import org.example.domain.Task;
import org.example.domain.TaskResult;
import org.example.runner.ScoreCalculator;

/**
 * Генерирует HTML-отчет по результатам проверки студентов.
 */
public class HtmlReportGenerator {

    private final CourseConfig config;
    private final ScoreCalculator scoreCalculator;

    /**
     * Создает генератор отчета.
     */
    public HtmlReportGenerator(CourseConfig config) {
        this.config = config;
        this.scoreCalculator = new ScoreCalculator(config.getSettings());
    }

    /**
     * Собирает полный HTML-отчет.
     */
    public String generate(List<StudentReport> reports) {
        StringBuilder sb = new StringBuilder();
        sb.append(htmlHead());
        sb.append("<body>\n");
        sb.append("<h1>OOP Course Check Report</h1>\n");
        sb.append("<p class='date'>Generated on: ").append(LocalDate.now()).append("</p>\n");

        Map<String, StudentReport> reportsByNick = new LinkedHashMap<>();
        for (StudentReport report : reports) {
            reportsByNick.put(report.getStudent().getGithubNick(), report);
        }

        for (Group group : config.getGroups().values()) {
            sb.append("<section class='group'>\n");
            sb.append("<h2 class='group-title'>Group: ").append(escape(group.getName())).append("</h2>\n");

            for (Student student : group.getStudents()) {
                StudentReport report = reportsByNick.remove(student.getGithubNick());
                if (report != null) {
                    sb.append(renderStudentSection(report));
                }
            }
            sb.append("</section>\n");
        }

        if (!reportsByNick.isEmpty()) {
            sb.append("<section class='group'>\n");
            sb.append("<h2 class='group-title'>Group: unassigned</h2>\n");
            for (StudentReport report : reportsByNick.values()) {
                sb.append(renderStudentSection(report));
            }
            sb.append("</section>\n");
        }

        sb.append(renderCheckPointSummary(reports));
        sb.append("</body>\n</html>");
        return sb.toString();
    }

    /**
     * Строит секцию отчета для одного студента.
     */
    private String renderStudentSection(StudentReport report) {
        Student student = report.getStudent();
        StringBuilder sb = new StringBuilder();
        sb.append("<section class='student'>\n");
        sb.append("<h2>").append(escape(student.getFullName()))
          .append(" (<a href='").append(escape(student.getRepoUrl())).append("'>")
          .append(escape(student.getGithubNick())).append("</a>)</h2>\n");
        sb.append("<table>\n");
        sb.append("<thead><tr>")
          .append("<th>Task</th><th>Compiled</th><th>Style</th><th>Docs</th>")
          .append("<th>Passed</th><th>Failed</th><th>Skipped</th><th>Score</th><th>Error</th>")
          .append("</tr></thead>\n<tbody>\n");

        for (TaskResult result : report.getTaskResults()) {
            sb.append(renderTaskRow(result));
        }

        sb.append("</tbody>\n</table>\n");
        sb.append("<p><strong>Total score: ").append(report.getTotalScore()).append("</strong>")
          .append(" — Grade: <strong>").append(scoreCalculator.toGrade(report.getTotalScore())).append("</strong></p>\n");
        sb.append("</section>\n");
        return sb.toString();
    }

    /**
     * Строит строку таблицы для одной задачи.
     */
    private String renderTaskRow(TaskResult result) {
        Task task = result.getTask();
        return "<tr>" +
               "<td>" + escape(task.getName()) + " (" + escape(task.getId()) + ")</td>" +
               "<td class='" + (result.isCompiled() ? "ok" : "fail") + "'>" + (result.isCompiled() ? "✓" : "✗") + "</td>" +
               "<td class='" + (result.isStyleOk() ? "ok" : "fail") + "'>" + (result.isStyleOk() ? "✓" : "✗") + "</td>" +
               "<td class='" + (result.isDocsGenerated() ? "ok" : "fail") + "'>" + (result.isDocsGenerated() ? "✓" : "✗") + "</td>" +
               "<td>" + result.getTestsPassed() + "</td>" +
               "<td>" + result.getTestsFailed() + "</td>" +
               "<td>" + result.getTestsSkipped() + "</td>" +
               "<td>" + result.getScore() + " / " + task.getMaxScore() + "</td>" +
               "<td>" + (result.getErrorMessage() != null ? escape(result.getErrorMessage()) : "") + "</td>" +
               "</tr>\n";
    }

    /**
     * Строит таблицу чекпоинтов с оценками по студентам.
     */
    private String renderCheckPointSummary(List<StudentReport> reports) {
        if (config.getCheckPoints().isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        sb.append("<section class='checkpoints'>\n");
        sb.append("<h2>Checkpoint Summary</h2>\n");
        sb.append("<table>\n<thead><tr><th>Student</th>");
        for (CheckPoint cp : config.getCheckPoints()) {
            sb.append("<th>").append(escape(cp.getName())).append("<br/><small>")
              .append(cp.getDate()).append("</small></th>");
        }
        sb.append("<th>Final Grade</th></tr></thead>\n<tbody>\n");

        for (StudentReport report : reports) {
            sb.append("<tr><td>").append(escape(report.getStudent().getFullName())).append("</td>");
            for (CheckPoint cp : config.getCheckPoints()) {
                int scoreAtCp = scoreAtCheckPoint(report, cp);
                sb.append("<td>").append(scoreCalculator.toGrade(scoreAtCp))
                  .append(" (").append(scoreAtCp).append(")</td>");
            }
            sb.append("<td>").append(scoreCalculator.toGrade(report.getTotalScore())).append("</td>");
            sb.append("</tr>\n");
        }

        sb.append("</tbody>\n</table>\n</section>\n");
        return sb.toString();
    }

    /**
     * Считает баллы студента на дату чекпоинта.
     */
    private int scoreAtCheckPoint(StudentReport report, CheckPoint cp) {
        return report.getTaskResults().stream()
                .filter(r -> {
                    LocalDate deadline = r.getTask().getSoftDeadline();
                    return deadline == null || !deadline.isAfter(cp.getDate());
                })
                .mapToInt(TaskResult::getScore)
                .sum();
    }

    /**
     * Возвращает начало HTML со стилями.
     */
    private String htmlHead() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                <meta charset="UTF-8"/>
                <title>OOP Checker Report</title>
                <style>
                  body { font-family: sans-serif; margin: 2rem; }
                  h1 { color: #333; }
                  h2 { color: #555; border-bottom: 1px solid #ccc; padding-bottom: 4px; }
                  table { border-collapse: collapse; width: 100%; margin-bottom: 1rem; }
                  th, td { border: 1px solid #ccc; padding: 6px 10px; text-align: left; }
                  th { background: #f0f0f0; }
                  .ok { color: green; font-weight: bold; }
                  .fail { color: red; font-weight: bold; }
                  .student { margin-bottom: 2rem; }
                  .date { color: gray; font-size: 0.9rem; }
                  section.checkpoints { margin-top: 2rem; }
                  .group { margin-bottom: 2rem; }
                  .group-title { border: none; margin-top: 2rem; color: #1f4b8f; }
                </style>
                </head>
                """;
    }

    /**
     * Экранирует спецсимволы HTML.
     */
    private String escape(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;");
    }
}
