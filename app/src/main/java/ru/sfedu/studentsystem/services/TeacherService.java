package ru.sfedu.studentsystem.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.sfedu.studentsystem.model.Teacher;

public interface TeacherService {
    @POST("teacher/create/{name}/{uid}")
    Call<Integer> registerTeacher(@Path("name") String name, @Path("uid") String uid);
    @GET("teacher/get/id/{id}")
    Call<Teacher> getTeacherById(@Path("id")Long id);
    @GET("teacher/get/uid/{uid}")
    Call<Teacher> getTeacherByUid(@Path("uid") String uid);
    @GET("teacher/get/eventid/{eventid}")
    Call<List<Long>> getTeachersIdsByEventId(@Path("eventid") Long eventId);
    @GET("teacher/get/name/{name}")
    Call<List<Teacher>> getTeacherByName(@Path("name") String name);
    @GET("teacher/get/disciplineid/{disciplineid}")
    Call<List<Teacher>> getTeacherForDiscipline(@Path("disciplineid") Long disciplineId);
    @GET("teacher/get/all")
    Call<List<Teacher>> getAll();
    @PUT("teacher/update/{id}/{name}")
    Call<Integer> updateTeacher(@Path("id") Long id, @Path("name") String name);
    @DELETE("teacher/delete/{id}")
    Call<Integer> deleteTeacher(@Path("id") Long id);
}
