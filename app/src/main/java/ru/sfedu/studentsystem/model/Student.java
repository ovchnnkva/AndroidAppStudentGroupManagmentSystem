package ru.sfedu.studentsystem.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@RequiresApi(api = Build.VERSION_CODES.N)
public class Student {
	private long id;
	private String uid;
    private String name;
	private Date birthday;
    private Map<Discipline,Integer> scores = new HashMap<>();
	private long studyGroupId;
	private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");


	public Student (long id,String name, String birthday, StudyGroup studyGroup) throws Exception {
		this.birthday=dateFormatting(birthday);
		this.id = id;
		this.name = name;
		studyGroupId = studyGroup.getId();
		scores = new HashMap<>();
		Log.i("STUDENT","Create student " + this);
	}
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
  	public void setScores (Map<Discipline,Integer> scores) {
  		this.scores = scores;
  }

 	 public Map<Discipline,Integer> getScores() {
  		return scores;
  }


	public long getStudyGroupId() {
		return studyGroupId;
	}

	public void setStudyGroupId(long studyGroupId) {
		this.studyGroupId = studyGroupId;
	}

	private Date dateFormatting(String date) throws Exception {
		try{
			return formatter.parse(date);
		}catch (ParseException e){
			throw new Exception("invalid date format");
		}
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
				", scores=" + scores +
				", studyGroupId=" + studyGroupId +
				'}';
	}
}
