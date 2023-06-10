package ru.sfedu.studentsystem.adminActivity.recycle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.adminActivity.recycle.fragment.DisciplineTeacherShortFragment;

public class DisciplineTeacherShortAdapter extends RecyclerView.Adapter<DisciplineTeacherShortAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<DisciplineTeacherShortFragment> fragments;

    public DisciplineTeacherShortAdapter(Context context, List<DisciplineTeacherShortFragment> fragments){
        this.inflater = LayoutInflater.from(context);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_short_teacher_discipline, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DisciplineTeacherShortFragment fragment = fragments.get(position);
        holder.nameView.setText(fragment.getNameDiscipline());
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView nameView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name_discipline_short_teacher_fragment);
        }
    }
}
