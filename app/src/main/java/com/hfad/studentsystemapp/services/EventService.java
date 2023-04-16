package com.hfad.studentsystemapp.services;

import com.hfad.studentsystemapp.model.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EventService {
    @GET("event/get/scheduleid/{id}")
    Call<List<Event>> getEventByScheduleId(@Path("id")Long id);
    @POST("event/create/{name}/{date}/{time}")
    Call<Integer> registryEvent(@Path("name") String name, @Path("date") String date,
                                @Path("time") String time);
    @PUT("event/update/{id}/{name}/{date}/{time}/{scheduleid}")
    Call<Integer> updateEvent(@Path("id")Long id, @Path("name") String name,
                              @Path("date") String date, @Path("time") String time,
                              @Path("scheduleid") Long scheduleid);
    @DELETE("event/delete/{id}")
    Call<Integer> deleteEvent(@Path("id")Long id);
}
