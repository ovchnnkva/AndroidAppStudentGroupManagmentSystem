package com.hfad.studentsystemapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SessionScheduleActivity extends AppCompatActivity {
    private List<SessiaFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_schedule);
        initData();
        RecyclerView recyclerView = findViewById(R.id.container_session);
        SessiaAdapter adapter = new SessiaAdapter(this, fragments);
        recyclerView.setAdapter(adapter);

    }
    private void initData(){
        TextView textView = findViewById(R.id.type_session);
        textView.setText("Лето 2023");
        fragments.add(new SessiaFragment("Веб-технологии","01 июня","a.504", "Кривошеев А.П."));
        fragments.add(new SessiaFragment("Имитационное моделирование","05 июня","a.510", "Олишевский Д.П."));
    }
}