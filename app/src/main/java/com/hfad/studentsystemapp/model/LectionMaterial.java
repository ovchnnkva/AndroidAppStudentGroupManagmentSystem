package com.hfad.studentsystemapp.model;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Objects;


@RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
public class LectionMaterial extends EducationMaterials{
	public LectionMaterial(long id,String name, Discipline discipline) {
		super(id,name,discipline);
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
