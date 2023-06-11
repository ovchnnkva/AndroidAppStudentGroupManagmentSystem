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
import java.util.Objects;

import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.adminActivity.StudyGroupDetailsActivity;
import ru.sfedu.studentsystem.adminActivity.recycle.fragment.StudyGroupFragment;

public class StudyGroupAdapter extends RecyclerView.Adapter<StudyGroupAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<StudyGroupFragment> fragments;

    public StudyGroupAdapter(Context context, List<StudyGroupFragment> fragments){
        this.inflater = LayoutInflater.from(context);
        this.fragments = fragments;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_study_group_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudyGroupFragment fragment = fragments.get(position);

        holder.codeView.setText(fragment.getCode());
        holder.specializationView.setText(fragment.getSpecialization());
        holder.courseView.setText(Objects.toString(fragment.getCourse()));

        holder.goToDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StudyGroupDetailsActivity.class);
                intent.putExtra("code", fragment.getCode());
                intent.putExtra("specialization", fragment.getSpecialization());
                intent.putExtra("course", fragment.getCourse());
                intent.putExtra("groupId", fragment.getGroupId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView codeView;
        final TextView specializationView;
        final TextView courseView;
        final Button goToDetailButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            codeView = itemView.findViewById(R.id.group_code_search_fragment);
            specializationView = itemView.findViewById(R.id.specialization_group_search_fragment);
            courseView = itemView.findViewById(R.id.course_group_search);
            goToDetailButton = itemView.findViewById(R.id.go_to_detail_study_group);
        }
    }
}
