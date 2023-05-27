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
import ru.sfedu.studentsystem.studentActivities.recycle.fragments.DetailPerformanceFragment;

public class DetailPerformanceAdapter extends  RecyclerView.Adapter<DetailPerformanceAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<DetailPerformanceFragment> fragments;

    public DetailPerformanceAdapter(Context context, List<DetailPerformanceFragment> fragments){
        this.fragments = fragments;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_detail_performance,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailPerformanceFragment fragment = fragments.get(position);
        holder.nameTaskView.setText(fragment.getNameTask());
        holder.dateView.setText(fragment.getDateAppendScore());
        holder.scoresView.setText(fragment.getScores());
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView nameTaskView;
        final TextView scoresView;
        final TextView dateView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTaskView = itemView.findViewById(R.id.task_name_fragment);
            scoresView = itemView.findViewById(R.id.task_score_fragment);
            dateView = itemView.findViewById(R.id.task_date_fragment);

        }
    }
}
