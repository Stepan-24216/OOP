package org.example.domain;

import java.util.Objects;

/** Студент курса ООП. */
public final class Student {

    private final String fullName;
    private final String githubNick;
    private final String repoUrl;

    public Student(String fullName, String githubNick, String repoUrl) {
        this.fullName = Objects.requireNonNullElse(fullName, "");
        this.githubNick = Objects.requireNonNullElse(githubNick, "");
        this.repoUrl = Objects.requireNonNullElse(repoUrl, "");
    }

    /** @return полное имя студента */
    public String getFullName() { return fullName; }

    /** @return GitHub-ник студента */
    public String getGithubNick() { return githubNick; }

    /** @return URL репозитория студента */
    public String getRepoUrl() { return repoUrl; }
}
