package com.hfad.studentsystemapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LectionMaterialAdapter extends RecyclerView.Adapter<LectionMaterialAdapter.ViewHolder>{
    private final List<LectionMaterialFragment> fragments;
    private final LayoutInflater inflater;

    LectionMaterialAdapter(Context context,List<LectionMaterialFragment> fragments) {
        this.fragments = fragments;
        inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public LectionMaterialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_education_material,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LectionMaterialFragment leactionMaterial=fragments.get(position);
        holder.nameView.setText(leactionMaterial.getName());
        holder.teacherView.setText(leactionMaterial.getTeacher());
        holder.disciplineView.setText(leactionMaterial.getDiscipline());
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView nameView;
        final TextView teacherView;
        final TextView disciplineView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.pract_material_name);
            teacherView = itemView.findViewById(R.id.pract_material_teacher);
            disciplineView = itemView.findViewById(R.id.pract_material_discipline);
        }
    }
}

