package ru.sfedu.studentsystem.adminActivity.recycle.fragment;

import androidx.fragment.app.Fragment;

import ru.sfedu.studentsystem.model.Discipline;

public class DisciplineTeacherShortFragment extends Fragment {
    private String nameDiscipline;

    public DisciplineTeacherShortFragment(Discipline discipline){
        this.nameDiscipline = discipline.getName();
    }

    public String getNameDiscipline() {
        return nameDiscipline;
    }

    public void setNameDiscipline(String nameDiscipline) {
        this.nameDiscipline = nameDiscipline;
    }
}
