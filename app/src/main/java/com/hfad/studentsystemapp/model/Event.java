package com.hfad.studentsystemapp.model;

import android.util.Log;

import androidx.fragment.app.Fragment;


import java.util.Objects;
import java.util.regex.Pattern;

public class Event extends Fragment {
	private long id;
	private long scheduleId;
	private String name;
	private String time;
	private String date;
	private String location;
	private Pattern pattern = Pattern.compile("([0-1]?[0-9]|2[0-3]):[0-5][0-9]");
	//Примеры: 2:20, 02:20, 12:00

	//
	// Constructors
	//
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
	public Event(){Log.e("EVENT","Create event");}


  
		//
		// Methods
		//


		//
		// Accessor methods
		//

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
  		this.time = time;
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

		//
		// Other methods
		//



	public String toString(){
	  return ""+ name +" "+date+" "+time+"\n";
	}
}
