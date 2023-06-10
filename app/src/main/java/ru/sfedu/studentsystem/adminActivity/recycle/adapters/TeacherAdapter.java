package ru.sfedu.studentsystem.adminActivity.recycle.adapters;

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
import ru.sfedu.studentsystem.adminActivity.TeacherDetailActivity;
import ru.sfedu.studentsystem.adminActivity.recycle.fragment.TeacherFragment;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<TeacherFragment> fragments;

    public TeacherAdapter(Context context, List<TeacherFragment> fragments){
        this.inflater = LayoutInflater.from(context);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_search_teacher, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeacherFragment fragment = fragments.get(position);
        holder.nameView.setText(fragment.getName());

        holder.goToDetailButton.setOnClickListener(event -> {
            Intent intent = new Intent(event.getContext(), TeacherDetailActivity.class);
            intent.putExtra("teacherName", fragment.getName());
            intent.putExtra("teacherId", fragment.getTeacherId());
            event.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView nameView;
        final Button goToDetailButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name_search_teacher_fragment);
            goToDetailButton = itemView.findViewById(R.id.go_to_detail_search_teacher_fragment);
        }
    }
}
