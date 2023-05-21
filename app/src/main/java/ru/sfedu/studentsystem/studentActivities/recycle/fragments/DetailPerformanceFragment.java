package ru.sfedu.studentsystem.studentActivities.recycle.fragments;

import android.util.Log;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailPerformanceFragment extends Fragment {

    private String nameTask;
    private String scores;
    private Date dateAppendScore;
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");

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

    public Date getDateAppendScore() {
        return dateAppendScore;
    }

    public void setDateAppendScore(Date dateAppendScore) {
        try {
            this.dateAppendScore = dateFormatting(dateAppendScore.toString());
        } catch (Exception e){
            Log.e("DETAILPERF", "wrong date format");
        }
    }

    private Date dateFormatting(String dateString) throws Exception {
        try {
            return simpleDateFormat.parse(dateString);
        }catch(ParseException e){
            throw new Exception("invalid date format");
        }

    }
}
