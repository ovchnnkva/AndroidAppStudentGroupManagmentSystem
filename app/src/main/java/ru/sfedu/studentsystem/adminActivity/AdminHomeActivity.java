package ru.sfedu.studentsystem.adminActivity;

import static ru.sfedu.studentsystem.Constants.AUTH_FILE_NAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ru.sfedu.studentsystem.MainActivity;
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
            Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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
            Intent intent = new Intent(AdminHomeActivity.this, SearchTeacherActivity.class);
            startActivity(intent);
        });

        goToSchedules.setOnClickListener(event -> {

        });

        goToStudentGroup.setOnClickListener(event -> {

        });
    }
}