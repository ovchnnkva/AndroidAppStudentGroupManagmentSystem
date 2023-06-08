package ru.sfedu.studentsystem.teacherActivity.recycle.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.studentActivities.DetailPerformanceActivity;
import ru.sfedu.studentsystem.teacherActivity.recycle.fragments.StudentShortPerformanceFragment;

public class StudentDetailPerformanceShortAdapter extends RecyclerView.Adapter<StudentDetailPerformanceShortAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<StudentShortPerformanceFragment> fragments;

    public StudentDetailPerformanceShortAdapter(Context context, List<StudentShortPerformanceFragment> fragments){
        this.fragments = fragments;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public StudentDetailPerformanceShortAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_perfomance_short, parent,false);
        return new StudentDetailPerformanceShortAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentDetailPerformanceShortAdapter.ViewHolder holder, int position) {
        StudentShortPerformanceFragment fragment = fragments.get(position);
        Log.d("ADAPTER", fragment.getTypeAttestation());
        holder.typeAttestation.setText(fragment.getTypeAttestation());
        String percentPerformanceStr = fragment.getPercentPerformance() + "%";
        holder.percentPerformance.setText(percentPerformanceStr);
        holder.actualScore.setText(fragment.getActualScores());
        holder.nameDiscipline.setText(fragment.getNameDiscipline());

        Intent intent = new Intent(holder.itemView.getContext(), DetailPerformanceActivity.class);
        intent.putExtra("studentId", fragment.getStudentId());
        intent.putExtra("disciplineId", fragment.getDisciplineId());
        intent.putExtra("typeSemester", fragment.getTypeSemester());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        holder.goToAppendScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView nameDiscipline;
        final TextView percentPerformance;
        final TextView actualScore;
        final TextView typeAttestation;
        final Button goToAppendScoreButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameDiscipline = itemView.findViewById(R.id.name_discipline_short_performance);
            percentPerformance = itemView.findViewById(R.id.performance_percent_short_performance);
            actualScore = itemView.findViewById(R.id.actual_performance_short_performance);
            typeAttestation = itemView.findViewById(R.id.type_attestation_short_performance);
            goToAppendScoreButton = itemView.findViewById(R.id.append_score_short_performance);
        }
    }
}
