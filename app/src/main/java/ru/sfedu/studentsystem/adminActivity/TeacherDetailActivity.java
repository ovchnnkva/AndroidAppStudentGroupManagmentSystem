package ru.sfedu.studentsystem.adminActivity;

import android.os.Bundle;
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
import ru.sfedu.studentsystem.adminActivity.dialogs.AppendDisciplineDialog;
import ru.sfedu.studentsystem.adminActivity.recycle.adapters.DisciplineTeacherShortAdapter;
import ru.sfedu.studentsystem.adminActivity.recycle.fragment.DisciplineTeacherShortFragment;
import ru.sfedu.studentsystem.model.Discipline;
import ru.sfedu.studentsystem.services.DisciplineService;
import ru.sfedu.studentsystem.services.RetrofitService;

public class TeacherDetailActivity extends AppCompatActivity {
    private TextView nameTeacher;
    private RecyclerView containerDiscipline;
    private Button appendDiscipline;
    private RetrofitService retrofit;
    private ProgressBar loading;
    private List<DisciplineTeacherShortFragment> fragments = new ArrayList<>();

    long teacherId;

    private AppendDisciplineDialog appendDisciplineDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);

        initView();
    }

    private void initView(){
        nameTeacher = findViewById(R.id.name_teacher_detail);
        containerDiscipline = findViewById(R.id.student_in_group_detail);
        loading = findViewById(R.id.loading_teacher_detail);
        appendDiscipline = findViewById(R.id.create_discipline_detail_teacher);
        retrofit = new RetrofitService();
        appendDisciplineDialog = new AppendDisciplineDialog();

        String name = getIntent().getStringExtra("teacherName");
        nameTeacher.setText(name);
        teacherId = getIntent().getLongExtra("teacherId", 0);
        appendDiscipline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addFragment(appendDisciplineDialog.showDialog(v.getContext(), teacherId));
            }
        });

        initDisciplinesIds(teacherId);
    }

    private void initDisciplinesIds(long teacherId){
        DisciplineService service = retrofit.createService(DisciplineService.class);
        Call<List<Long>> call = service.getDisciplinesByTeacherId(teacherId);
        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if(response.isSuccessful()){
                    if(response.body().size() > 0){
                        response.body().forEach(id -> initDiscipline(id));
                        loading.setVisibility(View.INVISIBLE);
                    }
                } else loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                Toast.makeText(TeacherDetailActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initDiscipline(long id){
        DisciplineService service = retrofit.createService(DisciplineService.class);
        Call<Discipline> call = service.getDisciplineById(id);
        call.enqueue(new Callback<Discipline>() {
            @Override
            public void onResponse(Call<Discipline> call, Response<Discipline> response) {
                if(response.isSuccessful()){
                    addFragment(response.body());
                }
            }

            @Override
            public void onFailure(Call<Discipline> call, Throwable t) {
                Toast.makeText(TeacherDetailActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void addFragment(Discipline discipline){
        DisciplineTeacherShortFragment fragment = new DisciplineTeacherShortFragment(discipline);
        fragments.add(fragment);

        initRecycle();
    }

    private void initRecycle(){
        DisciplineTeacherShortAdapter adapter = new DisciplineTeacherShortAdapter(getApplicationContext(), fragments);
        adapter.notifyDataSetChanged();
        containerDiscipline.setAdapter(adapter);
    }
}