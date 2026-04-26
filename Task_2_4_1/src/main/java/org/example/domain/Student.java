package org.example.domain;

import java.util.Objects;

/**
 * Студент курса ООП.
 */
public record Student(String fullName, String githubNick, String repoUrl) {

    /**
     * Создает данные студента.
     */
    public Student(String fullName, String githubNick, String repoUrl) {
        this.fullName = Objects.requireNonNullElse(fullName, "");
        this.githubNick = Objects.requireNonNullElse(githubNick, "");
        this.repoUrl = Objects.requireNonNullElse(repoUrl, "");
    }

    /**
     * Возвращает полное имя студента.
     *
     * @return полное имя студента
     */
    @Override
    public String fullName() {
        return fullName;
    }

    /**
     * Возвращает GitHub-ник студента.
     *
     * @return GitHub-ник студента
     */
    @Override
    public String githubNick() {
        return githubNick;
    }

    /**
     * Возвращает ссылку на репозиторий студента.
     *
     * @return URL репозитория студента
     */
    @Override
    public String repoUrl() {
        return repoUrl;
    }
}
