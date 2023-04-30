package ru.sfedu.studentsystem.studentActivities;

import static ru.sfedu.studentsystem.model.Constants.AUTH_FILE_NAME;
import static ru.sfedu.studentsystem.model.Constants.UID_USER_AUTH_FILE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class ClassesScheduleActivity extends AppCompatActivity {
    private static final String[] weekDay = {"Воскресенье","Понедельник","Вторник","Среда","Четверг","Пятница","Суббота"};
    private RetrofitService retrofit;
    private String actualWeekDay;
    private ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_schedule);

        loading = findViewById(R.id.loading_events_schedule);

        retrofit = new RetrofitService();

       actualWeekDay = this.getIntent().getStringExtra("weekDay");
       initStudentUid();
    }
    private void initStudentUid(){

        SharedPreferences pref = getSharedPreferences(AUTH_FILE_NAME, MODE_PRIVATE);
        String uid = pref.getString(UID_USER_AUTH_FILE,"");
        Log.d("STUDENT", uid);


        initStudent(uid);
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
                Toast.makeText(ClassesScheduleActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initSchedule(Long groupId){
        Log.d("SCHEDULE","get with group id " + groupId);

        ScheduleService service = retrofit.createService(ScheduleService.class);
        Call<Schedule> call = service.getScheduleByGroupIdAndType(groupId, Constants.TypeSchedule.CLASSES.toString());
        call.enqueue(new Callback<Schedule>() {
            @Override
            public void onResponse(Call<Schedule> call, Response<Schedule> response) {
                if(response.isSuccessful()){
                    if((response.body() != null) && (response.body().getTypeSchedule().equals(Constants.TypeSchedule.CLASSES))) {
                        initEvents(response.body().getId());
                    }
                } else {
                    Toast.makeText(ClassesScheduleActivity.this, "Расписание ещё не опубликовано", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Schedule> call, Throwable t) {
                Toast.makeText(ClassesScheduleActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initEvents(Long scheduleId){
        Log.d("EVENTS", "get by schedule id " + scheduleId);

        EventService service = retrofit.createService(EventService.class);
        Call<List<Event>> call = service.getEventByScheduleIdAndWeekDay(scheduleId, actualWeekDay);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null) {
                        response.body().forEach(e -> buildViewByTimeEvent(e));
                    }

                } else {
                    emptyClasses();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(ClassesScheduleActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void buildViewByTimeEvent(Event event){
        loading.setVisibility(View.INVISIBLE);

        visibleClasses();

        switch (event.getTime()){
            case "08:00:00":{
                createFirstClass(event);
                break;
            }
            case "09:50:00":{
                createSecondClass(event);
                break;
            }
            case "11:55:00":{
                createThirdClass(event);
                break;
            }
            case "13:45:00":{
                createFouthClass(event);
                break;
            }
            case "15:25:00": {
                createFifthClass(event);
                break;
            }
        }

    }
    private void createFirstClass(Event event){
        initClass(findViewById(R.id.name_discipline_first_class), findViewById(R.id.first_class_teacher),findViewById(R.id.first_class_cabinet), event);
    }
    private void createSecondClass(Event event){
        initClass(findViewById(R.id.name_discipline_second_class), findViewById(R.id.second_class_teacher),findViewById(R.id.second_class_cabinet), event);
    }
    private void createThirdClass(Event event){
        initClass(findViewById(R.id.name_discipline_third_class), findViewById(R.id.third_class_teacher),findViewById(R.id.third_class_cabinet), event);
    }
    private void createFouthClass(Event event){
        initClass(findViewById(R.id.name_discipline_fouth_class), findViewById(R.id.fouth_class_teacher),findViewById(R.id.fouth_class_cabinet), event);
    }
    private void createFifthClass(Event event){
        initClass(findViewById(R.id.name_discipline_fifth_class), findViewById(R.id.fifth_class_teacher),findViewById(R.id.fifth_class_cabinet), event);
    }
    private void initClass(TextView nameClass, TextView teacherClass, TextView locationClass, Event event){
        String locationStr = event.getLocation();
        if(locationStr.length()>5){
            locationStr = locationStr.replaceAll(",", "\n");

        }
        nameClass.setText(event.getName());
        teacherClass.setText(event.getTeacher());
        locationClass.setText(locationStr);
    }

    private void emptyClasses(){
        loading.setVisibility(View.INVISIBLE);

        findViewById(R.id.empty_classes).setVisibility(View.VISIBLE);
    }

    private void visibleClasses(){
        findViewById(R.id.week_type).setVisibility(View.VISIBLE);
        findViewById(R.id.time_first_class_1).setVisibility(View.VISIBLE);
        findViewById(R.id.time_first_class_2).setVisibility(View.VISIBLE);
        findViewById(R.id.time_second_class_1).setVisibility(View.VISIBLE);
        findViewById(R.id.time_second_class_2).setVisibility(View.VISIBLE);
        findViewById(R.id.time_third_class_1).setVisibility(View.VISIBLE);
        findViewById(R.id.time_third_class_2).setVisibility(View.VISIBLE);
        findViewById(R.id.time_fouth_class_1).setVisibility(View.VISIBLE);
        findViewById(R.id.time_fouth_class_2).setVisibility(View.VISIBLE);
        findViewById(R.id.time_fifth_class_1).setVisibility(View.VISIBLE);
        findViewById(R.id.time_fifth_class_2).setVisibility(View.VISIBLE);
        findViewById(R.id.time_sixth_class_1).setVisibility(View.VISIBLE);
        findViewById(R.id.time_sixth_class_2).setVisibility(View.VISIBLE);
    }

}