package ru.sfedu.studentsystem.adminActivity;

import android.content.Intent;
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
import ru.sfedu.studentsystem.adminActivity.recycle.adapters.StudyGroupAdapter;
import ru.sfedu.studentsystem.adminActivity.recycle.fragment.StudyGroupFragment;
import ru.sfedu.studentsystem.model.StudyGroup;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.services.StudyGroupService;

public class SearchStudyGroupActivity extends AppCompatActivity {
    private EditText search;
    private RecyclerView container;
    private RetrofitService retrofit;
    private ProgressBar loading;
    private List<StudyGroupFragment> fragments = new ArrayList<>();
    private Button createStudyGroupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_study_group);

        initView();
    }
    private void initView(){
        search = findViewById(R.id.search_study_group);
        container = findViewById(R.id.study_group_container);
        loading = findViewById(R.id.loading_study_group_search);
        createStudyGroupButton = findViewById(R.id.create_study_group_button);

        retrofit = new RetrofitService();

        createStudyGroupButton.setOnClickListener(event -> {
            Intent intent = new Intent(getApplicationContext(), CreateStudyGroupActivity.class);
            startActivity(intent);
        });

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
//        createStudyGroupButton.setOnClickListener(event -> {
//            Intent intent = new Intent(event.getContext(), RegistrationTeacherActivity.class);
//            startActivity(intent);
//        });
    }

    private void searchTeacher(){
        clear();
        loading.setVisibility(View.VISIBLE);
        String regex = search.getText().toString();
        Log.d("REGEX", regex);

        StudyGroupService service = retrofit.createService(StudyGroupService.class);
        Call<List<StudyGroup>> call = service.getStudyGroupByCode(regex);
        call.enqueue(new Callback<List<StudyGroup>>() {
            @Override
            public void onResponse(Call<List<StudyGroup>> call, Response<List<StudyGroup>> response) {
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
            public void onFailure(Call<List<StudyGroup>> call, Throwable t) {
                Toast.makeText(SearchStudyGroupActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void clear(){
        fragments = new ArrayList<>();
        StudyGroupAdapter adapter = new StudyGroupAdapter(getApplicationContext(), fragments);
        container.setAdapter(adapter);
    }

    private void addFragment(StudyGroup group){
        StudyGroupFragment fragment = new StudyGroupFragment(group);
        if(fragments.stream().noneMatch(f -> f.getGroupId() == fragment.getGroupId()))
            fragments.add(fragment);
        initRecycle();
    }
    private void initRecycle(){
        loading.setVisibility(View.INVISIBLE);
        StudyGroupAdapter adapter = new StudyGroupAdapter(this, fragments);
        container.setAdapter(adapter);
    }
}