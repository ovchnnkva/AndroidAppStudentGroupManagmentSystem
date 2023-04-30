package ru.sfedu.studentsystem.studentActivities.recycle.fragments;

import androidx.fragment.app.Fragment;

import ru.sfedu.studentsystem.model.Event;

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

    public SessiaFragment(Event event){
        this.name = event.getName();
        this.date = formmatingDate(event.getDate(), event.getTime());
        this.location = event.getLocation();
        this.teacher = event.getTeacher();
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

    private String formmatingDate(String date, String time){
        StringBuilder builderTime = new StringBuilder(time);

        return date + " " + builderTime.substring(0, 5);
    }
}
