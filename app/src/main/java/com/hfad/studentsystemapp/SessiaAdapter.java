package com.hfad.studentsystemapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SessiaAdapter extends RecyclerView.Adapter<SessiaAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<SessiaFragment> fragments;


    SessiaAdapter(Context context, List<SessiaFragment> fragments){
        this.fragments = fragments;
        inflater =  LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SessiaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_session, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessiaAdapter.ViewHolder holder, int position) {
        SessiaFragment fragment = fragments.get(position);
        holder.nameView.setText(fragment.getName());
        holder.dateView.setText(fragment.getDate());
        holder.locationView.setText(fragment.getLocation());
        holder.teacherView.setText(fragment.getTeacher());
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView nameView;
        final TextView dateView;
        final TextView locationView;
        final TextView teacherView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.sessia_name);
            dateView = itemView.findViewById(R.id.sessia_date);
            locationView = itemView.findViewById(R.id.sessia_location);
            teacherView = itemView.findViewById(R.id.sessia_teacher);
        }
    }
}
