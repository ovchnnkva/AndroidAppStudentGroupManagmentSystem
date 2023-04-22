package ru.sfedu.studentsystem.studentActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import ru.sfedu.studentsystem.R;

public class EducationMaterialActivity extends AppCompatActivity {
    private  TextView nameView ;
    private TextView disciplineView;
    private TextView deadlineView;
    private TextView score;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_material);
        init();
        Intent intent = getIntent();
        nameView.setText(intent.getStringExtra("name"));
        disciplineView.setText(intent.getStringExtra("discipline"));
        deadlineView.setText(intent.getStringExtra("deadline"));
        score.setText(intent.getStringExtra("score"));
        description.setText(intent.getStringExtra("description"));
    }

    public void init(){
        nameView = findViewById(R.id.name_education_material);
        disciplineView=findViewById(R.id.discipline_education_material);
        deadlineView=findViewById(R.id.deadline_education_material);
        score=findViewById(R.id.score_education_material);
        description=findViewById(R.id.description_education_material);
    }
}