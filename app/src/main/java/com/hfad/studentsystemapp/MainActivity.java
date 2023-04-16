package com.hfad.studentsystemapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hfad.studentsystemapp.model.Discipline;
import com.hfad.studentsystemapp.model.Student;
import com.hfad.studentsystemapp.model.StudyGroup;
import com.hfad.studentsystemapp.services.DisciplineService;
import com.hfad.studentsystemapp.services.StudentService;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.31.188:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        DisciplineService service = retrofit.create(DisciplineService.class);

            Call<List<Discipline>> call = service.getDisciplineByName("хите");
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<List<Discipline>> call, Response<List<Discipline>> response) {
                    if(response.isSuccessful()){
                        List<Discipline> discipline = response.body();
                        Log.d("SERVER", discipline.toString());

                    }else{
                        Log.d("SERVER", response.message());
                    }

                }

                @Override
                public void onFailure(Call<List<Discipline>> call, Throwable t) {
                    Log.e("SERVER", "error");
                }
            });
    }
}