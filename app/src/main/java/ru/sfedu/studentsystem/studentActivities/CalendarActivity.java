package ru.sfedu.studentsystem.studentActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import ru.sfedu.studentsystem.R;

public class CalendarActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Intent intent = new Intent(CalendarActivity.this, ClassesScheduleActivity.class);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(year, month, dayOfMonth);

            intent.putExtra("weekDay", calendar1.get(Calendar.DAY_OF_WEEK));

            CalendarActivity.this.startActivity(intent);
        });
    }


}