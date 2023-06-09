package ru.sfedu.studentsystem.adminActivity.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import ru.sfedu.studentsystem.model.StudyGroup;

public class StudyGroupSearchAdapter extends ArrayAdapter<StudyGroup> {

    private Context context;
    private StudyGroup[] groups;

    public StudyGroupSearchAdapter(@NonNull Context context, int resource, StudyGroup[] groups) {
        super(context, resource, groups);
        this.groups = groups;
        this.context = context;
    }

    @Override
    public int getCount(){
        return groups.length;
    }

    @Override
    public StudyGroup getItem(int position){
        return groups[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(groups[position].getGroupsCode());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(groups[position].getGroupsCode());

        return label;
    }
}
