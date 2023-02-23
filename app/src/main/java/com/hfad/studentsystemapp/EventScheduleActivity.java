package com.hfad.studentsystemapp;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class EventScheduleActivity extends FragmentActivity {
    ArrayList<EventFragment> events = new ArrayList<>();

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
        events.add(new EventFragment("Собрание","03.03.2023 12:30", "ИВТиПТ а.514"));
        events.add(new EventFragment("Ярмарка вакансий", "23.03.2023 11:55","а.514" ));
    }
}