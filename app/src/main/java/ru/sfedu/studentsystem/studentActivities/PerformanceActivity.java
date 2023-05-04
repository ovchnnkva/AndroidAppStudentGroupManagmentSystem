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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.model.Discipline;
import ru.sfedu.studentsystem.model.Student;
import ru.sfedu.studentsystem.model.Teacher;
import ru.sfedu.studentsystem.services.DisciplineService;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.services.StudentRecordsBookService;
import ru.sfedu.studentsystem.services.StudentService;
import ru.sfedu.studentsystem.services.TeacherService;
import ru.sfedu.studentsystem.studentActivities.recycle.adapters.PerformanceAdapter;
import ru.sfedu.studentsystem.studentActivities.recycle.fragments.PerformanceFragment;

public class PerformanceActivity extends AppCompatActivity {

    private RetrofitService retrofit;
    private List<Discipline> disciplines;
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

        disciplines = new ArrayList<>();

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
                    disciplines.add(response.body());
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
        Log.d("TEACHER", "disciplineId "+discipline.getId());

        TeacherService service = retrofit.createService(TeacherService.class);
        Call<List<Teacher>> call = service.getTeacherForDiscipline(discipline.getId());
        call.enqueue(new Callback<List<Teacher>>() {
            @Override
            public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                if(response.isSuccessful()) {
                    discipline.setTeachers(response.body());
                } else {
                    Toast.makeText(PerformanceActivity.this, "Не назначен преподаватель на дисциплину", Toast.LENGTH_LONG).show();
                }
                initScores(discipline);
            }

            @Override
            public void onFailure(Call<List<Teacher>> call, Throwable t) {

            }
        });
    }

    private void initScores(Discipline discipline){
        Map<Discipline, Map<String, Integer>> scores = new HashMap<>();

        StudentRecordsBookService service = retrofit.createService(StudentRecordsBookService.class);
        Call<Map<String, Integer>> call = service.getScoreByDiscipline(student.getId(), discipline.getId(), actualTypeSemester);
        call.enqueue(new Callback<Map<String, Integer>>() {
            @Override
            public void onResponse(Call<Map<String, Integer>> call, Response<Map<String, Integer>> response) {
                if(response.isSuccessful()) {
                    scores.put(discipline, response.body());
                }
               addFragment(scores);
            }

            @Override
            public void onFailure(Call<Map<String, Integer>> call, Throwable t) {
                Toast.makeText(PerformanceActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
        
        addFragment(scores);
    }
    private List<PerformanceFragment> fragments = new ArrayList<>();

    private void addFragment(Map<Discipline, Map<String, Integer>> scores){
        Log.d("RECYCLE", "init");

        scores.forEach((key, value) -> {
            int studentsScoreSum = getSumPerformance(value);
            PerformanceFragment fragment = new PerformanceFragment(key.getName(),
                    Math.round((studentsScoreSum/(float)key.getMaxScoreForSemester())*100),
                    key.getTypeAttestation());
            fragment.setActualScore(studentsScoreSum, key.getMaxScoreForSemester());
            fragment.setTeachers(key.getTeachers());
            fragments.add(fragment);
        });

        initRecycle();
        loading.setVisibility(View.INVISIBLE);
    }

    private void initRecycle(){
        Log.d("RECYCLE", "init");
        PerformanceAdapter adapter = new PerformanceAdapter(this, fragments);
        recyclerView.setAdapter(adapter);

    }

    private int getSumPerformance(Map<String, Integer> scores){
        if (scores.isEmpty()) return 0;
        return scores.values().stream().mapToInt(Integer::intValue).sum();
    }

}