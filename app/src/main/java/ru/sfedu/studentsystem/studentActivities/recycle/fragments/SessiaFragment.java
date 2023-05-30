package ru.sfedu.studentsystem.studentActivities.recycle.fragments;

import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.stream.Collectors;

import ru.sfedu.studentsystem.model.Event;
import ru.sfedu.studentsystem.model.Teacher;

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

        String teachersStr = event.getTeachers().stream().map(e -> e.getName()).reduce(",", String::concat);
        this.teacher = teachersStr;
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

    public void setTeacher(List<Teacher> teachers) {
        String teachersStr = teachers.stream().map(Object::toString).collect(Collectors.joining(","));
        this.teacher = teachersStr;
    }

    private String formmatingDate(String date, String time){
        StringBuilder builderTime = new StringBuilder(time);

        return date + " " + builderTime.substring(0, 5);
    }
}
