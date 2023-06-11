package ru.sfedu.studentsystem.studentActivities;

import static ru.sfedu.studentsystem.Constants.AUTH_FILE_NAME;
import static ru.sfedu.studentsystem.Constants.ROLE_USER_AUTH_FILE;
import static ru.sfedu.studentsystem.Constants.UID_USER_AUTH_FILE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import ru.sfedu.studentsystem.model.Discipline;
import ru.sfedu.studentsystem.model.EducationMaterials;
import ru.sfedu.studentsystem.model.LectionMaterial;
import ru.sfedu.studentsystem.model.PracticalMaterial;
import ru.sfedu.studentsystem.model.Student;
import ru.sfedu.studentsystem.model.Teacher;
import ru.sfedu.studentsystem.services.DisciplineService;
import ru.sfedu.studentsystem.services.LectionMaterialService;
import ru.sfedu.studentsystem.services.PracticalMaterialService;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.services.StudentService;
import ru.sfedu.studentsystem.services.TeacherService;
import ru.sfedu.studentsystem.studentActivities.recycle.adapters.PracticalMaterialAdapter;
import ru.sfedu.studentsystem.studentActivities.recycle.fragments.MaterialFragment;
import ru.sfedu.studentsystem.teacherActivity.CreateMaterialActivity;

public class MaterialsSelectionActivity extends AppCompatActivity {
    private  List<MaterialFragment> fragments = new ArrayList<>();
    private Button madeButton;
    private Button notMadeButton;
    private Button lectionButton;
    private String actualTypeSemester;

    private RetrofitService retrofit;

