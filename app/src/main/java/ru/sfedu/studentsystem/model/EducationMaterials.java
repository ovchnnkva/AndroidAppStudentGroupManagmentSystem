package ru.sfedu.studentsystem.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
public class EducationMaterials{

	protected String teachersFile="";
	protected String teacherComment = "";
	protected Teacher teacher;
	protected Long teacherId;

  	protected String name;
	protected Long studentId;
  	protected long id;

  	protected long disciplineID;
	protected Discipline discipline;

	protected String typeSemester;

		public EducationMaterials (long id, Teacher teacher, String name, Discipline discipline) {
			this.id = id;
			this.teacher = teacher;
			teacherId = teacher.getId();
			this.name = name;
			this.discipline = discipline;
			this.teacher = teacher;
			disciplineID = discipline.getId();
			Log.i("EDUCATION MATERIAL","create education material");
		}
		public EducationMaterials(){Log.i("EDUCATION MATERIAL","create education material");}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
		teacherId = teacher.getId();
	}

	public String getTypeSemester() {
		return typeSemester;
	}

	public void setTypeSemester(String typeSemester) {
		this.typeSemester = typeSemester;
	}

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
		return studentId;
	}

	public void setStudyGroupId(Long studyGroupId) {
		this.studentId = studyGroupId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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
