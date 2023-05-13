package ru.sfedu.studentsystem.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Student {
	private long id;
	private String uid;
    private String name;
	private Date birthday;
	private List<PracticalMaterial> scores;
	private StudyGroup group;
	private long studyGroupId;


	public Student(){
		scores = new ArrayList<>();
		Log.d("STUDENT", "create");
	}


	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

  	public void setName (String name) {
  		this.name = name;
  }

  	public String getName () {
  		return name;
  }

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public StudyGroup getGroup() {
		return group;
	}

	public void setGroup(StudyGroup group) {
		this.group = group;
	}

	public List<PracticalMaterial> getScores() {
		return scores;
	}

	public void setScores(List<PracticalMaterial> scores) {
		this.scores = scores;
	}

	public long getStudyGroupId() {
		return studyGroupId;
	}

	public void setStudyGroupId(long studyGroupId) {
		this.studyGroupId = studyGroupId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Student student = (Student) o;
		return id == student.id && name.equals(student.name) && birthday.equals(student.birthday) ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, birthday);
	}

	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", uid='" + uid + '\'' +
				", name='" + name + '\'' +
				", birthday=" + birthday +
				", studyGroupId=" + studyGroupId +
				'}';
	}
}
