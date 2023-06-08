package ru.sfedu.studentsystem.teacherActivity.recycle.fragments;

import androidx.fragment.app.Fragment;

public class StudentShortPerformanceFragment extends Fragment {
    private String nameDiscipline;
    private int percentPerformance;
    private String actualScores;
    private String typeAttestation;
    private String typeSemester;
    private long studentId;
    private long disciplineId;
    public StudentShortPerformanceFragment(String nameDiscipline, int percentPerformance, String typeAttestation){
        this.nameDiscipline = nameDiscipline;
        this.percentPerformance = percentPerformance;
        this.typeAttestation = typeAttestation;
    }


    public String getTypeAttestation() {
        return typeAttestation;
    }


    public void setTypeAttestation(String typeAttestation) {
        this.typeAttestation = typeAttestation;
    }



    public void setActualScore(int studentsScore, int semestersScoreMax){
        actualScores = "";
        actualScores += studentsScore+"/"+semestersScoreMax+"/100";
    }

    public String getActualScores(){
        return actualScores;
    }

    public String getNameDiscipline() {
        return nameDiscipline;
    }

    public void setNameDiscipline(String nameDiscipline) {
        this.nameDiscipline = nameDiscipline;
    }

    public int getPercentPerformance() {
        return percentPerformance;
    }

    public void setPercentPerformance(int percentPerformance) {
        this.percentPerformance = percentPerformance;
    }

    public String getTypeSemester() {
        return typeSemester;
    }

    public void setTypeSemester(String typeSemester) {
        this.typeSemester = typeSemester;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(long disciplineId) {
        this.disciplineId = disciplineId;
    }

    public void setActualScores(String actualScores) {
        this.actualScores = actualScores;
    }
}

