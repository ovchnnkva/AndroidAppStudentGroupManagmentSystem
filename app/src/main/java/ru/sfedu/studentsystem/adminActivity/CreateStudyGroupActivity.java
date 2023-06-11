package ru.sfedu.studentsystem.adminActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.services.StudyGroupService;

public class CreateStudyGroupActivity extends AppCompatActivity {
    private EditText specializationGroup;
    private EditText codeGroup;
    private EditText courseGroup;
    
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_study_group);
        
        initView();
    }

    private void initView() {
        specializationGroup = findViewById(R.id.specialization_group_create);
        codeGroup = findViewById(R.id.code_group_create);
        courseGroup = findViewById(R.id.course_group_create);
        saveButton = findViewById(R.id.save_study_group);
        
        saveButton.setOnClickListener(event -> {
            if(validation()){
                saveGroup();
            } else {
                Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveGroup(){
        RetrofitService retrofit = new RetrofitService();
        StudyGroupService service = retrofit.createService(StudyGroupService.class);
        Call<Integer> call = service.registerStudyGroup(Integer.parseInt(courseGroup.getText().toString().replaceAll(" ", "")), 
                specializationGroup.getText().toString(), codeGroup.getText().toString());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if((response.isSuccessful()) && (response.body() == 1)){
                    Toast.makeText(CreateStudyGroupActivity.this, "Данные сохранены", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(CreateStudyGroupActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean validation(){
        try {
            Integer.parseInt(courseGroup.getText().toString());
        } catch (NumberFormatException ex){
            Toast.makeText(this, "Поле 'Курс' должно быть заполнено числовым значением", Toast.LENGTH_SHORT).show();
            return false;
        }
        return (specializationGroup.getText() != null) && (codeGroup.getText() != null) && (courseGroup != null);
    }
}