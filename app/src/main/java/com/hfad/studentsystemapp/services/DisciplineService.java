package com.hfad.studentsystemapp.services;

import com.google.gson.JsonElement;
import com.hfad.studentsystemapp.model.Discipline;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DisciplineService {
    @POST("discipline/create/{name}")
    Call<Integer> registerDiscipline(@Path("name") String name);
    @GET("discipline/get/id/{id}")
    Call<Discipline> getDisciplineById(@Path("id") Long id);
    @GET("discipline/get/name/{name}")
    Call<List<Discipline>> getDisciplineByName(@Path("name") String name);
    @GET("discipline/get/groupid/{groupid}")
    Call<Discipline> getDisciplineByGroupId(@Path("groupid") Long id);
    @GET("discipline/get/all")
    Call<List<Discipline>> getAll();
    @PUT("discipline/update/{id}/{name}/{teacherid}")
    Call<Integer> updateDiscipline(@Path("id") Long id, @Path("name")String name,
                                      @Path("teacherid") Long teacherid);
    @DELETE("discipline/delete/{id}")
    Call<Integer> deleteDisciplineById(@Path("id") Long id);
}
