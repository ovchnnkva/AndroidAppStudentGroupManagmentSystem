package ru.sfedu.studentsystem.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.sfedu.studentsystem.model.Discipline;

public interface DisciplineService {
    @POST("discipline/create/{name}/{typeattestation}/{maxscore}")
    Call<Integer> registerDiscipline(@Path("name") String name, @Path("typeattestation") String typeAttestation,
                                     @Path("maxscore") int maxScore);
    @GET("discipline/get/id/{id}")
    Call<Discipline> getDisciplineById(@Path("id") Long id);
    @GET("discipline/get/name/{name}")
    Call<List<Discipline>> getDisciplineByName(@Path("name") String name);
    @GET("discipline/get/groupid/{groupid}")
    Call<List<Long>> getDisciplineByGroupId(@Path("groupid") Long id);
    @GET("discipline/get/teacherid/{teacherid}")
    Call<List<Long>> getDisciplinesByTeacherId(@Path("teacherid")Long id);
    @GET("discipline/get/all")
    Call<List<Discipline>> getAll();
    @PUT("discipline/update/{id}/{name}/{teacherid}/{typeattestation}/{maxscore}")
    Call<Integer> updateDiscipline(@Path("id") Long id, @Path("name")String name,
                                   @Path("teacherid") Long teacherid, @Path("typeattestation")String typeAttestation,
                                   @Path("maxscore") int maxScore);
    @DELETE("discipline/delete/{id}")
    Call<Integer> deleteDisciplineById(@Path("id") Long id);
}
