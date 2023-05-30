package ru.sfedu.studentsystem.teacherActivity;

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
import ru.sfedu.studentsystem.studentActivities.CalendarActivity;

public class HomeActivityTeacher extends AppCompatActivity {
    private Button studentsButton;
    private Button classesScheduleButton;
    private Button studentsMaterialsButton;
    private Button createMaterialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        initButtons();
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
            Intent intent = new Intent(HomeActivityTeacher.this, MainActivity.class);
            this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void initButtons(){
        studentsButton = findViewById(R.id.go_to_student);
        classesScheduleButton = findViewById(R.id.go_to_classes_schedule_teacher);
        studentsMaterialsButton = findViewById(R.id.go_to_materials);
        createMaterialButton = findViewById(R.id.go_to_create_material);

        classesScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}