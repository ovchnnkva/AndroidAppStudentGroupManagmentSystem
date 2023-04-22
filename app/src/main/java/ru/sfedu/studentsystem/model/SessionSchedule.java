package ru.sfedu.studentsystem.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class SessionSchedule extends Schedule {
    private long id;

    public SessionSchedule(long id){
        super(id,Constants.TypeSchedule.SESSION);
        Log.i("SESSION SCHEDULE","create session schedule");
    }
    public SessionSchedule(){
        super(Constants.TypeSchedule.SESSION);
    }
}
