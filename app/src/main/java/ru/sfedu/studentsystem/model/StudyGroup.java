package ru.sfedu.studentsystem.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.sfedu.studentsystem.Constants;
import ru.sfedu.studentsystem.studentActivities.ClassesSchedule;
import ru.sfedu.studentsystem.studentActivities.EventsSchedule;
import ru.sfedu.studentsystem.studentActivities.SessionSchedule;


public class StudyGroup {
  	private String groupCode;
  	private long id;
  	private long classesScheduleId;
  	private long eventsScheduleId;
  	private long sessionScheduleId;
  	private int course;
  	private String specialization;
  	private Schedule classesSchedule = new ClassesSchedule();
  	private Schedule eventsSchedule = new EventsSchedule();
  	private Schedule sessionSchedule=new SessionSchedule();
  	private List<Discipline> disciplines= new ArrayList<>();

	public StudyGroup (String groupCode, String specialization, int course) {
		this.groupCode = groupCode;
		this.specialization = specialization;
		this.course = course;
		Log.i("STUDY GROUP","create study group " + this);
	}
	public StudyGroup(){Log.i("STUDY GROUP","create study group");}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
  	public void setGroupsCode(String groupCode) {
  			this.groupCode = groupCode;
  	}

  	public String getGroupsCode() {
  			return groupCode;
  	}

	public void setSpecialization(String specialization){
	  this.specialization = specialization;
  	}

  	public String getSpecialization(){
		return specialization;
  	}

	public void setCourse(int course){
		  this.course = course;
	}

	public int getCourse(){
		  return course;
  }

	public void setDisciplines(List<Discipline> disciplines) {
		this.disciplines = disciplines;
	}

	public List<Discipline> getDisciplines() {
		return disciplines;
	}

	public void setClassesSchedule(Schedule classesSchedule) {
		this.classesSchedule = classesSchedule;
		classesScheduleId = classesSchedule.getId();
	}

  	public void setEventsSchedule(Schedule eventsSchedule) {
  		this.eventsSchedule = eventsSchedule;
		  eventsScheduleId = eventsSchedule.getId();
  }

  	public void setSessionSchedule(Schedule sessionSchedule) {
  		this.sessionSchedule = sessionSchedule;
		  sessionScheduleId = sessionSchedule.getId();
  	}

	public long getClassesScheduleId() {
		return classesScheduleId;
	}

	public long getEventsScheduleId() {
		return eventsScheduleId;
	}

	public long getSessionScheduleId() {
		return sessionScheduleId;
	}

	/**
	 * Get the value of schedule by typeSchedule
	 * @param type the enum for type schedule
	 * @return Schedule
	 */
  	public Schedule getSchedule(Constants.TypeSchedule type)  {
	  	switch (type){
			case EVENTS: return eventsSchedule;
			case CLASSES: return classesSchedule;
			case SESSION: return sessionSchedule;

	    }
		return new Schedule();
  	}
  	//
	// Other methods
	//
	/**
	 * Append the value of disciplines
	 * @param discipline the new value for disciplines
	 */
	public void appendDisciplines(Discipline discipline) throws Exception {
		if(!disciplines.contains(discipline)) {
			disciplines.add(discipline);
			Log.i("STUDY GROUP","discipline append to "+groupCode);
		}else{
			Log.e("STUDY GROUP","discipline has already been added to study group "+groupCode);
			throw new Exception("discipline has already been added");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		StudyGroup that = (StudyGroup) o;
		return id == that.id && course == that.course && groupCode.equals(that.groupCode) && specialization.equals(that.specialization);
	}

	@Override
	public int hashCode() {
		return Objects.hash(groupCode, id, course, specialization);
	}

	@Override
	public String toString(){
		return "code: "+groupCode+"\nspecialization: "+ specialization +"\ncourse: "+course+"\ndisciplines: "+disciplines.toString();
	}
}
