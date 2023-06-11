package ru.sfedu.studentsystem.adminActivity.recycle.fragment;

import androidx.fragment.app.Fragment;

import ru.sfedu.studentsystem.model.StudyGroup;

public class StudyGroupFragment extends Fragment {
    private String code;
    private String specialization;
    private int course;
    private long groupId;

    public StudyGroupFragment(StudyGroup group){
        this.code = group.getGroupsCode();
        this.specialization = group.getSpecialization();
        this.course = group.getCourse();
        this.groupId = group.getId();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}
