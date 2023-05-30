package ru.sfedu.studentsystem;

import static ru.sfedu.studentsystem.Constants.AUTH_FILE_NAME;
import static ru.sfedu.studentsystem.Constants.LOGIN_FROM_AUTH_FILE;
import static ru.sfedu.studentsystem.Constants.PASS_FROM_AUTH_FILE;
import static ru.sfedu.studentsystem.Constants.ROLE_USER_AUTH_FILE;
import static ru.sfedu.studentsystem.Constants.UID_USER_AUTH_FILE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.services.RoleService;
import ru.sfedu.studentsystem.studentActivities.HomeActivityStudent;
import ru.sfedu.studentsystem.teacherActivity.HomeActivityTeacher;


public class MainActivity extends AppCompatActivity {
    private Button signInButton;
    private FirebaseAuth auth;
    private EditText signinEmail;
    private EditText signinPassword;
    private Constants.ROLES roleUser;
    private String email;
    private String pass;
    private String uid;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        signinEmail = findViewById(R.id.email_auth);
        signinPassword = findViewById(R.id.pass_auth);
        signInButton = findViewById(R.id.sign_in);

        signInButton.setOnClickListener(v -> auth());

        if(isAuth()){
            authSucccesfull();
        }
    }
    private void auth(){
        email = signinEmail.getText().toString();
        pass = signinPassword.getText().toString();

        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!pass.isEmpty()) {
                auth.signInWithEmailAndPassword(email, pass)
                        .addOnSuccessListener(e -> {
                            uid = e.getUser().getUid();
                            getRole();
                        }).addOnFailureListener(e ->
                                Toast.makeText(MainActivity.this, "Введены некорректные данные", Toast.LENGTH_LONG).show());
            }else {
                signinPassword.setError("Введите пароль");
            }
        }else if(email.isEmpty()){
            signinEmail.setError("Email не заполнен");
        } else{
            signinEmail.setError("Введен некорректный email");
        }
    }

    private void saveAuth(){
        SharedPreferences preferences = getSharedPreferences(AUTH_FILE_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PASS_FROM_AUTH_FILE, pass);
        editor.putString(LOGIN_FROM_AUTH_FILE, email);
        editor.putString(UID_USER_AUTH_FILE, uid);
        editor.putString(ROLE_USER_AUTH_FILE, roleUser.toString().replaceAll("\"",""));
        editor.apply();

        if(checkAllDataInPref()){
            Toast.makeText(MainActivity.this, "Авторизация сохранена",Toast.LENGTH_LONG).show();
        } else Toast.makeText(MainActivity.this, "Авторизация не сохранена",Toast.LENGTH_LONG).show();
    }
    private boolean isAuth(){
        SharedPreferences preferences = getSharedPreferences(AUTH_FILE_NAME,MODE_PRIVATE);
        if(preferences.contains(LOGIN_FROM_AUTH_FILE)) {
            String email = preferences.getString(LOGIN_FROM_AUTH_FILE, "");
            String pass = preferences.getString(PASS_FROM_AUTH_FILE, "");
            String role = preferences.getString(ROLE_USER_AUTH_FILE, "");
            uid = preferences.getString(UID_USER_AUTH_FILE,"");
            signinEmail.setText(email);
            signinPassword.setText(pass);
            roleUser = Constants.ROLES.valueOf(role.replaceAll("\"",""));
            return true;
        }else {
            return false;
        }
    }

    private void authSucccesfull(){
        if(!isAuth()){
           saveAuth();
        }
        redirect();
    }

    private void redirect(){
        Intent intent;
        switch (roleUser){
            case STUDENT: intent = new Intent(MainActivity.this, HomeActivityStudent.class); this.startActivity(intent);break;
            case TEACHER:intent = new Intent(MainActivity.this, HomeActivityTeacher.class); this.startActivity(intent);break;
            case ADMIN:break;
        }
    }
    private boolean checkAllDataInPref(){
        SharedPreferences preferences = getSharedPreferences(AUTH_FILE_NAME,MODE_PRIVATE);;
        return preferences.contains(LOGIN_FROM_AUTH_FILE) && preferences.contains(PASS_FROM_AUTH_FILE) && preferences.contains(ROLE_USER_AUTH_FILE) && preferences.contains(UID_USER_AUTH_FILE);
    }

    private void getRole(){
        RetrofitService retrofit = new RetrofitService();
        RoleService roleService = retrofit.createService(RoleService.class);
        Call<ResponseBody> call = roleService.getRoleByUid(uid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    try {
                        String role = response.body().string();
                        roleUser = Constants.ROLES.valueOf(role.replaceAll("\"", ""));
                        authSucccesfull();
                    }catch(IOException e){
                        Log.e("RESPONSE", e.getMessage());
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Ошибка валидации пользователя. Обратитесь к администратору.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка сервера. Мы скоро всё починим...", Toast.LENGTH_LONG).show();
            }

        });
    }
}