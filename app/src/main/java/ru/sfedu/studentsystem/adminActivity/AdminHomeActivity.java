package ru.sfedu.studentsystem.adminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.teacherActivity.SearchStudentActivity;

public class AdminHomeActivity extends AppCompatActivity {
    private Button goToStudent;
    private Button goToTeacher;
    private Button goToSchedules;
    private Button goToStudentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        init();
    }

    private void init(){
        goToStudent = findViewById(R.id.go_to_students_admin);
        goToTeacher = findViewById(R.id.go_to_teachers_admin);
        goToSchedules = findViewById(R.id.go_to_teachers_admin);
        goToStudentGroup = findViewById(R.id.go_to_student_group_admin);

        goToStudent.setOnClickListener(event -> {
            Intent intent = new Intent(AdminHomeActivity.this, SearchStudentActivity.class);
            startActivity(intent);
        });

        goToTeacher.setOnClickListener(event -> {

        });

        goToSchedules.setOnClickListener(event -> {

        });

        goToStudentGroup.setOnClickListener(event -> {

        });
    }
}