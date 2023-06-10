package ru.sfedu.studentsystem.adminActivity.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.model.Discipline;
import ru.sfedu.studentsystem.services.DisciplineService;
import ru.sfedu.studentsystem.services.RetrofitService;

public class AppendDisciplineDialog {
    private Dialog dialog;
    private EditText nameDiscipline;
    private Spinner typeAttestation;
    private EditText maxScoreSemester;
    private Button save;
    private Button close;
    private RetrofitService retrofitService;
    private String typeAttestationStr;
    private long teacherId;
    private Discipline discipline = new Discipline();

    public Discipline showDialog(Context context, long id){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.append_discipline_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        retrofitService = new RetrofitService();

        nameDiscipline = dialog.findViewById(R.id.create_discipline_dialog);
        maxScoreSemester = dialog.findViewById(R.id.max_score_semester_append_discipline_dialog);
        typeAttestation = dialog.findViewById(R.id.type_attestation_append_discipline_dialog);
        save = dialog.findViewById(R.id.save_discipline_dialog_append);
        close = dialog.findViewById(R.id.close_button_append_dialog_discipline);

        teacherId = id;

        initSpinner();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    saveDiscipline();
                    discipline.setName(nameDiscipline.getText().toString());
                } else {
                    Toast.makeText(context, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );

        dialog.show();
        return discipline;
    }
    private void initSpinner(){
        String[] attestationsType = {"Экзамен", "Зачет", "Дифф. зачет"};
        ArrayAdapter<String> adapter = new ArrayAdapter(dialog.getContext(), android.R.layout.simple_spinner_item, attestationsType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeAttestation.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                typeAttestationStr = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        typeAttestation.setOnItemSelectedListener(itemSelectedListener);
    }
    private void saveDiscipline(){
        DisciplineService service = retrofitService.createService(DisciplineService.class);
        Call<Integer> call = service.registerDiscipline(nameDiscipline.getText().toString(), typeAttestationStr, Integer.parseInt(maxScoreSemester.getText().toString().replaceAll(" ", "")));
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    if(response.body() > 0){
                        getDisciplineId();
                    } else {
                        Toast.makeText(dialog.getContext(), "Ошибка сервера", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(dialog.getContext(), "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getDisciplineId(){
        DisciplineService service = retrofitService.createService(DisciplineService.class);
        Call<Long> call = service.getDIsciplineIdByAllParam(nameDiscipline.getText().toString(), typeAttestationStr, Integer.parseInt(maxScoreSemester.getText().toString().replaceAll(" ", "")));
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if(response.isSuccessful()){
                    appendDisciplineToTeacher(response.body());
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Toast.makeText(dialog.getContext(), "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void appendDisciplineToTeacher(long disciplineId){
        DisciplineService service = retrofitService.createService(DisciplineService.class);
        Call<Integer> call = service.appendDisciplineToTeacher(disciplineId, teacherId);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    Toast.makeText(dialog.getContext(), "Запись сохранена", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(dialog.getContext(), "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });

    }
    private boolean validation(){
        return !maxScoreSemester.getText().toString().equals("") && !nameDiscipline.getText().toString().equals("");
    }
}