package ru.sfedu.studentsystem.studentActivities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.model.PracticalMaterial;
import ru.sfedu.studentsystem.services.PracticalMaterialService;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.studentActivities.recycle.adapters.LectionMaterialAdapter;
import ru.sfedu.studentsystem.studentActivities.recycle.adapters.PracticalMaterialAdapter;
import ru.sfedu.studentsystem.studentActivities.recycle.fragments.LectionMaterialFragment;
import ru.sfedu.studentsystem.studentActivities.recycle.fragments.PracticalMaterialFragment;

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
        onCall();
        for(PracticalMaterialFragment f:fragments) {
            if (f.isMade())
                fragmentMade.add(f);
            else fragmentsNotMade.add(f);
        }
        lectionFragment.add(new LectionMaterialFragment("Методичка","Архитектура информационных систем", "Жмайлов Б.Б."));

    }
    private void onCall(){
        RetrofitService retrofit = new RetrofitService();
        PracticalMaterialService service = retrofit.createService(PracticalMaterialService.class);
        Call<List<PracticalMaterial>> call = service.getPracticalMaterialByGroupId(1L);
        call.enqueue(new Callback<List<PracticalMaterial>>() {
            @Override
            public void onResponse(Call<List<PracticalMaterial>> call, Response<List<PracticalMaterial>> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    response.body().forEach(m -> fragments.add(new PracticalMaterialFragment(m)));
                }
            }

            @Override
            public void onFailure(Call<List<PracticalMaterial>> call, Throwable t) {
                Log.e("SERVER", "no materials");
            }
        });
    }

}