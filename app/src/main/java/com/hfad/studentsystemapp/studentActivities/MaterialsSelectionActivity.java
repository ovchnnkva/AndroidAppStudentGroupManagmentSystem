package com.hfad.studentsystemapp.studentActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hfad.studentsystemapp.R;

import java.util.ArrayList;
import java.util.List;

public class MaterialsSelectionActivity extends AppCompatActivity {
    private List<PracticalMaterialFragment> fragments = new ArrayList<>();
    private List<PracticalMaterialFragment> fragmentMade = new ArrayList<>();
    private List<PracticalMaterialFragment> fragmentsNotMade= new ArrayList<>();
    private List<LectionMaterialFragment> lectionFragment=new ArrayList<>();
    private Button madeButton;
    private Button notMadeButton;
    private Button lectionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials_selection);
        initData();
        RecyclerView recyclerView = findViewById(R.id.container_material);

        madeButton = findViewById(R.id.button_made);
        notMadeButton = findViewById(R.id.button_not_made);
        lectionButton = findViewById(R.id.button_lection_material);

        Context context = this;

        madeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PracticalMaterialAdapter adapter=new PracticalMaterialAdapter(context,fragmentMade);
                recyclerView.setAdapter(adapter);
            }
        });
        notMadeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PracticalMaterialAdapter adapter=new PracticalMaterialAdapter(context,fragmentsNotMade);
                recyclerView.setAdapter(adapter);
            }
        });
        lectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LectionMaterialAdapter adapter=new LectionMaterialAdapter(context,lectionFragment);
                recyclerView.setAdapter(adapter);
            }
        });



    }
    private void initData(){
        fragments.add(new PracticalMaterialFragment("/lab1.doc", "Лабораторная работа 1","Олишевский Д.П.","Имитационное моделирование", 20,"30.03.2023 23:59"));
        fragments.add(new PracticalMaterialFragment("/labWeb.doc", "Лабораторная работа 1","Кривошеев А.П.","Веб-технологии", 5,"11.03.2023 23:59"));
        fragments.get(1).setStudentScore(4);
        fragments.add(new PracticalMaterialFragment("/ormLab", "Лабораторная работа 2","Бурбелов М.О.", "Архитектера Информационных Систем",10,"01.04.2023 23:59"));
        for(PracticalMaterialFragment f:fragments) {
            if (f.isMade())
                fragmentMade.add(f);
            else fragmentsNotMade.add(f);
        }
        lectionFragment.add(new LectionMaterialFragment("Методичка","Архитектура информационных систем", "Жмайлов Б.Б."));
    }

}