    private ProgressBar loading;
    private  Student student;
    private RecyclerView container;
    private Button prevButton;
    private Constants.ROLES role;
    private Button appendMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials_selection);

        container =findViewById(R.id.container_material);
        retrofit = new RetrofitService();
        loading = findViewById(R.id.loading_materials);

        madeButton = findViewById(R.id.button_made);
        notMadeButton = findViewById(R.id.button_not_made);
        lectionButton = findViewById(R.id.button_lection_material);

        appendMaterial = findViewById(R.id.append_material_teacher);

        prevButton = madeButton;
        initRole();
        if(role.equals(Constants.ROLES.STUDENT)){
            initStudentUid();
        } else {
            initStudentById();
            appendMaterial.setEnabled(true);
            appendMaterial.setVisibility(View.VISIBLE);
            initAppendMaterialButton();
        }

        initSpinner();
        initButtons();
    }
    @Override
    protected void onResume() {
        super.onResume();
        clear();
        prevButton.callOnClick();
    }
    private void initAppendMaterialButton(){
        appendMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateMaterialActivity.class);
                intent.putExtra("studentId", student.getId());
                intent.putExtra("studyGroupId", student.getStudyGroupId());
                intent.putExtra("typeSem", actualTypeSemester);
                startActivity(intent);
            }
        });
    }

    private void initStudentById() {
        Intent intent = getIntent();
        long studentId = intent.getLongExtra("studentId", 0);

        StudentService service = retrofit.createService(StudentService.class);
        Call<Student> call = service.getStudentById(studentId);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if(response.isSuccessful()) {
                    student = response.body();
                    prevButton.callOnClick();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(MaterialsSelectionActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initRole(){
        SharedPreferences pref = getSharedPreferences(AUTH_FILE_NAME, MODE_PRIVATE);
        role = Constants.ROLES.valueOf(pref.getString(ROLE_USER_AUTH_FILE, ""));
    }
    private void initButtons(){
        madeButton.setOnClickListener(v -> {
            prevButton = madeButton;
            loading.setVisibility(View.VISIBLE);
            clear();
            initPracticalMaterial();

        });
        notMadeButton.setOnClickListener(v -> {
            prevButton = notMadeButton;
            loading.setVisibility(View.VISIBLE);
            clear();
            initPracticalMaterial();
        });
        lectionButton.setOnClickListener(v -> {
            prevButton = lectionButton;
            loading.setVisibility(View.VISIBLE);
            clear();
            initLectionMaterial();
        });
    }

    private void initSpinner(){
        String[] typesSemester = {"Осень 2022", "Весна 2023"};
        Spinner spinner = findViewById(R.id.type_semester_materials);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typesSemester);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        actualTypeSemester = typesSemester[0];
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loading.setVisibility(View.VISIBLE);
                actualTypeSemester = (String)parent.getItemAtPosition(position);
                if(student != null) {
                    prevButton.callOnClick();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    private void clear(){
        fragments = new ArrayList<>();
        PracticalMaterialAdapter adapter = new PracticalMaterialAdapter(getApplicationContext(), fragments);
        container.setAdapter(adapter);
    }
    private void initStudentUid(){
        SharedPreferences pref = getSharedPreferences(AUTH_FILE_NAME, MODE_PRIVATE);
        String uid = pref.getString(UID_USER_AUTH_FILE,"");
        Log.d("STUDENT", uid);

        initStudent(uid);
    }

    private void initStudent(String uid){
        Log.d("STUDENT", "get with student uid "+uid);

        StudentService service = retrofit.createService(StudentService.class);
        Call<Student> call = service.getStudentByUid(uid);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if(response.isSuccessful()) {
                    student = response.body();
                    prevButton.callOnClick();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(MaterialsSelectionActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void initPracticalMaterial(){
        if(student == null) return;
        PracticalMaterialService service = retrofit.createService(PracticalMaterialService.class);
        Call<List<PracticalMaterial>> call = service.getPracticalMaterialByStudentId(student.getStudyGroupId(), actualTypeSemester);
        call.enqueue(new Callback<List<PracticalMaterial>>() {
            @Override
            public void onResponse(Call<List<PracticalMaterial>> call, Response<List<PracticalMaterial>> response) {
                if (response.isSuccessful()) {
                    response.body().forEach(m -> {
                        initDiscipline(m);
                    });
                } else {
                    loading.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<PracticalMaterial>> call, Throwable t) {
                Toast.makeText(MaterialsSelectionActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void initLectionMaterial(){
        if(student == null) return;
        LectionMaterialService service = retrofit.createService(LectionMaterialService.class);
        Call<List<LectionMaterial>> call = service.getLectionMaterialByGroupId(student.getId(), actualTypeSemester);
        call.enqueue(new Callback<List<LectionMaterial>>() {
            @Override
            public void onResponse(Call<List<LectionMaterial>> call, Response<List<LectionMaterial>> response) {
                if (response.isSuccessful()) {
                    response.body().forEach(m -> {
                        initDiscipline(m);
                    });
                } else {
                    loading.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<LectionMaterial>> call, Throwable t) {
                Toast.makeText(MaterialsSelectionActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void initDiscipline(EducationMaterials materials){
        Log.d("DISCIPLINEID", materials.getDisciplineID()+"");

        DisciplineService service = retrofit.createService(DisciplineService.class);
        Call<Discipline> call = service.getDisciplineById(materials.getDisciplineID());
        call.enqueue(new Callback<Discipline>() {
            @Override
            public void onResponse(Call<Discipline> call, Response<Discipline> response) {
                if(response.isSuccessful()){
                    materials.setDiscipline(response.body());
                    initTeacher(materials);
                }
            }

            @Override
            public void onFailure(Call<Discipline> call, Throwable t) {
                Toast.makeText(MaterialsSelectionActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initTeacher(EducationMaterials materials){
        Log.d("TEACHERID", materials.getTeacherId()+"");

        TeacherService service = retrofit.createService(TeacherService.class);
        Call<Teacher> call = service.getTeacherById(materials.getTeacherId());
        call.enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                if(response.isSuccessful()){
                    materials.setTeacher(response.body());
                    if((role.equals(Constants.ROLES.STUDENT)) || (role.equals(Constants.ROLES.ADMIN)) || ((role.equals(Constants.ROLES.TEACHER)) && (response.body().getUid().equals(getTeacherUid())))){
                        addPracticalMaterialFragment(materials);
                    }
                }
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {
                Toast.makeText(MaterialsSelectionActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getTeacherUid(){
        return getSharedPreferences(AUTH_FILE_NAME, MODE_PRIVATE).getString(UID_USER_AUTH_FILE, "");
    }

    private void addPracticalMaterialFragment(EducationMaterials materials){
        if(materials.getName().equals("Экзамен"))  return;
        MaterialFragment fragment = null;
        if(prevButton.equals(lectionButton))
            fragment = new MaterialFragment((LectionMaterial) materials);
        else if(checkButtonPreviousClick() == ((PracticalMaterial) materials).isMade())
           fragment = new MaterialFragment((PracticalMaterial) materials);

        if(fragment != null) fragments.add(fragment);
        initRecyclePracticalMaterial();
    }
    private void initRecyclePracticalMaterial(){
        loading.setVisibility(View.INVISIBLE);
        Log.d("RECYCLE", fragments.size() + "");
        PracticalMaterialAdapter adapter = new PracticalMaterialAdapter(this, fragments);
        container.setAdapter(adapter);
    }

    private boolean checkButtonPreviousClick(){
        return prevButton.equals(madeButton);
    }
}