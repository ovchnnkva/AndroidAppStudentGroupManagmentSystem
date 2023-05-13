package ru.sfedu.studentsystem.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class PracticalMaterial extends EducationMaterials {

	private String studentFile ="";
	private String studentComment = "";
	private int maximumScore = 0;
	private int studentScore = 0;
	private Date deadline;
	private boolean isMade = false;
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");

	public PracticalMaterial (long id,Teacher teacher, String name, Discipline discipline, String deadline, int maximumScore) throws Exception {
		super(id,teacher,name,discipline);
		this.deadline = dateFormatting(deadline);
		checkExceedingMaximumScore(maximumScore);
		this.maximumScore = maximumScore;
		Log.i("PRACTICAL","create practical material");
	}

	public PracticalMaterial(){Log.i("PRACTICAL","create practical material");}

  	public void setMaximumScore (int maximumScore) {
		  this.maximumScore = maximumScore;
  	}

  	public int getMaximumScore () {
		  return maximumScore;
	}

  	public void setStudentScore(int studentScore) {
  		this.studentScore = studentScore;
  	}

  	public int getStudentScore() {
  		return studentScore;
  	}

  	public void setStudentComment(String comment) throws Exception {
		if(studentComment.length()+comment.length()<255)
  			studentComment += comment;
	  	else
			throw new Exception("comment cannot be more than 255 characters");
  	}

  	public String getStudentComment() {
  		return studentComment;
  	}

	public void setDeadline(Date deadline) throws Exception {
	  this.deadline = dateFormatting(String.valueOf(deadline));
  }

	public Date getDeadline(){
		return deadline;
  	}
	public String getStudentFile(){
		return studentFile;
	}

	public void setStudentFile(String path){
		studentFile = path;
	}

	public String returnStudentFile(){
		return studentFile;
	}

	public boolean isMade() {
		return isMade;
	}

	public void setMade(boolean made) {
		isMade = made;
	}



	private Date dateFormatting(String dateString) throws Exception {
		try {
			return simpleDateFormat.parse(dateString);
		}catch(ParseException e){
			throw new Exception("invalid date format");
		}

	}
	private void checkExceedingMaximumScore(int maximumScore) throws Exception {
		if(maximumScore>100){
			throw new Exception("maximum score can't be more than 100");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		PracticalMaterial that = (PracticalMaterial) o;
		return id == that.id  ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id);
	}

	@Override
	public String toString(){
		return discipline+"\ntask: "+ name+"\ndeadline: "+simpleDateFormat.format(deadline)+
				"\nmaximum score: " + maximumScore +"\n student score: "
				+ studentScore +" \nteachers file:" + teachersFile +"\nstudents file:"
				+ studentFile + "\nTeacher comment: "
				+ teacherComment + "\nStudent comment: " + studentComment;
	}

}
