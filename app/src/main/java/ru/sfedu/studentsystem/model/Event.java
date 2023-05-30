package ru.sfedu.studentsystem.model;

import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Event extends Fragment {
	private long id;
	private long scheduleId;
	private String name;
	private String time;
	private String date;
	private String location;
	private List<Teacher> teachers = new ArrayList<>();
	private static final Pattern pattern = Pattern.compile("([0-1]?[0-9]|2[0-3]):[0-5][0-9]");

	public Event(long id, long scheduleId, String name, String date, String time,String location) throws IllegalArgumentException{
		if ((name != "") && (pattern.matcher(time).find()) && (date != "")) {
			this.id = id;
			this.scheduleId = scheduleId;
			this.name = name;
			this.time = time;
			this.date = date;
			this.location = location;
			Log.i("EVENT","Create event");
		}else{
			Log.e("EVENT","invalid name or time or date of event");
			throw new IllegalArgumentException("Invalid name or time or date");
		}
	}
	public Event(){Log.d("EVENT","Create event");}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setName(String name) {
  		this.name = name;
  }

  	public String getName() {
  		return name;
  }

  	public void setTime(String time) {
		StringBuilder timeBuilder = new StringBuilder(time);
  		this.time = timeBuilder.substring(0,5);
  }

  	public String getTime() {
  		return time;
  }

  	public void setDate(String date) {
  		this.date = date;
  }

  	public String getDate() {
  		return date;
  }

	public long getIdEvent() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}
	public void appendTeacher(Teacher teacher){
		teachers.add(teacher);
	}
	@Override
	public String toString() {
		return "Event{" +
				"id=" + id +
				", scheduleId=" + scheduleId +
				", name='" + name + '\'' +
				", time='" + time + '\'' +
				", date='" + date + '\'' +
				", location='" + location + '\'' +
				", teachers='" + teachers + '\'' +
				'}';
	}
}
