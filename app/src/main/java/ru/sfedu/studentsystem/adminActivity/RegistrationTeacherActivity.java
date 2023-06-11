package ru.sfedu.studentsystem.adminActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.studentsystem.Constants;
import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.services.RoleService;
import ru.sfedu.studentsystem.services.TeacherService;

public class RegistrationTeacherActivity extends AppCompatActivity {
    private EditText nameRegistration;
    private EditText mailRegistration;
    private EditText passwordRegistration;
    private Button save;
    private RetrofitService retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_teacher);

        initView();
    }

    private void initView(){
        nameRegistration = findViewById(R.id.name_teacher_registration);
        mailRegistration = findViewById(R.id.mail_registartion_teacher);
        passwordRegistration = findViewById(R.id.password_registration_teacher);
        save = findViewById(R.id.save_registration_teacher_button);

        retrofit = new RetrofitService();

        save.setOnClickListener(event -> registration());
    }
    private boolean validation(){
        return !nameRegistration.getText().toString().equals("") && !mailRegistration.getText().toString().equals("") && !passwordRegistration.getText().toString().equals("");
    }
    private void registration(){
        if(validation()){
            String mail = mailRegistration.getText().toString().replaceAll(" ", "");
            String pass = passwordRegistration.getText().toString().replaceAll(" ","");
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
                    saveTeacher(event.getUser().getUid());
                })
                .addOnFailureListener(event -> Toast.makeText(this, "Введены некорректные данные", Toast.LENGTH_SHORT).show());
    }

    private void saveTeacher(String uid){
        TeacherService service = retrofit.createService(TeacherService.class);
        Call<Integer> call = service.registerTeacher(nameRegistration.getText().toString(), uid);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    if (response.body() > 0){
                        saveRole(uid);
                    } else {
                        Toast.makeText(RegistrationTeacherActivity.this, "Ошибка сохранения", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }
    private void saveRole(String uid){
        RoleService service = retrofit.createService(RoleService.class);
        Call<Integer> call = service.saveRole(uid, Constants.ROLES.TEACHER.toString());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    Toast.makeText(RegistrationTeacherActivity.this, "Информация о преподавателе сохранена", Toast.LENGTH_LONG).show();
                    finishActivity();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(RegistrationTeacherActivity.this, "Ошибка сервера. Информация о студенте не сохранена", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void finishActivity(){
        finish();
    }
}