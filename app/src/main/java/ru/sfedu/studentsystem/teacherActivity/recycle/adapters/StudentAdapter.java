package ru.sfedu.studentsystem.teacherActivity.recycle.adapters;

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
import ru.sfedu.studentsystem.teacherActivity.DetailStudentActivity;
import ru.sfedu.studentsystem.teacherActivity.recycle.fragments.StudentFragment;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<StudentFragment> fragments;

    public StudentAdapter(Context context, List<StudentFragment> fragments){
        this.inflater = LayoutInflater.from(context);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_search_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {
        StudentFragment student = fragments.get(position);
        holder.nameView.setText(student.getName());
        holder.groupCodeView.setText(student.getGroupCode());
        holder.specializationView.setText(student.getSpecialization());

        holder.goToDetailButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailStudentActivity.class);
            intent.putExtra("name", student.getName());
            intent.putExtra("groupCode", student.getGroupCode());
            intent.putExtra("specialization", student.getSpecialization());
            intent.putExtra("birthday", student.getBirthday());
            intent.putExtra("groupid", student.getGroupId());
            intent.putExtra("id", student.getStudentId());

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView nameView;
        final TextView groupCodeView;
        final TextView specializationView;
        final Button goToDetailButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name_student_fragment);
            groupCodeView = itemView.findViewById(R.id.group_fragment);
            specializationView = itemView.findViewById(R.id.specialization_fragment);
            goToDetailButton = itemView.findViewById(R.id.button_go_to_detail);
        }
    }
}
