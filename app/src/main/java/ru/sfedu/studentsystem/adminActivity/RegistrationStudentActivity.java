package ru.sfedu.studentsystem.adminActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.studentsystem.Constants;
import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.adminActivity.adapters.StudyGroupSearchAdapter;
import ru.sfedu.studentsystem.model.StudyGroup;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.services.RoleService;
import ru.sfedu.studentsystem.services.StudentService;
import ru.sfedu.studentsystem.services.StudyGroupService;

public class RegistrationStudentActivity extends AppCompatActivity {
    private EditText nameStudent;
    private EditText dateBirthdayStudent;
    private EditText studyGroupStudentText;
    private EditText mailStudent;
    private EditText passwordStudent;
    private StudyGroup[] fragments;
    private RetrofitService retrofit;
    private Button registrationStudent;
    private StudyGroup studyGroupStudent;
    private Calendar dateAndTime=Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_student);

        initView();
    }

    private void initView(){
        nameStudent = findViewById(R.id.name_student_registration);
        dateBirthdayStudent = findViewById(R.id.birthday_student_registration);
        studyGroupStudentText = findViewById(R.id.search_spinner_study_group_registration_student);
        mailStudent = findViewById(R.id.mail_registartion_student);
        passwordStudent = findViewById(R.id.password_registration_student);
        registrationStudent = findViewById(R.id.save_registration_button);
        
        registrationStudent.setOnClickListener(event -> registration());

        retrofit = new RetrofitService();

        dateBirthdayStudent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setDate(v);
                    dateBirthdayStudent.setFocusableInTouchMode(false);
                    dateBirthdayStudent.setFocusable(false);
                    dateBirthdayStudent.setFocusableInTouchMode(true);
                    dateBirthdayStudent.setFocusable(true);
                }
            }
        });

        initSearchGroupDialog();
    }

    private void initSearchGroupDialog()  {

        studyGroupStudentText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Dialog dialog = new Dialog(v.getContext());
                    dialog.setContentView(R.layout.search_spinner);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                    dialog.show();

                    EditText search = dialog.findViewById(R.id.search_group);
                    ListView listView = dialog.findViewById(R.id.listView_of_searchableSpinner);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.d("POSITION", position + "");
                            Log.d("FRAG", fragments[position].getGroupsCode());
                            studyGroupStudentText.setText(fragments[position].getGroupsCode());
                            studyGroupStudent = fragments[position];
                            dialog.dismiss();
                        }
                    });

                    search.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String regex = search.getText().toString().replaceAll(" ", "");
                            if (!regex.equals("")) {
                                searchStudyGroup(listView, regex);
                            }
                            else clear(listView);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
            }
        });
    }
    private void clear(ListView listView){
        fragments = new StudyGroup[1];
        StudyGroupSearchAdapter adapter = new StudyGroupSearchAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, fragments);
        listView.setAdapter(adapter);
    }
    private void searchStudyGroup(ListView listView, String regex){
        StudyGroupService service = retrofit.createService(StudyGroupService.class);
        Call<List<StudyGroup>> call = service.getStudyGroupByCode(regex);
        call.enqueue(new Callback<List<StudyGroup>>() {
            @Override
            public void onResponse(Call<List<StudyGroup>> call, Response<List<StudyGroup>> response) {
                if(response.isSuccessful()){
                    List<StudyGroup> groups = response.body();
                    fragments = groups.toArray(new StudyGroup[0]);
                    StudyGroupSearchAdapter adapter = new StudyGroupSearchAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, fragments);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<StudyGroup>> call, Throwable t) {

            }
        });

    }
    
    private void registration(){
        if(validation()){
            String mail = mailStudent.getText().toString().replaceAll(" ", "");
            String pass = passwordStudent.getText().toString().replaceAll(" ","");
            registrationInFirebase(mail, pass);
        } else {
            Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_LONG).show();
        }
        
    }
    private void registrationInFirebase(String mail, String pass){
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(mail, pass)
                .addOnSuccessListener(event ->{
                    Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                    Log.d("UID", event.getUser().getUid());
                    saveStudent(event.getUser().getUid());
                })
                .addOnFailureListener(event -> Toast.makeText(this, "Введены некорректные данные", Toast.LENGTH_SHORT).show());
    }
    private void saveStudent(String uid){
        StudentService service = retrofit.createService(StudentService.class);
        String dateStr = formmatingDate(dateBirthdayStudent.getText().toString());
        Call<Integer> call = service.registerStudent(nameStudent.getText().toString(),dateStr,
                studyGroupStudent.getId(), uid);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    if (response.body() > 0){
                        saveRole(uid);
                    } else {
                        Toast.makeText(RegistrationStudentActivity.this, "Ошибка сохранения", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
               
            }
        });
    }
    private String formmatingDate(String date){
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date newDate = oldDateFormat.parse(date);
            return newDateFormat.format(newDate);
        } catch(ParseException e){
            Log.e("FORMATDATE", e.getMessage());
        }
        return "";
    }
    private void saveRole(String uid){
        RoleService service = retrofit.createService(RoleService.class);
        Call<Integer> call = service.saveRole(uid, Constants.ROLES.STUDENT.toString());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){ 
                    Toast.makeText(RegistrationStudentActivity.this, "Информация о студенте сохранена", Toast.LENGTH_LONG).show();
                    finishActivity();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(RegistrationStudentActivity.this, "Ошибка сервера. Информация о студенте не сохранена", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void finishActivity(){
        finish();
    }
    
    private boolean validation(){
        return (nameStudent.getText() != null) && (dateBirthdayStudent.getText() != null) && (studyGroupStudentText.getText() != null)
                && (mailStudent != null) && (passwordStudent != null) && checkValidPass();
    }
    private boolean checkValidPass(){
        String pass = passwordStudent.getText().toString().replaceAll(" ","");
        Pattern patternDegits = Pattern.compile("[1-9]");
        return (pass.length() > 8) && (patternDegits.matcher(pass).find());
    }

    public void setDate(View v) {
        new DatePickerDialog(RegistrationStudentActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }


    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String dateBirthdayStr = "" + (dayOfMonth < 10 ? "0"+dayOfMonth : dayOfMonth) + "." + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "." + year;
            dateBirthdayStudent.setText(dateBirthdayStr);
        }
    };
}