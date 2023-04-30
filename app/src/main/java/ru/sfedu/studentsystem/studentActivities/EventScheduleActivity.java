package ru.sfedu.studentsystem.studentActivities;

import static ru.sfedu.studentsystem.model.Constants.AUTH_FILE_NAME;
import static ru.sfedu.studentsystem.model.Constants.UID_USER_AUTH_FILE;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.studentsystem.R;
import ru.sfedu.studentsystem.model.Constants;
import ru.sfedu.studentsystem.model.Event;
import ru.sfedu.studentsystem.model.Schedule;
import ru.sfedu.studentsystem.model.Student;
import ru.sfedu.studentsystem.services.EventService;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.services.ScheduleService;
import ru.sfedu.studentsystem.services.StudentService;
import ru.sfedu.studentsystem.studentActivities.recycle.adapters.EventAdapter;
import ru.sfedu.studentsystem.studentActivities.recycle.fragments.EventFragment;

@RequiresApi(api = Build.VERSION_CODES.N)
public class EventScheduleActivity extends FragmentActivity {
    private RetrofitService retrofit;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_schedule);

        loading = findViewById(R.id.loading);

        retrofit = new RetrofitService();

        initStudentUid();
    }

    private void initStudent(String uid){
        Log.d("STUDENT", "get with student uid "+uid);

        StudentService service = retrofit.createService(StudentService.class);
        Call<Student> call = service.getSTudentByUid(uid);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if(response.isSuccessful()) {
                    Student student = response.body();
                    initSchedule(student.getStudyGroupId());
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(EventScheduleActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initSchedule(Long groupId){
        Log.d("SCHEDULE","get with group id " + groupId);

        ScheduleService service = retrofit.createService(ScheduleService.class);
        Call<Schedule> call = service.getScheduleByGroupIdAndType(groupId, Constants.TypeSchedule.EVENTS.toString());
        call.enqueue(new Callback<Schedule>() {
            @Override
            public void onResponse(Call<Schedule> call, Response<Schedule> response) {
                if(response.isSuccessful()){
                    if((response.body() != null)) {
                        initEvents(response.body().getId());
                    }
                } else {
                    Toast.makeText(EventScheduleActivity.this, "Расписание ещё не опубликовано", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Schedule> call, Throwable t) {
                Toast.makeText(EventScheduleActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initEvents(Long scheduleId){
        Log.d("EVENTS", "get by schedule id " + scheduleId);

        EventService service = retrofit.createService(EventService.class);
        Call<List<Event>> call = service.getEventByScheduleId(scheduleId);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null) {
                        initView(response.body());
                    }
                } else {
                    Toast.makeText(EventScheduleActivity.this, "Расписание ещё не опубликовано", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(EventScheduleActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initView(List<Event> events){
        loading.setVisibility(View.INVISIBLE);

        List<EventFragment> fragments = new ArrayList<>();
        events.forEach(e -> {
            Log.d("EVENT",e.toString());
            fragments.add(new EventFragment(e));
        });

        RecyclerView recyclerView = findViewById(R.id.container_event);
        EventAdapter adapter = new EventAdapter(this, fragments);

        recyclerView.setAdapter(adapter);
    }

    private void initStudentUid(){
        SharedPreferences pref = getSharedPreferences(AUTH_FILE_NAME, MODE_PRIVATE);
        String uid = pref.getString(UID_USER_AUTH_FILE,"");
        Log.d("STUDENT", uid);

        initStudent(uid);
    }
}