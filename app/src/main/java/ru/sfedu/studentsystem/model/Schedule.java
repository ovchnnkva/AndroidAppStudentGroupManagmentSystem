package ru.sfedu.studentsystem.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
@RequiresApi(api = Build.VERSION_CODES.N)
public class Schedule{
	protected long id;
	protected Constants.TypeSchedule typeSchedule;
	private final List<String> dayOfWeek = List.of("Понедельник","Вторник","Среда","Четверг","Пятница","Суббота");
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

	//
	// Other methods
	//

	/**
	 *Create and append new event to schedule
	 * @name the value of name event
	 * @date the value of date event
	 * @time the value of time event
	 * time for event of classes - day of week
	 * time for other type event - hour:minutes
	 * @throws IllegalArgumentException, Exception
	 */

	public void createEvent(long idEvent, String name, String date, String time,String location) throws Exception {
		if((typeSchedule.equals(Constants.TypeSchedule.CLASSES)) && (!dayOfWeek.contains(date))){
			Log.e("SCHEDULE","invalid date for classes schedule");
			throw new IllegalArgumentException("The date in the class schedule should be the day of the week");
		}
		Event event = new Event(idEvent,id,name, date, time, location);
		try{
			appendToSchedule(Optional.ofNullable(event));
		}catch(Exception e){
			Log.e("SCHEDULE","event no append to schedule");
			throw new Exception(e);
		}
	}

	/**
	 *
	 * @param event the value event for append to schedule
	 * @throws Exception
	 */
	private void appendToSchedule(Optional<Event> event) throws Exception {
		if (!schedule.contains(event)){
			event.ifPresent(ev->schedule.add(ev));
			Log.i("SCHEDULE","event "+event.get().getName()+" append to "+typeSchedule+ " schedule");
		}else{
			Log.e("SCHEDULE","This event already been added to "+typeSchedule+" schedule");
			throw new Exception("This event already been added");
		}
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
		return typeSchedule+"\n"+schedule.stream().map(Object::toString).collect(Collectors.joining("\n"));
	}

}