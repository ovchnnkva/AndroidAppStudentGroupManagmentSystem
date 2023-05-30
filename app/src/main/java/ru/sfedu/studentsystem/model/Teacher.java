package ru.sfedu.studentsystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Teacher {
	private long id;
	private String name;
  	private List<Discipline> disciplines=new ArrayList<>();

	public Teacher (long id,String name) {
		this.id = id;
		this.name = name;
	};

	public Teacher(){}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
  		this.name = name;
  }

  	public String getName() {
  		return name;
  }

  	public void setDisciplines(List<Discipline> disciplines) {
  		this.disciplines = disciplines;
  }

  	public List<Discipline> getDisciplines() {
  		return disciplines;
  }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Teacher teacher = (Teacher) o;
		return id == teacher.id && Objects.equals(name, teacher.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString(){
		return name;
	}
}
