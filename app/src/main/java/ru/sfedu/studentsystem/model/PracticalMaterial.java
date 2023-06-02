package ru.sfedu.studentsystem.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class PracticalMaterial extends EducationMaterials {

	private String studentFile ="";
	private String studentComment = "";

	private int maximumScore = 0;
	private int studentScore = 0;

	private String deadline;
	private String dateScoreAppend;
	private boolean made = false;



	public PracticalMaterial (long id,Teacher teacher, String name, Discipline discipline, String deadline, int maximumScore) throws Exception {
		super(id,teacher,name,discipline);
		this.deadline = dateFormattingDeadlineToString(deadline);
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
	  this.deadline = dateFormattingDeadlineToString(String.valueOf(deadline));
  }

	public String getDeadline(){
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
		return made;
	}

	public void setMade(boolean made) {
		made = made;
	}

	public String getDateScoreAppend() {
		return dateScoreAppend;
	}

	public void setDateScoreAppend(String dateScoreAppend) throws Exception {
		this.dateScoreAppend = dateFormattingAppendScore(dateScoreAppend);
	}

	private String dateFormattingDeadlineToString(String dateString) throws Exception {
		Log.d("PARSE DEADLINE", dateString);
		SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
		SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
		try {
			Date date = oldDateFormat.parse(dateString);
			return newDateFormat.format(date);
		}catch(ParseException e){
			throw new Exception("invalid date format");
		}
	}
	private String dateFormattingAppendScore(String dateString) throws Exception {
		Log.d("PARSE APPEND DATE", dateString);
		SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
		SimpleDateFormat newDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
		try {
			Date date = oldDateFormat.parse(dateString);
			return newDateFormat.format(date);
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
	public String toString() {
		return "PracticalMaterial{" +
				"studentFile='" + studentFile + '\'' +
				", studentComment='" + studentComment + '\'' +
				", maximumScore=" + maximumScore +
				", studentScore=" + studentScore +
				", deadline='" + deadline + '\'' +
				", dateScoreAppend=" + dateScoreAppend +
				", made=" + made +
				'}';
	}
}
