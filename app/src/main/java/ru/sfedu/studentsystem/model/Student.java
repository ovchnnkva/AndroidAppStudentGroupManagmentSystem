package ru.sfedu.studentsystem.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Date;
import java.util.Objects;
@RequiresApi(api = Build.VERSION_CODES.N)
public class Student {
	private long id;
	private String uid;
    private String name;
	private Date birthday;
    private long recordBookId;
	private StudyGroup group;
	private long studyGroupId;


	public Student(){
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

	public long getRecordBookId() {
		return recordBookId;
	}

	public void setRecordBookId(long recordBookId) {
		this.recordBookId = recordBookId;
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
