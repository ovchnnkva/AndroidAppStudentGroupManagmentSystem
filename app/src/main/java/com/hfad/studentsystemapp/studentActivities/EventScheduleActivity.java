package com.hfad.studentsystemapp.studentActivities;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import com.hfad.studentsystemapp.R;
import com.hfad.studentsystemapp.model.Event;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class EventScheduleActivity extends FragmentActivity {
    List<Event> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_schedule);
        initData();

        RecyclerView recyclerView = findViewById(R.id.container_event);
        EventAdapter adapter = new EventAdapter(this, events);
        recyclerView.setAdapter(adapter);

    }

    private void initData(){

    }
}