package com.hfad.studentsystemapp.model;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@RequiresApi(api = Build.VERSION_CODES.N)
public class Student {
	private long id;
    private String name;
	private Date birthday;
    private Map<Discipline,Integer> scores = new HashMap<>();
	private long studyGroupId;
	private StudyGroup studyGroup;
	private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

	//
	// Constructors
	//

	public Student (long id,String name, String birthday, StudyGroup studyGroup) throws Exception {
		this.birthday=dateFormatting(birthday);
		this.id = id;
		this.name = name;
		this.studyGroup = studyGroup;
		studyGroupId = studyGroup.getId();
		scores = new HashMap<>();
		Log.i("STUDENT","Create student " + this);
	}
	public Student(){

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

  	public void setStudyGroup (StudyGroup studyGroup) {
  		this.studyGroup = studyGroup;
		  studyGroupId = studyGroup.getId();
  }
  	public StudyGroup getStudyGroup () {
  		return studyGroup;
  }

	public long getStudyGroupId() {
		return studyGroupId;
	}

	public void setStudyGroupId(long studyGroupId) {
		this.studyGroupId = studyGroupId;
	}

	//
		// Other methods
		//
	private Date dateFormatting(String date) throws Exception {
		try{
			return formatter.parse(date);
		}catch (ParseException e){
			throw new Exception("invalid date format");
		}
	}

	/**
	 * Append discipline and the value score to scores
	 * @param discipline for keys map
	 * @param score for values map
	 * @throws Exception
	 */

  public void appendDisciplineScore(Discipline discipline, int score) throws Exception {
	  checkExceedingMaximumScore(score);
	  if (!studyGroup.getDisciplines().contains(discipline)) {
		  Log.e("STUDENT","It is impossible to add points, as this discipline does not exist");
		  throw new Exception("It is impossible to add points, as this discipline does not exist");
	  }
	  if (scores.containsKey(discipline)) {
		  checkExceedingMaximumScore(scores.get(discipline) +score);
		  Log.i("STUDENT","append score to discipline "+discipline.getName());
		  scores.put(discipline, scores.get(discipline) + score);
		  Log.d("STUDENT","score of discipline "+discipline.getName()+" ="+scores.get(discipline));
	  }else {
		  scores.put(discipline, score);
		  Log.d("STUDENT","score of discipline " + discipline.getName() + " =" + scores.get(discipline));
	  }
  }

	/**
	 * Check for exceeding the maximum score
	 * @score the value to check
	 * @throws Exception
	 */
  private void checkExceedingMaximumScore(int score) throws Exception {
	  if(score>100){
		  Log.e("STUDENT","total score cannot exceed 100, score ="+score);
		  throw new Exception("total score cannot exceed 100");
	  }
  }

	/**
	 *Method for displaying information about schedule
	 * @param type the enum of type schedule
	 * @return the value of schedule
	 */
  public Schedule returnInformationAboutSchedule(Constants.TypeSchedule type) throws Exception {
	  Log.i("STUDENT","return schedule "+type);
	  return studyGroup.getSchedule(type);
  }

	/**
	 * Method for displaying information about student performance
	 * @return the String with information about performance
	 */

  public String returnInformationAboutPerformance(){
	  String result =scores.entrySet().stream().map(e->e.getKey()+" score:"+e.getValue()).collect(Collectors.joining("\n"));
	  Log.i("STUDENT","return information about performance");
	  return result;
  }

	/**
	 * Method for complete the practicalMaterial
	 * @param practicalMaterial the value of practicalMaterial
	 * @param path the value of studentFile
	 */
  public void attachFileToPracticalMaterial(PracticalMaterial practicalMaterial, String path) throws Exception {
	  checkStudyGroupContainsDiscipline(practicalMaterial.getDiscipline());
	  practicalMaterial.setStudentFile(path);
	  Log.i("STUDENT","Practical material done");
  }

	/**
	 * Method for student commenting the practicalMaterial
	 * @param practicalMaterial the value of practicalMaterial
	 * @param comment the value for append to studentComment in practicalMaterial
	 * @throws Exception
	 */
  public void commentingPracticalMaterial(PracticalMaterial practicalMaterial, String comment) throws Exception {
	  checkStudyGroupContainsDiscipline(practicalMaterial.getDiscipline());

	  Log.i("STUDENT","student "+name+" commenting practical material "+ practicalMaterial.getName());
	  Log.d("STUDENT","comment in practical material:"+ practicalMaterial.getStudentComment());
	  practicalMaterial.setStudentComment(comment);
  }
	private void checkStudyGroupContainsDiscipline(Discipline discipline) throws Exception {
	  if(!(studyGroup.getDisciplines().contains(discipline))){
		  throw new Exception("The assignment is not available to the student");
	  }
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Student student = (Student) o;
		return id == student.id && name.equals(student.name) && birthday.equals(student.birthday) && studyGroup.equals(student.studyGroup);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, birthday, studyGroup);
	}

	@Override
	public String toString(){
	  return "\nname: "+name+"\ndate of birthday: "+ formatter.format(birthday)+"\ngroup: "+studyGroup;
  }
}
