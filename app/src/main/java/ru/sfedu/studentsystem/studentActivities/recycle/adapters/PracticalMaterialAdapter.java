package ru.sfedu.studentsystem.studentActivities.recycle.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.studentActivities.EducationMaterialActivity;
import ru.sfedu.studentsystem.studentActivities.recycle.fragments.MaterialFragment;

public class PracticalMaterialAdapter extends RecyclerView.Adapter<PracticalMaterialAdapter.ViewHolder>{
   private final LayoutInflater inflater;
   private final List<MaterialFragment> fragments;

   public PracticalMaterialAdapter(Context context, List<MaterialFragment> fragments){
      this.fragments = fragments;
      inflater = LayoutInflater.from(context);
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = inflater.inflate(R.layout.fragment_education_material, parent,false);
      return new ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      MaterialFragment practicalMaterial = fragments.get(position);
      holder.nameView.setText(practicalMaterial.getName());
      holder.teacherView.setText(practicalMaterial.getTeacher());
      holder.disciplineView.setText(practicalMaterial.getDiscipline());
      if(practicalMaterial.isPractical()) {
          String maxScoreStr = practicalMaterial.getStudentScore() + "/" + practicalMaterial.getMaxScore();
          holder.maxScoreView.setText(maxScoreStr);
      }

      holder.appendScoreView.setText(practicalMaterial.getAppendScore());

      holder.goToMaterialButton.setOnClickListener(v -> {
          Intent intent = new Intent(v.getContext(), EducationMaterialActivity.class);
          intent.putExtra("name",practicalMaterial.getName());
          intent.putExtra("discipline",practicalMaterial.getDiscipline());
          intent.putExtra("deadline", practicalMaterial.getDeadline());
          intent.putExtra("description", practicalMaterial.getTeacherComment());
          intent.putExtra("score",practicalMaterial.getStudentScore()+"/"+practicalMaterial.getMaxScore());
          intent.putExtra("studentScore", practicalMaterial.getStudentScore());
          intent.putExtra("file",practicalMaterial.getFile());
          intent.putExtra("isPract", practicalMaterial.isPractical());

          v.getContext().startActivity(intent);
      });
   }

   @Override
   public int getItemCount() {
      return fragments.size();
   }

   public static class ViewHolder extends RecyclerView.ViewHolder{
       final TextView nameView;
       final TextView teacherView;
       final TextView disciplineView;
       final TextView maxScoreView;
       final Button goToMaterialButton;
       final TextView appendScoreView;


       public ViewHolder(@NonNull View itemView) {
          super(itemView);
          nameView = itemView.findViewById(R.id.pract_material_name);
          teacherView = itemView.findViewById(R.id.pract_material_teacher);
          disciplineView = itemView.findViewById(R.id.pract_material_discipline);
          maxScoreView = itemView.findViewById(R.id.pract_material_max_score);
          goToMaterialButton=itemView.findViewById(R.id.go_to_material_activity);
          appendScoreView = itemView.findViewById(R.id.append_score_material_fragment);
       }
    }
}
