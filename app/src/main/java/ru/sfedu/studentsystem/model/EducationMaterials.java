package ru.sfedu.studentsystem.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
public class EducationMaterials {
  	protected String teachersFile="";
  	protected String teacherComment = "";
  	protected String name;
  	protected Discipline discipline;
	  protected Long studyGroupId;
  	protected long id;
  	protected long disciplineID;

		public EducationMaterials (long id,String name, Discipline discipline) {
			this.id = id;
			this.name = name;
			this.discipline = discipline;
			disciplineID = discipline.getId();
			Log.i("EDUCATION MATERIAL","create education material");
		}
		public EducationMaterials(){Log.i("EDUCATION MATERIAL","create education material");}


  	public void setTeachersFile (String path) {
  		teachersFile= path;
  }

  	public String getTeachersFile () {
  		return teachersFile;
  }

  	public void setTeacherComment(String comment) throws Exception {
		if((teacherComment.length()+comment.length())<255){
  			teacherComment += comment;
	  	}else{
		  	throw new Exception("comment cannot be more than 255 characters");
		}
  	}
  	public String getTeacherComment() {
  		return teacherComment;
  }

  	public void setName(String name) {
  		this.name = name;
  }

  	public String getName () {
  		return name;
  }
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDisciplineID() {
		return disciplineID;
	}

	public void setDisciplineID(long disciplineID) {
		this.disciplineID = disciplineID;
	}


	public void setDiscipline (Discipline discipline) {
  		this.discipline = discipline;
		  disciplineID = discipline.getId();
  	}

  	public Discipline getDiscipline() {
  		return discipline;
  	}

	public Long getStudyGroupId() {
		return studyGroupId;
	}

	public void setStudyGroupId(Long studyGroupId) {
		this.studyGroupId = studyGroupId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EducationMaterials that = (EducationMaterials) o;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString(){
	  return discipline+" "+ name+" \n"+teachersFile+"\ncomment: "+teacherComment;
  }
}