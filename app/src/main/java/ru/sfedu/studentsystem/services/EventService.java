package ru.sfedu.studentsystem.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.sfedu.studentsystem.model.Event;

public interface EventService {
    @GET("event/get/scheduleid/{id}")
    Call<List<Event>> getEventByScheduleId(@Path("id")Long id);
    @GET("event/get/weekday/{id}/{week}")
    Call<List<Event>> getEventByScheduleIdAndWeekDay(@Path("id") Long id, @Path("week")String week);
    @POST("event/create/{name}/{date}/{time}/{location}/{teacher}")
    Call<Integer> registryEvent(@Path("name") String name, @Path("date") String date,
                                @Path("time") String time, @Path("location") String location,
                                @Path("teacher") String teacher);
    @PUT("event/update/{id}/{name}/{date}/{time}/{scheduleid}/{location}/{teacher}")
    Call<Integer> updateEvent(@Path("id")Long id, @Path("name") String name,
                              @Path("date") String date, @Path("time") String time,
                              @Path("scheduleid") Long scheduleid, @Path("location") String location,
                              @Path("teacher") String teacher);
    @DELETE("event/delete/{id}")
    Call<Integer> deleteEvent(@Path("id")Long id);
}
