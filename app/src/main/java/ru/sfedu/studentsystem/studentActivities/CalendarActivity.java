package ru.sfedu.studentsystem.studentActivities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;
import java.util.Calendar;

import ru.sfedu.studentsystem.R;

public class CalendarActivity extends AppCompatActivity  {
    private static String[] weekDay = {"Воскресенье","Понедельник","Вторник","Среда","Четверг","Пятница","Суббота"};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setDate(Instant.now().toEpochMilli());
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Intent intent = new Intent(CalendarActivity.this, ClassesScheduleActivity.class);
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);

                intent.putExtra("weekDay",weekDay[calendar.get(Calendar.DAY_OF_WEEK) - 1]);

                CalendarActivity.this.startActivity(intent);
            }
        });
    }


}