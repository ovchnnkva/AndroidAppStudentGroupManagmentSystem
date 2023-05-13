package ru.sfedu.studentsystem.studentActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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

    private void init(){
        classesScheduleButton = findViewById(R.id.to_class_schedule_button);
        classesScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivityStudent.this, ClassesScheduleActivity.class);
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