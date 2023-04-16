package com.hfad.studentsystemapp.model;
import static com.hfad.studentsystemapp.model.Constants.TypeSchedule.EVENTS;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class EventsSchedule extends Schedule {
    private long id;

    public EventsSchedule() {
        super(EVENTS);
        Log.i("EVENT","create events schedule");
    }

    public EventsSchedule(long id) {
        super(id, EVENTS);
        Log.i("EVENT","create events schedule");
    }
}
