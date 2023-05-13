package ru.sfedu.studentsystem.studentActivities;


import android.util.Log;

import ru.sfedu.studentsystem.model.Constants;
import ru.sfedu.studentsystem.model.Schedule;

public class ClassesSchedule extends Schedule {

    private long id;

    public ClassesSchedule(){
        super(Constants.TypeSchedule.CLASSES);
        Log.i("CLASS SCHEDULE","create classes schedule");
    }
    public ClassesSchedule(long id){
        super(id, Constants.TypeSchedule.CLASSES);
        Log.i("CLASS SCHEDULE","create classes schedule");
    }
}
