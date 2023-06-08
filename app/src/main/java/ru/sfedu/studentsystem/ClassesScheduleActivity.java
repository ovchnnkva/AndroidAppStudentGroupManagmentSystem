package ru.sfedu.studentsystem;

import static ru.sfedu.studentsystem.Constants.AUTH_FILE_NAME;
import static ru.sfedu.studentsystem.Constants.ROLE_USER_AUTH_FILE;
import static ru.sfedu.studentsystem.Constants.UID_USER_AUTH_FILE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sfedu.studentsystem.model.Event;
import ru.sfedu.studentsystem.model.Schedule;
import ru.sfedu.studentsystem.model.Student;
import ru.sfedu.studentsystem.model.Teacher;
import ru.sfedu.studentsystem.services.EventService;
import ru.sfedu.studentsystem.services.RetrofitService;
import ru.sfedu.studentsystem.services.ScheduleService;
import ru.sfedu.studentsystem.services.StudentService;
import ru.sfedu.studentsystem.services.TeacherService;

public class ClassesScheduleActivity extends AppCompatActivity {
    private static final String[] weekDay = {"Воскресенье","Понедельник","Вторник","Среда","Четверг","Пятница","Суббота"};
    private RetrofitService retrofit;
    private String actualWeekDay;
    private ProgressBar loading;
    private Constants.ROLES role = Constants.ROLES.STUDENT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_schedule);

        loading = findViewById(R.id.loading_events_schedule);

        retrofit = new RetrofitService();

       actualWeekDay = weekDay[this.getIntent().getIntExtra("weekDay", 0)];
       Log.d("WEEKDAY", actualWeekDay);
       initUid();
    }
    private void initUid(){

        SharedPreferences pref = getSharedPreferences(AUTH_FILE_NAME, MODE_PRIVATE);
        String uid = pref.getString(UID_USER_AUTH_FILE,"");
        Log.d("STUDENT", uid);
        role = Constants.ROLES.valueOf(pref.getString(ROLE_USER_AUTH_FILE, ""));
        switch (role) {
            case STUDENT:{
                initStudent(uid);
                break;
            }
            case TEACHER:{
                initTeacher(uid);
                break;
            }
        }
    }
    private void initTeacher(String uid){
        Log.d("TEACHER", "get teacher with uid: " + uid);

        TeacherService service = retrofit.createService(TeacherService.class);
        Call<Teacher> call = service.getTeacherByUid(uid);
        call.enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                if(response.isSuccessful()){
                    initEventsIds(response.body().getId());
                }
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {
                Toast.makeText(ClassesScheduleActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initEventsIds(Long teacherId){
        Log.d("EVENTS", "get events ids by teachers id: " + teacherId);

        EventService service = retrofit.createService(EventService.class);
        Call<List<Long>> call = service.getEventsIdByTeacherId(teacherId);
        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if(response.isSuccessful()){
                    response.body().forEach(id -> initEventById(id));
                } else loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {

            }
        });
    }
    private void initEventById(Long id){
        Log.d("EVENTS", "get by id " + id);

        EventService service = retrofit.createService(EventService.class);
        Call<Event> call = service.getEventById(id);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if(response.isSuccessful()){
                    if(response.body() != null) {
                        Event event = response.body();
                        if (event.getDate().equals(actualWeekDay))
                            buildViewByTimeEvent(event);
                        else loading.setVisibility(View.INVISIBLE);
                    }

                } else {
                    emptyClasses();
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Toast.makeText(ClassesScheduleActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initStudent(String uid){
        Log.d("STUDENT", "get with student uid "+uid);

        StudentService service = retrofit.createService(StudentService.class);
        Call<Student> call = service.getStudentByUid(uid);
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
                loading.setVisibility(View.INVISIBLE);
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
                        initEventsByScheduleId(response.body().getId());
                    }
                } else {
                    Toast.makeText(ClassesScheduleActivity.this, "Расписание ещё не опубликовано", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Schedule> call, Throwable t) {
                Toast.makeText(ClassesScheduleActivity.this, "Ошибка сервера. Повторите попытку позже", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void initEventsByScheduleId(Long scheduleId){
        Log.d("EVENTS", "get by schedule id " + scheduleId);

        EventService service = retrofit.createService(EventService.class);
        Call<List<Event>> call = service.getEventByScheduleIdAndWeekDay(scheduleId, actualWeekDay);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null) {
                        response.body().forEach(e -> initTeacherIdForEvent(e));
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
    private void initTeacherIdForEvent(Event event){
        Log.d("EVENT", "get teacher for event with id: " +event.getIdEvent());

        TeacherService service = retrofit.createService(TeacherService.class);
        Call<List<Long>> call = service.getTeachersIdsByEventId(event.getIdEvent());
        call.enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if(response.isSuccessful()){
                    response.body().forEach(id -> initTeacher(id, event));
                }
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {

            }
        });
    }
    private void initTeacher(Long teacherId, Event event){
        Log.d("TEACHER", "get teacher with id: " + teacherId);

        TeacherService service = retrofit.createService(TeacherService.class);
        Call<Teacher> call = service.getTeacherById(teacherId);
        call.enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                if(response.isSuccessful()){
                    event.appendTeacher(response.body());
                    buildViewByTimeEvent(event);
                }
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {

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
        if(role.equals(Constants.ROLES.STUDENT)) {
            String teachers = event.getTeachers().stream().map(Object::toString).collect(Collectors.joining(","));
            teacherClass.setText(teachers);
        }
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