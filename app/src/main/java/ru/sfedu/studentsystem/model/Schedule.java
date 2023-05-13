package ru.sfedu.studentsystem.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Schedule{
	protected long id;
	protected long groupId;
	protected Constants.TypeSchedule typeSchedule;
	protected List<Event> schedule = new ArrayList<>();

	public Schedule(long id,Constants.TypeSchedule type){
		this.id = id;
		this.typeSchedule = type;
		Log.i("SCHEDULE","create schedule");
	}
	public Schedule(Constants.TypeSchedule type){
		this.typeSchedule = type;
	}
	public Schedule(){Log.i("SCHEDULE","create schedule");}

	public void setSchedule(List<Event> schedule) {
		this.schedule = schedule;
	}

	public List<Event> getSchedule() {
		return schedule;
	}
	public Constants.TypeSchedule getTypeSchedule(){
		return typeSchedule;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public void setTypeSchedule(Constants.TypeSchedule typeSchedule) {
		this.typeSchedule = typeSchedule;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Schedule schedule = (Schedule) o;
		return id == schedule.id && typeSchedule == schedule.typeSchedule;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, typeSchedule);
	}

	@Override
	public String toString(){
		return typeSchedule.toString();
	}

}