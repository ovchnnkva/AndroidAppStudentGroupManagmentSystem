package ru.sfedu.studentsystem.teacherActivity;

import static ru.sfedu.studentsystem.Constants.AUTH_FILE_NAME;
import static ru.sfedu.studentsystem.Constants.UID_USER_AUTH_FILE;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.model.Discipline;
import ru.sfedu.studentsystem.model.Teacher;
import ru.sfedu.studentsystem.services.DisciplineService;
import ru.sfedu.studentsystem.services.LectionMaterialService;
import ru.sfedu.studentsystem.services.PracticalMaterialService;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.services.TeacherService;
import ru.sfedu.studentsystem.studentActivities.MaterialsSelectionActivity;
import ru.sfedu.studentsystem.teacherActivity.adapters.DisciplinesAdapter;

public class CreateMaterialActivity extends AppCompatActivity {
    private EditText nameMaterial;
    private EditText teacherCommentMaterial;
    private EditText deadlineMaterial;
    private EditText maxScoreMaterial;
    private Button save;
    private Spinner typeMaterial;
    private Spinner disciplineSpinner;
    private String dateDeadline = "01.01.1999";
    private String tomeDeadline = "23:59";
    private String actualTypeMaterial;
    private Long studentId;
    private String typeSemester;
    private long studyGroupId;
    private RetrofitService retrofit;
    private long teacherId;
    private long disciplineId;
    private List<Discipline> disciplineList = new ArrayList<>();

