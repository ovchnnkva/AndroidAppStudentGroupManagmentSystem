package ru.sfedu.studentsystem.studentActivities.recycle.fragments;

import android.util.Log;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.sfedu.studentsystem.model.LectionMaterial;
import ru.sfedu.studentsystem.model.PracticalMaterial;

public class MaterialFragment extends Fragment {
    private Long id;
    private String file="";
    private String name="";
    private String teacher="";
    private String discipline="";
    private String deadline="";
    private int maxScore=0;
    private int studentScore=0;
    private boolean isMade = false;
    private String appendScore = "";
    private String teacherComment = "";
    private boolean isPractical = false;

    public MaterialFragment(PracticalMaterial material){
        id = material.getId();
        name = material.getName();
        teacher =material.getTeacher().getName();
        discipline = material.getDiscipline().getName();
        deadline = dateFormatting(material.getDeadline());
        maxScore = material.getMaximumScore();
        studentScore = material.getStudentScore();
        isMade = material.isMade();
        appendScore = dateFormatting(material.getDateScoreAppend());
        teacherComment = material.getTeacherComment();
        isPractical = true;
    }
    public MaterialFragment(LectionMaterial material){
        id = material.getId();
        name = material.getName();
        teacher = material.getTeacher().getName();
        discipline = material.getDiscipline().getName();
        teacherComment = material.getTeacherComment();
        file = material.getTeachersFile();
    }

    public boolean isPractical() {
        return isPractical;
    }

    public void setPractical(boolean practical) {
        isPractical = practical;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAppendScore() {
        return appendScore;
    }

    public void setAppendScore(String appendScore) {
        this.appendScore = appendScore;
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

    public String getTeacherComment() {
        return teacherComment;
    }

    public void setTeacherComment(String teacherComment) {
        this.teacherComment = teacherComment;
    }
    private String dateFormatting(String dateString) {
        if((dateString != null) && !dateString.isEmpty()) {
            Log.d("PARSE APPEND DATE", dateString);
            SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
            SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            try {
                Date date = oldDateFormat.parse(dateString);
                return newDateFormat.format(date);
            } catch (ParseException e) {
                Log.d("DATEFORMAT", e.getMessage());
            }
        }
        return dateString;
    }
}
