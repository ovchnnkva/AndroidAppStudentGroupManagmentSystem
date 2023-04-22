package ru.sfedu.studentsystem.studentActivities;

import androidx.fragment.app.Fragment;

public class SessiaFragment extends Fragment {
    private String name;
    private String date;
    private String location;
    private String teacher;

    SessiaFragment(String name, String date, String location,String teacher){
        this.name = name;
        this.date=date;
        this.location = location;
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
