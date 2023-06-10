package ru.sfedu.studentsystem.teacherActivity.recycle.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import ru.sfedu.studentsystem.model.Discipline;

public class DisciplinesAdapter extends ArrayAdapter<Discipline> {
    private Context context;
    private Discipline[] disciplines;

    public DisciplinesAdapter(@NonNull Context context, int resource, Discipline[] disciplines) {
        super(context, resource, disciplines);
        this.disciplines = disciplines;
        this.context = context;
    }
    @Override
    public int getCount(){
        return disciplines.length;
    }

    @Override
    public Discipline getItem(int position){
        return disciplines[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(disciplines[position].getName());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(disciplines[position].getName());

        return label;
    }
}
