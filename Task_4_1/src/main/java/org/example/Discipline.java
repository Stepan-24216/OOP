package org.example;

public class Discipline {
    private String name;
    private int estimation;
    private int avgEstimation;

    public Discipline(String name, int estimation) {
        this.name = name;
        this.estimation = estimation;
        this.avgEstimation = estimation;
    }

    public String getName() {
        return name;
    }

    public int getEstimation() {
        return estimation;
    }

    public void setEstimation(int estimation) {
        this.estimation = estimation;
    }

    public void setName(String name) {
        this.name = name;
    }
}
