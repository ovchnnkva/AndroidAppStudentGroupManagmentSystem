package com.hfad.studentsystemapp.model;


import static com.hfad.studentsystemapp.model.Constants.TypeSchedule.CLASSES;

import android.util.Log;

public class ClassesSchedule extends Schedule {

    private long id;
    //
    // Constructors
    //
    public ClassesSchedule(){
        super(CLASSES);
        Log.i("CLASS SCHEDULE","create classes schedule");
    }
    public ClassesSchedule(long id){
        super(id, CLASSES);
        Log.i("CLASS SCHEDULE","create classes schedule");
    }
}
