package ru.sfedu.studentsystem.adminActivity.recycle.fragment;

import androidx.fragment.app.Fragment;

import ru.sfedu.studentsystem.model.Teacher;

public class TeacherFragment extends Fragment {
    private String name;
    private long teacherId;

    public TeacherFragment(Teacher teacher){
        this.name = teacher.getName();
        this.teacherId = teacher.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }
}
