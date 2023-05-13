package ru.sfedu.studentsystem.studentActivities.recycle.fragments;

import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;

import java.util.List;

import ru.sfedu.studentsystem.model.Teacher;
import ru.sfedu.studentsystem.studentActivities.DetailPerformanceActivity;

public class PerformanceFragment extends Fragment implements View.OnClickListener{
    private String nameDiscipline;
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


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), DetailPerformanceActivity.class);
        v.getContext().startActivity(intent);
    }
}
