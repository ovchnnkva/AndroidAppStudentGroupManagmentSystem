package ru.sfedu.studentsystem.studentActivities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.model.Event;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final List<Event> events;

    EventAdapter(Context context, List<Event> events){
        this.events=events;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_event, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        Event event = events.get(position);
        holder.nameView.setText(event.getName());
        holder.dateView.setText(event.getDate());
        holder.locationView.setText(event.getLocation());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView nameView;
        final TextView dateView;
        final TextView locationView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.event_name);
            dateView = itemView.findViewById(R.id.event_date);
            locationView = itemView.findViewById(R.id.evemt_location);
        }
    }
}
