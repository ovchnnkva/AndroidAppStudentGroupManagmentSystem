package ru.sfedu.studentsystem.services;

import ru.sfedu.studentsystem.model.Schedule;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ScheduleService {
    @POST("schedule/create/{type}")
    Call<Integer> registrySchedule(@Path("type") String type);
    @GET("schedule/get/id/{id}")
    Call<Schedule> getScheduleById(@Path("id") Long id);
    @DELETE("schedule/delete/{id}")
    Call<Schedule> deleteSchedule(@Path("id")Long id);
    @GET("schedule/get/groupid/{groupid}/{type}")
    Call<Schedule> getScheduleByGroupIdAndType(@Path("groupid")Long groupid, @Path("type") String type);
}
