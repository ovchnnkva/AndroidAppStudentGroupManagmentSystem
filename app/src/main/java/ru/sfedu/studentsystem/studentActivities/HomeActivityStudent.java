package ru.sfedu.studentsystem.studentActivities;

import static ru.sfedu.studentsystem.Constants.AUTH_FILE_NAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ru.sfedu.studentsystem.MainActivity;
import ru.sfedu.studentsystem.R;

public class HomeActivityStudent extends AppCompatActivity {
    private Button sessionScheduleButton;
    private Button eventScheduleButton;
    private Button classesScheduleButton;
    private Button performanceButton;
    private Button materialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);

        init();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_stop) {
            SharedPreferences pref = getSharedPreferences(AUTH_FILE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(HomeActivityStudent.this, MainActivity.class);
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    private void init(){
        classesScheduleButton = findViewById(R.id.to_class_schedule_button);
        classesScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivityStudent.this, CalendarActivity.class);
                HomeActivityStudent.this.startActivity(intent);
            }
        });

        sessionScheduleButton = findViewById(R.id.go_to_sessia_shedule);
        sessionScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivityStudent.this, SessionScheduleActivity.class);
                HomeActivityStudent.this.startActivity(intent);
            }
        });

        eventScheduleButton = findViewById(R.id.go_to_event_schedule);
        eventScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivityStudent.this, EventScheduleActivity.class);
                HomeActivityStudent.this.startActivity(intent);
            }
        });

        performanceButton = findViewById(R.id.go_to_perfomance);
        performanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivityStudent.this, PerformanceActivity.class);
                HomeActivityStudent.this.startActivity(intent);
            }
        });

        materialButton = findViewById(R.id.go_to_materials);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivityStudent.this, MaterialsSelectionActivity.class);
                HomeActivityStudent.this.startActivity(intent);
            }
        });
    }
}