package ru.sfedu.studentsystem.studentActivities;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class EventFragment extends Fragment  implements View.OnClickListener {
    private String name;
    private String date;
    private String location;
    EventFragment(String name, String date, String location){
        this.name = name;
        this.date = date;
        this.location = location;
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

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
    }
}
