package ru.sfedu.studentsystem.studentActivities.recycle.fragments;

import androidx.fragment.app.Fragment;

public class DetailPerformanceFragment extends Fragment {

    private String nameTask;
    private String scores;
    private String dateAppendScore;

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

    public String getDateAppendScore() {
        return dateAppendScore;
    }

    public void setDateAppendScore(String dateAppendScore) {
        this.dateAppendScore = dateAppendScore;
    }

}