    private Calendar dateAndTime=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_material);
        retrofit = new RetrofitService();

        studentId = getIntent().getLongExtra("studentId", 0);
        typeSemester = getIntent().getStringExtra("typeSem");
        studyGroupId = getIntent().getLongExtra("studyGroupId", 0);

        initTeacher(getTeacherUid());
        initView();
    }

    private String getTeacherUid(){
        return getSharedPreferences(AUTH_FILE_NAME, MODE_PRIVATE).getString(UID_USER_AUTH_FILE, "");
    }
    private void initView() {
        nameMaterial = findViewById(R.id.name_create_material);
        teacherCommentMaterial = findViewById(R.id.teacher_comment_create_material);
        deadlineMaterial = findViewById(R.id.deadline_material_create);
        maxScoreMaterial = findViewById(R.id.max_score_material_create);
        save = findViewById(R.id.save_new_material_button);
        typeMaterial = findViewById(R.id.type_material_spinner);
        disciplineSpinner = findViewById(R.id.discipline_spinner);

        initTypeMaterialSpinner();
        initDisciplineSpinner();

        setInitialDateTime();

        deadlineMaterial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setDate(v);
                    setTime(v);
                    maxScoreMaterial.setFocusable(true);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMaterial();
            }
        });
    }
    private void initGroupsDisciplinesId(long groupId) {
        Log.d("GROUP", "get disciplines study group with id: " + groupId);

        DisciplineService service = retrofit.createService(DisciplineService.class);
        Call<List<Long>> call = service.getDisciplineByGroupId(groupId);
        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if(response.isSuccessful()){
                    initTeachersDisciplines(response.body());
                } else {
                    Toast.makeText(CreateMaterialActivity.this, "Группе не добавлены дисциплины", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                Toast.makeText(CreateMaterialActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initTeachersDisciplines(List<Long> groupsDisciplinesIds){
        DisciplineService service = retrofit.createService(DisciplineService.class);
        Call<List<Long>> call = service.getDisciplinesByTeacherId(teacherId);
        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if(response.isSuccessful()){
                    response.body().forEach(id -> {
                        if(groupsDisciplinesIds.contains(id)){
                            initDiscipline(id);
                        }
                    });

                }  else {
                    Toast.makeText(CreateMaterialActivity.this, "Преподавателю ещё не добавлены дисциплины", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                Toast.makeText(CreateMaterialActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
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
                    disciplineList.add(response.body());
                    initDisciplineSpinner();
                }
            }

            @Override
            public void onFailure(Call<Discipline> call, Throwable t) {
                Toast.makeText(CreateMaterialActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initTypeMaterialSpinner(){
        String[] typesMaterial = {"Практическое задание", "Лекционное задание"};
        Spinner spinner = findViewById(R.id.type_material_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typesMaterial);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actualTypeMaterial = (String)parent.getItemAtPosition(position);

                if(actualTypeMaterial.equals("Лекционное задание")) {
                    initLectionCreateView();
                } else {
                    initPracticalMaterial();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }
    private void initDisciplineSpinner(){
        Discipline[] teachersDiscipline = disciplineList.toArray(new Discipline[0]);
        Spinner spinner = findViewById(R.id.discipline_spinner);
        Log.d("DISCSPINER", "size = " + teachersDiscipline.length);
        DisciplinesAdapter adapter = new DisciplinesAdapter(this, android.R.layout.simple_spinner_item, teachersDiscipline);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Discipline discipline = adapter.getItem(position);
                disciplineId = discipline.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }
    private void initLectionCreateView(){
        maxScoreMaterial.setVisibility(View.INVISIBLE);
        deadlineMaterial.setVisibility(View.INVISIBLE);
    }
    private void initPracticalMaterial(){
        maxScoreMaterial.setVisibility(View.VISIBLE);
        deadlineMaterial.setVisibility(View.VISIBLE);
    }
    private void saveMaterial() {
        if(validate()){
            if(actualTypeMaterial.equals("Лекционное задание")){
                saveLectionMaterial();
            } else {
                savePracticalMaterial();
            }
        } else {
            Toast.makeText(this, "Некорректные данные", Toast.LENGTH_LONG).show();
        }
        
    }
    private void savePracticalMaterial(){
        PracticalMaterialService service = retrofit.createService(PracticalMaterialService.class);
        Call<Integer> call = service.registryPracticalMaterial(nameMaterial.getText().toString(), teacherCommentMaterial.getText().toString(), disciplineId, Integer.parseInt(maxScoreMaterial.getText().toString()),
                dateTimeFormating(deadlineMaterial.getText().toString()), studentId, teacherId, typeSemester);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    if(response.body() > 0){
                        Toast.makeText(CreateMaterialActivity.this, "Запись сохранена", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MaterialsSelectionActivity.class);
                        intent.putExtra("studentId", studentId);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(CreateMaterialActivity.this, "Ошибка сервера. Запись не сохранена", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String dateTimeFormating(String date) {
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try{
            Date newDate = oldDateFormat.parse(date);
            return newDateFormat.format(newDate);
        } catch (ParseException e) {
            Log.e("DATEFORMAT", e.getMessage());
        }
        return date;
    }
    private void saveLectionMaterial(){
        LectionMaterialService service = retrofit.createService(LectionMaterialService.class);
        Call<Integer> call = service.registryLectionMaterial(nameMaterial.getText().toString(), disciplineId, teacherCommentMaterial.getText().toString());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Toast.makeText(CreateMaterialActivity.this, "Запись сохранена", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MaterialsSelectionActivity.class);
                intent.putExtra("studentId", studentId);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(CreateMaterialActivity.this, "Ошибка сервера. Запись не сохранена", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initTeacher(String uid){
        Log.d("TEACHER", "get teacher with uid: " + uid);

        TeacherService service = retrofit.createService(TeacherService.class);
        Call<Teacher> call = service.getTeacherByUid(uid);
        call.enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                if(response.isSuccessful()){
                   teacherId = response.body().getId();
                   initGroupsDisciplinesId(studyGroupId);
                }
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {
                Toast.makeText(CreateMaterialActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean validate(){
        if(actualTypeMaterial.equals("Практическое задание")){
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm");
            boolean dateIsValid = false;
            try {
                String dateStr = deadlineMaterial.getText().toString();
                format.parse(dateStr);
                dateIsValid = true;
            } catch (ParseException e) {
                Toast.makeText(this, "Введен неверный формат даты", Toast.LENGTH_LONG).show();
            }
            return dateIsValid && (!nameMaterial.getText().toString().equals("")) && (disciplineSpinner.getSelectedItem() != null) && (!maxScoreMaterial.getText().toString().equals(""));
        }
        return (!nameMaterial.getText().toString().equals("")) && (disciplineSpinner.getSelectedItem() != null);
    }

    public void setDate(View v) {
        new DatePickerDialog(CreateMaterialActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTime(View v) {
        new TimePickerDialog(CreateMaterialActivity.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    private void setInitialDateTime() {
        String deadline = dateDeadline + " " + tomeDeadline;
        deadlineMaterial.setText(deadline);
    }


    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            tomeDeadline = "" + hourOfDay + ":" + (minute < 10 ? "0"+minute : minute);
            setInitialDateTime();
        }
    };


    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateDeadline = "" + (dayOfMonth < 10 ? "0"+dayOfMonth : dayOfMonth) + "." + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "." + year;
            setInitialDateTime();
        }
    };
}