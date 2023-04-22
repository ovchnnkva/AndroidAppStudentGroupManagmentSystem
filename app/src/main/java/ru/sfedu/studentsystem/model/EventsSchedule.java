package ru.sfedu.studentsystem.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
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
