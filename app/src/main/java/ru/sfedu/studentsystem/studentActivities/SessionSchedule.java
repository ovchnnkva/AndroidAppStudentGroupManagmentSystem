package ru.sfedu.studentsystem.studentActivities;

import android.util.Log;

import ru.sfedu.studentsystem.Constants;
import ru.sfedu.studentsystem.model.Schedule;

public class SessionSchedule extends Schedule {
    private long id;

    public SessionSchedule(long id){
        super(id, Constants.TypeSchedule.SESSION);
        Log.i("SESSION SCHEDULE","create session schedule");
    }
    public SessionSchedule(){
        super(Constants.TypeSchedule.SESSION);
    }
}
