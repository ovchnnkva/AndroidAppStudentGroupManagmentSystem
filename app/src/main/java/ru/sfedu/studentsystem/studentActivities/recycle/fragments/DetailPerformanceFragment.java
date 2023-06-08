package ru.sfedu.studentsystem.studentActivities.recycle.fragments;

import android.util.Log;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import ru.sfedu.studentsystem.Constants;
import ru.sfedu.studentsystem.model.PracticalMaterial;

public class DetailPerformanceFragment extends Fragment {
    private PracticalMaterial material;

    private String nameTask;
    private String scores;
    private int studentScore;
    private int maxScore;
    private String dateAppendScore;
    private Constants.ROLES role;

    public DetailPerformanceFragment(PracticalMaterial material, Constants.ROLES role) {
        this.material = material;
        this.nameTask = material.getName();
        this.studentScore = material.getStudentScore();
        this.maxScore = material.getMaximumScore();
        this.scores = material.getStudentScore()+" / "+material.getMaximumScore();
        try {
            this.dateAppendScore = dateFormattingAppendScore(material.getDateScoreAppend());
        } catch (Exception e){
            Log.e("PARSE", e.getMessage());
        }
        this.role = role;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
        setScores(studentScore, maxScore);
    }

    public int getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(int studentScore) {
        this.studentScore = studentScore;
        setScores(studentScore, maxScore);
    }

    public Constants.ROLES getRole() {
        return role;
    }

    public void setRole(Constants.ROLES role) {
        this.role = role;
    }

    public PracticalMaterial getMaterial() {
        return material;
    }

    public void setMaterial(PracticalMaterial material) {
        this.material = material;
        nameTask = material.getName();
        scores = material.getStudentScore() + " / " + material.getMaximumScore();
        dateAppendScore = material.getDateScoreAppend();
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(int studentScore, int maxScore) {
        scores = studentScore+" / "+maxScore;
    }
    public void setScores(int scores){
        this.scores = Objects.toString(scores);
    }
    public String getDateAppendScore() {
        return dateAppendScore;
    }

    public void setDateAppendScore(String dateAppendScore) {
        try {
            this.dateAppendScore = dateFormattingAppendScore(dateAppendScore);
        } catch (Exception e){
            Log.e("PARSE", e.getMessage());
        }
    }

    private String dateFormattingAppendScore(String dateString) throws Exception {
        Log.d("PARSE APPEND DATE", dateString);
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        try {
            Date date = oldDateFormat.parse(dateString);
            return newDateFormat.format(date);
        }catch(ParseException e){
            throw new Exception("invalid date format");
        }
    }


}
