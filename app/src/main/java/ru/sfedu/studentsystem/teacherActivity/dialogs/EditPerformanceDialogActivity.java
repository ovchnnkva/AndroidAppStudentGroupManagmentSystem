package ru.sfedu.studentsystem.teacherActivity.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.model.PracticalMaterial;
import ru.sfedu.studentsystem.services.PracticalMaterialService;
import ru.sfedu.studentsystem.services.RetrofitService;

public class EditPerformanceDialogActivity  {
    private Dialog dialog;
    private Button save;
    private Button close;
    private EditText editName;
    private EditText editScore;
    private RetrofitService retrofit;

    public void showDialog(Context activity, PracticalMaterial material){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_edit_performance_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        save = dialog.findViewById(R.id.save_button_edit_dialog);
        close = dialog.findViewById(R.id.close_button_edit_dialog);

        retrofit = new RetrofitService();

        editName = dialog.findViewById(R.id.edit_name);
        editScore = dialog.findViewById(R.id.edit_score);

        editName.setText(material.getName());
        editScore.setText(Objects.toString(material.getStudentScore()));

        if ((!editScore.getText().toString().equals("")) && (!editName.getText().toString().equals(""))) {

            save.setOnClickListener(event -> {
                int score = Integer.parseInt(editScore.getText().toString());
                String name = editName.getText().toString();
                LocalDate dateNow = LocalDateTime.now().toLocalDate();

                material.setStudentScore(score);
                material.setName(name);
                try {
                    material.setDateScoreAppend(dateNow.toString());
                } catch (Exception e) {
                    Log.e("DATE", "invalid parse");
                }
                updateMaterial(material);
                dialog.dismiss();
            });
        } else {
            Toast.makeText(activity, "Поля не могут быть пустыми", Toast.LENGTH_LONG).show();
        }
        close.setOnClickListener(event -> dialog.dismiss());
        dialog.show();
    }

    private void updateMaterial(PracticalMaterial material) {
        PracticalMaterialService service = retrofit.createService(PracticalMaterialService.class);
        Log.d("DATE", material.getDateScoreAppend());
        Call<Integer> call = service.updatePracticalMaterialSCoresAndName(material.getId(), material.getName(),
                material.getStudentScore(), material.getDateScoreAppend());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Log.d("UPDATE", "update is successful");
                    Toast.makeText(dialog.getContext(), "Запись обновлена", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(dialog.getContext(), "Ошибка сервера. Не удалось обновить запись.", Toast.LENGTH_LONG).show();
            }
        });
    }

}