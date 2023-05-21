package ru.sfedu.studentsystem.studentActivities.recycle.fragments;

import androidx.fragment.app.Fragment;

import java.util.List;

import ru.sfedu.studentsystem.model.Teacher;

public class PerformanceFragment extends Fragment{
    private String nameDiscipline;
    private long disciplineId;
    private long studentId;
    private String typeSemester;
    private String teachers;
    private int percentPerformance;
    private String actualScores;
    private String typeAttestation;

    public PerformanceFragment(String nameDiscipline, int percentPerformance, String typeAttestation){
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

    public void setTeachers(List<Teacher> teachersList){
        teachers = "";
        for (int i=0; i<teachersList.size()-1; i++){
            teachers += teachersList.get(i).getName()+",";
        }
        teachers += teachersList.get(teachersList.size()-1).getName();

    }

    public String getTeachers(){
        return teachers;
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

    public long getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(long disciplineId) {
        this.disciplineId = disciplineId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getTypeSemester() {
        return typeSemester;
    }

    public void setTypeSemester(String typeSemester) {
        this.typeSemester = typeSemester;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public void setActualScores(String actualScores) {
        this.actualScores = actualScores;
    }

}
