package ru.sfedu.studentsystem.teacherActivity;

import static ru.sfedu.studentsystem.Constants.AUTH_FILE_NAME;
import static ru.sfedu.studentsystem.Constants.UID_USER_AUTH_FILE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import ru.sfedu.studentsystem.model.Discipline;
import ru.sfedu.studentsystem.model.PracticalMaterial;
import ru.sfedu.studentsystem.model.Teacher;
import ru.sfedu.studentsystem.services.DisciplineService;
import ru.sfedu.studentsystem.services.PracticalMaterialService;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.services.TeacherService;
import ru.sfedu.studentsystem.studentActivities.MaterialsSelectionActivity;
import ru.sfedu.studentsystem.teacherActivity.recycle.adapters.StudentDetailPerformanceShortAdapter;
import ru.sfedu.studentsystem.teacherActivity.recycle.fragments.StudentShortPerformanceFragment;

public class DetailStudentActivity extends AppCompatActivity {
    private RecyclerView containerPerformance;
    private List<StudentShortPerformanceFragment> fragments = new ArrayList<>();
    private Teacher teacher;
    private long studentId;
    private RetrofitService retrofit;
    private final String actualTypeSemester = "Осень 2022";
    private ProgressBar loading;
    private Button goToMaterialsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_student);

        retrofit = new RetrofitService();

        initTeacherUid();
    }

    private void initTeacherUid() {
        SharedPreferences pref = getSharedPreferences(AUTH_FILE_NAME, MODE_PRIVATE);
        String teacherUid = pref.getString(UID_USER_AUTH_FILE, "");

        initTeacher(teacherUid);
    }
    private void initTeacher(String uid){
        Log.d("TEACHER", "get teacher with uid: " + uid);

        TeacherService service = retrofit.createService(TeacherService.class);
        Call<Teacher> call = service.getTeacherByUid(uid);
        call.enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                if(response.isSuccessful()){
                    teacher = response.body();
                    initView();
                }
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {
                Toast.makeText(DetailStudentActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initView(){
        TextView nameStudent = findViewById(R.id.name_student_detail);
        TextView specializationStudent = findViewById(R.id.specializaion_detail);
        TextView groupsCodeStudent = findViewById(R.id.code_detail);
        TextView birthdayStudent = findViewById(R.id.birthday_student_detail);
        containerPerformance = findViewById(R.id.performance_detail);
        loading = findViewById(R.id.loading_student_detail);
        goToMaterialsButton = findViewById(R.id.go_to_stuents_material_buttons);

        studentId = getIntent().getLongExtra("id", 0);

        String name = getIntent().getStringExtra("name");
        nameStudent.setText(name);
        String specialization = getIntent().getStringExtra("specialization");
        specializationStudent.setText(specialization);
        String codeGroup = getIntent().getStringExtra("groupCode");
        groupsCodeStudent.setText(codeGroup);
        String birthday = getIntent().getStringExtra("birthday");
        birthdayStudent.setText(birthday);

        long id = getIntent().getLongExtra("groupid", 0);

        goToMaterialsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MaterialsSelectionActivity.class);
                intent.putExtra("studentId", studentId);
                startActivity(intent);
            }
        });

        initGroupsDisciplinesId(id);
    }
    private void initGroupsDisciplinesId(long groupId) {
        Log.d("GROUP", "get disciplinesstudy group with id: " + groupId);

        DisciplineService service = retrofit.createService(DisciplineService.class);
        Call<List<Long>> call = service.getDisciplineByGroupId(groupId);
        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if(response.isSuccessful()){
                    initTeachersDisciplines(response.body());
                } else {
                    Toast.makeText(DetailStudentActivity.this, "Группе не добавлены дисциплины", Toast.LENGTH_LONG).show();
                    loading.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                Toast.makeText(DetailStudentActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initTeachersDisciplines(List<Long> groupsDisciplinesIds){
        Log.d("TEACHER", "get teacher with id: " + teacher.getId());

        DisciplineService service = retrofit.createService(DisciplineService.class);
        Call<List<Long>> call = service.getDisciplinesByTeacherId(teacher.getId());
        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if(response.isSuccessful()){
                    response.body().forEach(id -> {
                        if(groupsDisciplinesIds.contains(id)){
                            initPracticalMaterials(id);
                        }
                    });
                }  else {
                    Toast.makeText(DetailStudentActivity.this, "Преподавателю ещё не добавлены дисциплины", Toast.LENGTH_LONG).show();
                    loading.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                Toast.makeText(DetailStudentActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initPracticalMaterials(long disciplineId){
        PracticalMaterialService service = retrofit.createService(PracticalMaterialService.class);
        Call<List<PracticalMaterial>> call = service.getPracticalMaterialByDiscipline(studentId, disciplineId, actualTypeSemester);
        call.enqueue(new Callback<List<PracticalMaterial>>() {
            @Override
            public void onResponse(Call<List<PracticalMaterial>> call, Response<List<PracticalMaterial>> response) {
                if (response.isSuccessful()) {
                    initDiscipline(disciplineId, response.body());
                } else {
                    loading.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<PracticalMaterial>> call, Throwable t) {
                Toast.makeText(DetailStudentActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initDiscipline(Long disciplineid, List<PracticalMaterial> materials){
        Log.d("DISCIPLINEID", disciplineid+"");

        DisciplineService service = retrofit.createService(DisciplineService.class);
        Call<Discipline> call = service.getDisciplineById(disciplineid);
        call.enqueue(new Callback<Discipline>() {
            @Override
            public void onResponse(Call<Discipline> call, Response<Discipline> response) {
                if(response.isSuccessful()) {
                    Discipline discipline = response.body();
                    materials.forEach(m -> m.setDiscipline(discipline));
                    addFragment(materials, discipline);
                }
            }

            @Override
            public void onFailure(Call<Discipline> call, Throwable t) {
                Toast.makeText(DetailStudentActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void addFragment(List<PracticalMaterial> material, Discipline discipline){
        int studentScore = getSumPerformance(material);

        StudentShortPerformanceFragment fragment = new StudentShortPerformanceFragment(discipline.getName(), (int) ((float) studentScore /100f*100),discipline.getTypeAttestation());
        fragment.setActualScore(studentScore, discipline.getMaxScoreForSemester());
        fragment.setStudentId(studentId);
        fragment.setDisciplineId(discipline.getId());
        fragment.setTypeSemester(actualTypeSemester);
        Log.d("FRAGMENT", fragment.getTypeAttestation());
        fragments.add(fragment);

        initRecycle();
    }
    private void initRecycle(){
        StudentDetailPerformanceShortAdapter adapter = new StudentDetailPerformanceShortAdapter(getApplicationContext(), fragments);
        containerPerformance.setAdapter(adapter);
        loading.setVisibility(View.INVISIBLE);
    }

    private int getSumPerformance(List<PracticalMaterial> scores){
        if (scores.isEmpty()) return 0;
        final int[] sum = {0};
        scores.forEach(m -> sum[0] += m.getStudentScore());
        return sum[0];
    }
}