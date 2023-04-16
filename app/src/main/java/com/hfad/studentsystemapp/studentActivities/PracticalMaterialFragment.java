package com.hfad.studentsystemapp.studentActivities;

public class PracticalMaterialFragment {
    private String file="";
    private String name="";
    private String teacher="";
    private String discipline="";
    private String deadline="";
    private int maxScore=0;
    private int studentScore=0;
    private boolean isMade = false;

    PracticalMaterialFragment(String file, String name, String teacher, String discipline, int maxScore, String deadline){
        this.file = file;
        this.name = name;
        this.teacher = teacher;
        this.discipline = discipline;
        this.maxScore = maxScore;
        this.deadline = deadline;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(int studentScore) {
        isMade = true;
        this.studentScore = studentScore;
    }

    public boolean isMade() {
        return isMade;
    }

    public void setMade(boolean made) {
        isMade = made;
    }
}
