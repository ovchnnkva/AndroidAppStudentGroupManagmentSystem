package ru.sfedu.studentsystem.studentActivities;

import android.util.Log;

import ru.sfedu.studentsystem.model.Constants;
import ru.sfedu.studentsystem.model.Schedule;

public class EventsSchedule extends Schedule {
    private long id;

    public EventsSchedule() {
        super(Constants.TypeSchedule.EVENTS);
        Log.i("EVENT","create events schedule");
    }

    public EventsSchedule(long id) {
        super(id, Constants.TypeSchedule.EVENTS);
        Log.i("EVENT","create events schedule");
    }
}
