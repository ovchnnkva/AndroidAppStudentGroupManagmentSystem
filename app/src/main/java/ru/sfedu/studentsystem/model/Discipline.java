package ru.sfedu.studentsystem.model;

import android.util.Log;

import java.util.List;
import java.util.Objects;
public class Discipline{
	private String name;
	private long id;
	private List<Teacher> teachers;
	private String typeAttestation;
	private int maxScoreForSemester;

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

	public void appendTeacher(Teacher teacher){
		teachers.add(teacher);
	}
	public String getTypeAttestation() {
		return typeAttestation;
	}

	public void setTypeAttestation(String typeAttestation) {
		this.typeAttestation = typeAttestation;
	}

	public int getMaxScoreForSemester() {
		return maxScoreForSemester;
	}

	public void setMaxScoreForSemester(int maxScoreForSemester) {
		this.maxScoreForSemester = maxScoreForSemester;
	}

	public void setName(String name) {
  		this.name = name;
  }

  	public String getName() {
  		return name;
  }



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Discipline that = (Discipline) o;
		return id == that.id && name.equals(that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, id);
	}

	@Override
	public String toString() {
		return "Discipline{" +
				"name='" + name + '\'' +
				", id=" + id +
				'}';
	}
}
