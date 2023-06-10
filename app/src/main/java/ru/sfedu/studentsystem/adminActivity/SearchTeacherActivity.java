package ru.sfedu.studentsystem.adminActivity;

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
import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.adminActivity.recycle.adapters.TeacherAdapter;
import ru.sfedu.studentsystem.adminActivity.recycle.fragment.TeacherFragment;
import ru.sfedu.studentsystem.model.Teacher;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.services.TeacherService;

public class SearchTeacherActivity extends AppCompatActivity {

    private EditText search;
    private RecyclerView container;
    private RetrofitService retrofit;
    private ProgressBar loading;
    private List<TeacherFragment> fragments = new ArrayList<>();
    private Button createStudentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_teacher);

        retrofit = new RetrofitService();

        initView();
    }

    private void initView(){
        search = findViewById(R.id.search_teacher);
        container = findViewById(R.id.teachers_container_search);
        loading = findViewById(R.id.loading_teachers_search);
        createStudentButton = findViewById(R.id.create_teacher_button);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String regex = search.getText().toString().replaceAll(" ", "");
                if(!regex.equals(""))
                    searchTeacher();
                else {
                    loading.setVisibility(View.INVISIBLE);
                    clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void searchTeacher(){
        clear();
        loading.setVisibility(View.VISIBLE);
        String regex = search.getText().toString();
        Log.d("REGEX", regex);

        TeacherService service = retrofit.createService(TeacherService.class);
        Call<List<Teacher>> call = service.getTeacherByName(regex);
        call.enqueue(new Callback<List<Teacher>>() {
            @Override
            public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                if(response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        loading.setVisibility(View.INVISIBLE);
                        response.body().forEach(t -> addFragment(t));
                    } else {
                        loading.setVisibility(View.INVISIBLE);
                    }
                } else {
                    loading.setVisibility(View.INVISIBLE);
                    clear();
                }
            }

            @Override
            public void onFailure(Call<List<Teacher>> call, Throwable t) {
                Toast.makeText(SearchTeacherActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void clear(){
        fragments = new ArrayList<>();
        TeacherAdapter adapter = new TeacherAdapter(getApplicationContext(), fragments);
        container.setAdapter(adapter);
    }

    private void addFragment(Teacher teacher){
        TeacherFragment fragment = new TeacherFragment(teacher);
        if(fragments.stream().noneMatch(f -> f.getTeacherId() == fragment.getTeacherId()))
            fragments.add(fragment);
        initRecycle();
    }
    private void initRecycle(){
        loading.setVisibility(View.INVISIBLE);
        TeacherAdapter adapter = new TeacherAdapter(this, fragments);
        container.setAdapter(adapter);
    }
}
