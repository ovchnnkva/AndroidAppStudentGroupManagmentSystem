package ru.sfedu.studentsystem.studentActivities;

import static ru.sfedu.studentsystem.Constants.AUTH_FILE_NAME;
import static ru.sfedu.studentsystem.Constants.ROLE_USER_AUTH_FILE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import ru.sfedu.studentsystem.ClassesScheduleActivity;
import ru.sfedu.studentsystem.Constants;
import ru.sfedu.studentsystem.R;

public class CalendarActivity extends AppCompatActivity  {
        private SharedPreferences pref;
        private Constants.ROLES role;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        pref = getSharedPreferences(AUTH_FILE_NAME, MODE_PRIVATE);
        role = Constants.ROLES.valueOf(pref.getString(ROLE_USER_AUTH_FILE, ""));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(year, month, dayOfMonth);
            Intent intent = new Intent(CalendarActivity.this, ClassesScheduleActivity.class);
            intent.putExtra("weekDay", calendar1.get(Calendar.DAY_OF_WEEK));

            CalendarActivity.this.startActivity(intent);
        });
    }


}