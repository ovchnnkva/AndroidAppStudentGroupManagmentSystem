package ru.sfedu.studentsystem;

import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {
    private Button signInButton;
    private FirebaseAuth auth;
    private EditText signupEmail;
    private EditText signupPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.email_auth);
        signupPassword = findViewById(R.id.pass_auth);

        signInButton.setOnClickListener(v -> {
            String email = signupEmail.getText().toString().trim();
            String pass = signupPassword.getText().toString().trim();

            if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                if(!pass.isEmpty()){
                    auth.signInWithEmailAndPassword(email, pass)
                            .addOnSuccessListener(authResult -> Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show()).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    signupPassword.setError("Введите пароль");
                }
            }else if(email.isEmpty()){
                signupEmail.setError("Email не заполнен");
            } else{
                signupEmail.setError("Введен некорректный email");
            }

        });
    }
}