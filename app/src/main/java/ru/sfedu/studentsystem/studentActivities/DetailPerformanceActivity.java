package ru.sfedu.studentsystem.studentActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.model.Discipline;
import ru.sfedu.studentsystem.model.PracticalMaterial;
import ru.sfedu.studentsystem.services.DisciplineService;
import ru.sfedu.studentsystem.services.PracticalMaterialService;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.studentActivities.recycle.adapters.DetailPerformanceAdapter;
import ru.sfedu.studentsystem.studentActivities.recycle.fragments.DetailPerformanceFragment;

public class DetailPerformanceActivity extends AppCompatActivity {
    private long studentId;
    private  long disciplineId;
    private String typeSem;
    private RetrofitService retrofit;
    private TextView nameDiscipline;
    private Discipline discipline;
    private TextView resultScoreSemester;
    private TextView examScore;
    private TextView dateExamScoreAppend;
    private TextView resultScoreStudent;
    private RecyclerView container;

    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_performance);

        retrofit = new RetrofitService();

        loading = findViewById(R.id.loading_detail_performance);

        Intent intent = getIntent();

        initView();

        studentId = intent.getLongExtra("studentId", 0);
        disciplineId = intent.getLongExtra("disciplineId", 0);
        typeSem = intent.getStringExtra("typeSemester");

        loading.setVisibility(View.VISIBLE);

        Log.d("DISCIPLINE", disciplineId+"");
        Log.d("STUDENT", studentId+"");
        Log.d("TYPESEM", typeSem);

        initDiscipline();
    }

    private void initView() {
        nameDiscipline = findViewById(R.id.discipline_name_fragment);
        resultScoreSemester = findViewById(R.id.result_score_semester);
        examScore = findViewById(R.id.exam_score_fragment);
        dateExamScoreAppend = findViewById(R.id.date_exam_score_append);
        resultScoreStudent = findViewById(R.id.result_score_discipline);
        container = findViewById(R.id.container_detail_performance);
    }

    private void initDiscipline(){
        Log.d("DISCIPLINEID", disciplineId+"");

        DisciplineService service = retrofit.createService(DisciplineService.class);
        Call<Discipline> call = service.getDisciplineById(disciplineId);
        call.enqueue(new Callback<Discipline>() {
            @Override
            public void onResponse(Call<Discipline> call, Response<Discipline> response) {
                if(response.isSuccessful()){
                    discipline = response.body();
                    nameDiscipline.setText(discipline.getName());
                    initPracticalMaterials();
                }
            }

            @Override
            public void onFailure(Call<Discipline> call, Throwable t) {
                Toast.makeText(DetailPerformanceActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initPracticalMaterials(){
        PracticalMaterialService service = retrofit.createService(PracticalMaterialService.class);
        Call<List<PracticalMaterial>> call = service.getPracticalMaterialByDiscipline(studentId, disciplineId, typeSem);
        call.enqueue(new Callback<List<PracticalMaterial>>() {
            @Override
            public void onResponse(Call<List<PracticalMaterial>> call, Response<List<PracticalMaterial>> response) {
                if (response.isSuccessful()) {
                    addFragment(response.body());

                }
            }
            @Override
            public void onFailure(Call<List<PracticalMaterial>> call, Throwable t) {
                Toast.makeText(DetailPerformanceActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void addFragment(List<PracticalMaterial> materials){
        List<DetailPerformanceFragment> fragments = new ArrayList<>();
        AtomicInteger resultScore = new AtomicInteger();
        AtomicInteger examScore = new AtomicInteger();

        materials.forEach(m -> {
            if(m.getName().equals("Экзамен")) {
                initExam(m);
                examScore.addAndGet(m.getStudentScore());
            } else {
                DetailPerformanceFragment fragment = new DetailPerformanceFragment();

                fragment.setNameTask(m.getName());
                fragment.setScores(m.getStudentScore(), m.getMaximumScore());
                fragment.setDateAppendScore(m.getDateScoreAppend());

                fragments.add(fragment);

                resultScore.addAndGet(m.getStudentScore());
            }
        });
        String resultScoreSemesterStr = resultScore + "/" + discipline.getMaxScoreForSemester();
        resultScoreSemester.setText(resultScoreSemesterStr);

        String resultScoreStudentStr = (examScore.get() + resultScore.get()) + "/100";
        resultScoreStudent.setText(resultScoreStudentStr);

        Log.d("FRAGSIZE", fragments.size()+"");
        initRecycle(fragments);
    }

    private void initRecycle(List<DetailPerformanceFragment> fragments){
        loading.setVisibility(View.INVISIBLE);
        DetailPerformanceAdapter adapter = new DetailPerformanceAdapter(this, fragments);
        container.setLayoutManager(new LinearLayoutManager(this));
        container.setAdapter(adapter);
    }

    private void initExam(PracticalMaterial material){
        String examScoreStr = material.getStudentScore()+"/40";

        TextView examStr = findViewById(R.id.exam);
        examStr.setVisibility(View.VISIBLE);

        examScore.setText(examScoreStr);
        dateExamScoreAppend.setText(material.getDateScoreAppend());

    }


}