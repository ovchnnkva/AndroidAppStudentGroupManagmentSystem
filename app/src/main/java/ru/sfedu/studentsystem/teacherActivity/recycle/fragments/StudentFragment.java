package ru.sfedu.studentsystem.teacherActivity.recycle.fragments;

import androidx.fragment.app.Fragment;

import ru.sfedu.studentsystem.model.Student;

public class StudentFragment extends Fragment {
    private String name;
    private String groupCode;
    private String birthday;
    private String specialization;
    private long studentId;
    private long groupId;

    private boolean fromStudyGroupDetail = false;
    public StudentFragment(Student student){
        this.name = student.getName();
        this.groupCode = student.getGroup().getGroupsCode();
        this.birthday = student.getBirthday();
        this.specialization = student.getGroup().getSpecialization();
        this.studentId = student.getId();
        this.groupId = student.getStudyGroupId();
    }
    public StudentFragment(Student student, String groupCode, String specialization){
        this.name = student.getName();
        this.groupCode = groupCode;
        this.birthday = student.getBirthday();
        this.specialization = specialization;
        this.studentId = student.getId();
        this.groupId = student.getStudyGroupId();
        fromStudyGroupDetail = true;
    }

    public boolean isFromStudyGroupDetail() {
        return fromStudyGroupDetail;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
