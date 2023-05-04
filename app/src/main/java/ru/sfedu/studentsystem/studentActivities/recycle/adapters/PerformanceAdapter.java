package ru.sfedu.studentsystem.studentActivities.recycle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.studentActivities.recycle.fragments.PerformanceFragment;

public class PerformanceAdapter extends RecyclerView.Adapter<PerformanceAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<PerformanceFragment> fragments;

    public PerformanceAdapter(Context context, List<PerformanceFragment> fragments){
        this.fragments = fragments;
        inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_performance,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PerformanceFragment fragment = fragments.get(position);
        holder.typeAttestation.setText(fragment.getTypeAttestation());
        holder.percentPerformance.setText(fragment.getPercentPerformance() + "%");
        holder.actualScore.setText(fragment.getActualScores());
        holder.teachersName.setText(fragment.getTeachers());
        holder.nameDiscipline.setText(fragment.getNameDiscipline());
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView nameDiscipline;
        final TextView teachersName;
        final TextView percentPerformance;
        final TextView actualScore;
        final TextView typeAttestation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameDiscipline = itemView.findViewById(R.id.name_discipline_fragment);
            teachersName = itemView.findViewById(R.id.teachers_fragment);
            percentPerformance = itemView.findViewById(R.id.performance_percent);
            actualScore = itemView.findViewById(R.id.actual_performance);
            typeAttestation = itemView.findViewById(R.id.type_attestation);
        }
    }

}
