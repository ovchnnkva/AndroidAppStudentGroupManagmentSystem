package com.hfad.studentsystemapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PracticalMaterialAdapter extends RecyclerView.Adapter<PracticalMaterialAdapter.ViewHolder>{
   private final LayoutInflater inflater;
   private final List<PracticalMaterialFragment> fragments;

   PracticalMaterialAdapter(Context context, List<PracticalMaterialFragment> fragments){
      this.fragments = fragments;
      inflater = LayoutInflater.from(context);
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = inflater.inflate(R.layout.fragment_education_material,parent,false);
      return new ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      PracticalMaterialFragment practicalMaterial = fragments.get(position);
      holder.nameView.setText(practicalMaterial.getName());
      holder.teacherView.setText(practicalMaterial.getTeacher());
      holder.disciplineView.setText(practicalMaterial.getDiscipline());
      holder.maxScoreView.setText(practicalMaterial.getStudentScore()+"/"+practicalMaterial.getMaxScore());
      holder.deadlineView.setText(practicalMaterial.getDeadline());
   }

   @Override
   public int getItemCount() {
      return fragments.size();
   }

   public static class ViewHolder extends RecyclerView.ViewHolder{
       final TextView nameView;
       final TextView teacherView;
       final TextView deadlineView;
       final TextView disciplineView;
       final TextView maxScoreView;

       public ViewHolder(@NonNull View itemView) {
          super(itemView);
          nameView = itemView.findViewById(R.id.pract_material_name);
          teacherView = itemView.findViewById(R.id.pract_material_teacher);
          deadlineView = itemView.findViewById(R.id.pract_material_deadline);
          disciplineView = itemView.findViewById(R.id.pract_material_discipline);
          maxScoreView = itemView.findViewById(R.id.pract_material_max_score);
       }
    }
}
