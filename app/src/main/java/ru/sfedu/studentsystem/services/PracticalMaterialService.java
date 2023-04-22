package ru.sfedu.studentsystem.services;

import ru.sfedu.studentsystem.model.PracticalMaterial;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PracticalMaterialService {
    @POST("practicalmaterial/create/{name}/{disciplineid}/{maxscore}/{deadline}")
    Call<Integer> registryPtracticalMaterial(@Path("name") String name, @Path("disciplineid") Long disciplineid,
                                             @Path("maxscore") int maxscore, @Path("deadline") Date deadline);
    @GET("practicalmaterial/get/id/{id}")
    Call<PracticalMaterial> getPracticalMaterialById(@Path("id") Long id);
    @GET("practicalmaterial/get/name/{name}")
    Call<List<PracticalMaterial>> getPracticalMaterialByName(@Path("name") String name);
    @GET("practicalmaterial/get/groupid/{groupid}")
    Call<List<PracticalMaterial>> getPracticalMaterialByGroupId(@Path("groupid") Long groupid);
    @PUT("practicalmaterial/update/{id}/{name}/{deadline}/{disciplineid}/{maxscore}/{studentcomm}/{studentfile}/{studentscore}/{groupid}/{teachercomm}/{teacherfile}")
    Call<Integer> updatePracticalMaterial(@Path("id")Long id, @Path("name") String name,
                                          @Path("deadline") Date deadline, @Path("disciplineid") Long disciplineid,
                                          @Path("maxscore") int maxscore, @Path("studentcomm") String studentcomm,
                                          @Path("studentfile") String studentfile, @Path("studentscore")int studentscore,
                                          @Path("groupid") Long groupid, @Path("teachercomm") String teachercomm,
                                          @Path("teacherfile") String teacherfile);
    @DELETE("practicalmaterial/delete/{id}")
    Call<Integer> deletePracticalMaterial(@Path("id") Long id);
}
