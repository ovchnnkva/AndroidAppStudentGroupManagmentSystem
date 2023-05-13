package ru.sfedu.studentsystem.model;

import android.util.Log;

import java.util.Objects;


public class LectionMaterial extends EducationMaterials{

	public LectionMaterial(long id, Teacher teacher, String name, Discipline discipline) {
		super(id, teacher, name,discipline);
		Log.i("LECTION","create lection material");
	}
	public LectionMaterial(){Log.i("LECTION","create lection material");}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		LectionMaterial that = (LectionMaterial) o;
		return id == that.id;
	}
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id);
	}
}
