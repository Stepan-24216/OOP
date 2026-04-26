package org.example.domain;

import java.util.Objects;

/**
 * Студент курса ООП.
 */
public record Student(String fullName, String githubNick, String repoUrl) {

    public Student(String fullName, String githubNick, String repoUrl) {
        this.fullName = Objects.requireNonNullElse(fullName, "");
        this.githubNick = Objects.requireNonNullElse(githubNick, "");
        this.repoUrl = Objects.requireNonNullElse(repoUrl, "");
    }

    /**
     * @return полное имя студента
     */
    @Override
    public String fullName() {
        return fullName;
    }

    /**
     * @return GitHub-ник студента
     */
    @Override
    public String githubNick() {
        return githubNick;
    }

    /**
     * @return URL репозитория студента
     */
    @Override
    public String repoUrl() {
        return repoUrl;
    }
}
