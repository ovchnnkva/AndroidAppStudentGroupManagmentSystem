package com.hfad.studentsystemapp.studentActivities;

import androidx.fragment.app.Fragment;

public class LectionMaterialFragment extends Fragment {
    private String name;
    private String discipline;
    private String teacher;

    LectionMaterialFragment(String name, String discipline, String teacher){
        this.name = name;
        this.discipline = discipline;
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
