package ru.sfedu.studentsystem.studentActivities.recycle.fragments;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import ru.sfedu.studentsystem.model.Event;

public class EventFragment extends Fragment  implements View.OnClickListener {
    private String name;
    private String date;
    private String location;

    EventFragment(String name, String date, String location){
        this.name = name;
        this.date = date;
        this.location = location;
    }

    public EventFragment(Event event){
        this.name = event.getName();
        this.date = formattingDate(event.getDate(), event.getTime());
        this.location = event.getLocation();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String formattingDate(String date, String time){
        StringBuilder timeBuilder = new StringBuilder(time);
        return date + " "+timeBuilder.substring(0,5);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
    }
}
