package ru.sfedu.studentsystem.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Objects;
@RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
public class Discipline{
	private String name;
	private long teacherId;
	private long id;
	private Teacher teacher;

	public Discipline (long id, String name) throws IllegalArgumentException{
		this.id = id;
		if (!name.isEmpty())
			this.name = name;
		else {
			Log.e("DISCIPLINE","invalid name");
			throw new IllegalArgumentException("Invalid name");
		}
		Log.i("DISCIPLINE","discipline create "+this);
	};
	public Discipline(){Log.i("DISCIPLINE","discipline create");}

	//
	// Methods
	//


	//
	// Accessor methods
	//

	public void setName(String name) {
  		this.name = name;
  }

  	public String getName() {
  		return name;
  }

	public long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(long teacherId) {
		this.teacherId = teacherId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Discipline that = (Discipline) o;
		return teacherId == that.teacherId && id == that.id && name.equals(that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, teacherId, id);
	}

	@Override
	public String toString() {
		return "Discipline{" +
				"name='" + name + '\'' +
				", teacherId=" + teacherId +
				", id=" + id +
				'}';
	}
}
