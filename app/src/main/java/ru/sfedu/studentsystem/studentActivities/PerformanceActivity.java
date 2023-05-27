package ru.sfedu.studentsystem.studentActivities;

import static ru.sfedu.studentsystem.model.Constants.AUTH_FILE_NAME;
import static ru.sfedu.studentsystem.model.Constants.UID_USER_AUTH_FILE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import ru.sfedu.studentsystem.model.Student;
import ru.sfedu.studentsystem.model.Teacher;
import ru.sfedu.studentsystem.services.DisciplineService;
import ru.sfedu.studentsystem.services.PracticalMaterialService;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.services.StudentService;
import ru.sfedu.studentsystem.services.TeacherService;
import ru.sfedu.studentsystem.studentActivities.recycle.adapters.PerformanceAdapter;
import ru.sfedu.studentsystem.studentActivities.recycle.fragments.PerformanceFragment;

public class PerformanceActivity extends AppCompatActivity {

    private RetrofitService retrofit;
    private Student student;
    private String actualTypeSemester;
    private RecyclerView recyclerView;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);

        loading = findViewById(R.id.loading_events_schedule);
        recyclerView = findViewById(R.id.container_performance);

        retrofit = new RetrofitService();

        initStudentUid();
    }

    private void initSpinner(){
        String[] typesSemester = {"Осень 2022", "Весна 2023"};
        Spinner spinner = findViewById(R.id.type_semester);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typesSemester);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loading.setVisibility(View.VISIBLE);
                clear();
                String item = (String)parent.getItemAtPosition(position);
                actualTypeSemester = item;

                initDisciplinesInGroup(student.getStudyGroupId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }
    private void clear(){
        fragments = new ArrayList<>();
        initRecycle();
    }
    private void initStudentUid(){
        SharedPreferences pref = getSharedPreferences(AUTH_FILE_NAME, MODE_PRIVATE);
        String uid = pref.getString(UID_USER_AUTH_FILE,"");
        Log.d("STUDENT", uid);

        initStudent(uid);
    }

    private void initStudent(String uid){
        Log.d("STUDENT", "get with student uid "+uid);
        TextView studentsName = findViewById(R.id.name_student);

        StudentService service = retrofit.createService(StudentService.class);
        Call<Student> call = service.getSTudentByUid(uid);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if(response.isSuccessful()) {
                     student = response.body();
                     studentsName.setText(student.getName());
                     initSpinner();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(PerformanceActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initDisciplinesInGroup(Long groupId){
        Log.d("STUDYGROUP", groupId+"");

        DisciplineService service = retrofit.createService(DisciplineService.class);
        Call<List<Long>> call = service.getDisciplineByGroupId(groupId);
        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if(response.isSuccessful()){
                    response.body().forEach(d -> {
                        initDiscipline(d);
                    });
                } else {
                    Toast.makeText(PerformanceActivity.this, "Дисциплины еще не добавлены", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                Toast.makeText(PerformanceActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initDiscipline(Long disciplineid){
        Log.d("DISCIPLINEID", disciplineid+"");

        DisciplineService service = retrofit.createService(DisciplineService.class);
        Call<Discipline> call = service.getDisciplineById(disciplineid);
        call.enqueue(new Callback<Discipline>() {
            @Override
            public void onResponse(Call<Discipline> call, Response<Discipline> response) {
                if(response.isSuccessful()){
                    initTeachers(response.body());

                }
            }

            @Override
            public void onFailure(Call<Discipline> call, Throwable t) {
                Toast.makeText(PerformanceActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initTeachers(Discipline discipline){
        Log.d("TEACHER", "disciplineId " + discipline.getId());

        TeacherService service = retrofit.createService(TeacherService.class);
        Call<List<Teacher>> call = service.getTeacherForDiscipline(discipline.getId());
        call.enqueue(new Callback<List<Teacher>>() {
            @Override
            public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                if (response.isSuccessful()) {
                    discipline.setTeachers(response.body());
                    initPracticalMaterials(discipline);
                } else {
                    Toast.makeText(PerformanceActivity.this, "Не назначен преподаватель на дисциплину", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Teacher>> call, Throwable t) {

            }
        });
    }

    private void initPracticalMaterials(Discipline discipline){
        PracticalMaterialService service = retrofit.createService(PracticalMaterialService.class);
        Call<List<PracticalMaterial>> call = service.getPracticalMaterialByDiscipline(student.getId(), discipline.getId(), actualTypeSemester);
        call.enqueue(new Callback<List<PracticalMaterial>>() {
            @Override
            public void onResponse(Call<List<PracticalMaterial>> call, Response<List<PracticalMaterial>> response) {
                if (response.isSuccessful()) {
                    addFragment(response.body(), discipline);
                    if (response.body().size() == 0){
                        clear();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<PracticalMaterial>> call, Throwable t) {
                Toast.makeText(PerformanceActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });

    }
    private List<PerformanceFragment> fragments = new ArrayList<>();

    private void addFragment(List<PracticalMaterial> scores, Discipline discipline){
        Log.d("RECYCLE", "init");

        int studentsAllScores = getSumPerformance(scores);

        scores.forEach(material -> material.setDiscipline(discipline));
        PerformanceFragment fragment = new PerformanceFragment(discipline.getName(),
                (int) ((float)studentsAllScores/100f*100),discipline.getTypeAttestation());
        fragment.setTeachers(discipline.getTeachers());
        fragment.setActualScore(studentsAllScores, discipline.getMaxScoreForSemester());
        fragment.setStudentId(student.getId());
        fragment.setDisciplineId(discipline.getId());
        fragment.setTypeSemester(actualTypeSemester);
        fragments.add(fragment);

        initRecycle();
        loading.setVisibility(View.INVISIBLE);

    }

    private void initRecycle(){
        Log.d("RECYCLE", "init");
        PerformanceAdapter adapter = new PerformanceAdapter(this, fragments);
        recyclerView.setAdapter(adapter);

    }

    private int getSumPerformance(List<PracticalMaterial> scores){
        if (scores.isEmpty()) return 0;
        final int[] sum = {0};
        scores.forEach(m -> sum[0] += m.getStudentScore());
        return sum[0];
    }


}