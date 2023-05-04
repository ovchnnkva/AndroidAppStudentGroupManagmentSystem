package ru.sfedu.studentsystem.model;

import java.util.Map;

public class StudentsRecordBook {
    private long id;
    private Map<Long, Map<String, Integer>> scores;
    private String typeSemester;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<Long, Map<String, Integer>> getScores() {
        return scores;
    }

    public void setScores(Map<Long, Map<String, Integer>> scores) {
        this.scores = scores;
    }

    public String getTypeSemester() {
        return typeSemester;
    }

    public void setTypeSemester(String typeSemester) {
        this.typeSemester = typeSemester;
    }
}
