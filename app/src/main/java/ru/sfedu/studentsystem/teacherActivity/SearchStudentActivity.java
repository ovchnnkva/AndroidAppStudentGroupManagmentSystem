package ru.sfedu.studentsystem.teacherActivity;

import static ru.sfedu.studentsystem.Constants.AUTH_FILE_NAME;
import static ru.sfedu.studentsystem.Constants.ROLE_USER_AUTH_FILE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.studentsystem.Constants;
import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.adminActivity.RegistrationStudentActivity;
import ru.sfedu.studentsystem.model.Student;
import ru.sfedu.studentsystem.model.StudyGroup;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.services.StudentService;
import ru.sfedu.studentsystem.services.StudyGroupService;
import ru.sfedu.studentsystem.teacherActivity.recycle.adapters.StudentAdapter;
import ru.sfedu.studentsystem.teacherActivity.recycle.fragments.StudentFragment;

public class SearchStudentActivity extends AppCompatActivity {
    private EditText search;
    private RecyclerView container;
    private RetrofitService retrofit;
    private ProgressBar loading;
    private List<StudentFragment> fragments = new ArrayList<>();
    private Constants.ROLES role;
    private Button createStudentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student);

        retrofit = new RetrofitService();

        initRole();
        initView();
    }

    private void initRole(){
        SharedPreferences pref = getSharedPreferences(AUTH_FILE_NAME, MODE_PRIVATE);
        role = Constants.ROLES.valueOf(pref.getString(ROLE_USER_AUTH_FILE,""));
    }
    private void initView(){
        search = findViewById(R.id.search_student);
        container = findViewById(R.id.students_container);
        loading = findViewById(R.id.loading_students_search);
        createStudentButton = findViewById(R.id.create_student_button);

        if(role.equals(Constants.ROLES.ADMIN)){
            createStudentButton.setVisibility(View.VISIBLE);
            createStudentButton.setOnClickListener(event -> {
                Intent intent = new Intent(SearchStudentActivity.this, RegistrationStudentActivity.class);
                startActivity(intent);
            });
        }

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String regex = search.getText().toString().replaceAll(" ", "");
                if(!regex.equals(""))
                    searchStudents();
                else clear();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void searchStudents(){
        clear();
        loading.setVisibility(View.VISIBLE);
        String regex = search.getText().toString();
        Log.d("REGEX", regex);

        StudentService service = retrofit.createService(StudentService.class);
        Call<List<Student>> call = service.getStudentByName(regex);
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if(response.body().size() == 0) {
                    loading.setVisibility(View.INVISIBLE);
                } else if(response.isSuccessful()){
                    response.body().forEach(s -> initStudyGroup(s));
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(SearchStudentActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void clear(){
        fragments = new ArrayList<>();
        StudentAdapter adapter = new StudentAdapter(getApplicationContext(), fragments);
        container.setAdapter(adapter);
    }
    private void initStudyGroup(Student student){
        Log.d("GROUP", "get study group with id: "+student.getStudyGroupId());

        StudyGroupService service = retrofit.createService(StudyGroupService.class);
        Call<StudyGroup> call = service.getStudyGroupById(student.getStudyGroupId());
        call.enqueue(new Callback<StudyGroup>() {
            @Override
            public void onResponse(Call<StudyGroup> call, Response<StudyGroup> response) {
                if(response.isSuccessful()){
                    student.setGroup(response.body());
                    addFragment(student);
                } else {
                    Log.d("GROUP", "no group");
                    loading.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<StudyGroup> call, Throwable t) {
                Toast.makeText(SearchStudentActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void addFragment(Student student){
        StudentFragment fragment = new StudentFragment(student);
        if(fragments.stream().noneMatch(f -> f.getStudentId() == fragment.getStudentId()))
            fragments.add(fragment);
        initRecycle();
    }
    private void initRecycle(){
        loading.setVisibility(View.INVISIBLE);
        StudentAdapter adapter = new StudentAdapter(this, fragments);
        container.setAdapter(adapter);
    }
}