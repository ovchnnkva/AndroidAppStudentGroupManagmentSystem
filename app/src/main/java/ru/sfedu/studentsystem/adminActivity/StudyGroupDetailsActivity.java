package ru.sfedu.studentsystem.adminActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.model.Student;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.services.StudentService;
import ru.sfedu.studentsystem.teacherActivity.recycle.adapters.StudentAdapter;
import ru.sfedu.studentsystem.teacherActivity.recycle.fragments.StudentFragment;

public class StudyGroupDetailsActivity extends AppCompatActivity {
    private TextView codeView;
    private TextView specializationView;
    private TextView courseView;
    private RecyclerView studentsContainer;
    private RetrofitService retrofit;
    private ProgressBar loading;

    private List<StudentFragment> fragments = new ArrayList<>();

    private long groupId;
    private String groupCodeStr;
    private String specializationStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_group_details);

        retrofit = new RetrofitService();

        initView();
    }

    private void initView(){
        codeView = findViewById(R.id.code_study_group_detail);
        specializationView = findViewById(R.id.specialization_group_detail);
        courseView = findViewById(R.id.cours_group_detail);
        studentsContainer = findViewById(R.id.student_in_group_detail);
        loading = findViewById(R.id.loading_study_group_detail);

        groupCodeStr = getIntent().getStringExtra("code");
        codeView.setText(groupCodeStr);

        specializationStr = getIntent().getStringExtra("specialization");
        specializationView.setText(specializationStr);

        String courseStr = "Курс " + getIntent().getIntExtra("course", 0);
        courseView.setText(courseStr);

        groupId = getIntent().getLongExtra("groupId", 0);

        initStudents();
    }

    private void initStudents(){
        StudentService service = retrofit.createService(StudentService.class);
        Call<List<Student>> call = service.getStudentByGroupId(groupId);
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if(response.isSuccessful()){
                    response.body().forEach(s -> addFragment(s));
                    loading.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(StudyGroupDetailsActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void addFragment(Student student){
        StudentFragment fragment = new StudentFragment(student, groupCodeStr, specializationStr);
        fragments.add(fragment);
        initRecycle();
    }
    private void initRecycle(){
        StudentAdapter adapter = new StudentAdapter(getApplicationContext(), fragments);
        studentsContainer.setAdapter(adapter);
    }
}