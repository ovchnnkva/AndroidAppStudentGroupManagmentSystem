package ru.sfedu.studentsystem.model;


import android.util.Log;

public class ClassesSchedule extends Schedule {

    private long id;
    //
    // Constructors
    //
    public ClassesSchedule(){
        super(Constants.TypeSchedule.CLASSES);
        Log.i("CLASS SCHEDULE","create classes schedule");
    }
    public ClassesSchedule(long id){
        super(id, Constants.TypeSchedule.CLASSES);
        Log.i("CLASS SCHEDULE","create classes schedule");
    }
}
