package ru.sfedu.studentsystem.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.sfedu.studentsystem.model.LectionMaterial;

public interface LectionMaterialService {
    @POST("lectionmaterial/create/{name}/{disciplineid}/{teachercom}")
    Call<Integer> registryLectionMaterial(@Path("name") String name, @Path("disciplineid") Long disciplineid,
                                          @Path("teachercom") String teacherCom);
    @GET("lectionmaterial/get/id/{id}")
    Call<LectionMaterial> getLectionMaterialById(@Path("id") Long id);
    @GET("lectionmaterial/get/groupid/{groupid}/{typesem}")
    Call<List<LectionMaterial>> getLectionMaterialByGroupId(@Path("groupid") Long groupid, @Path("typesem") String typeSem);
    @GET("lectionmaterial/name/{name}")
    Call<List<LectionMaterial>> getLectionMaterialByName(@Path("name") String name);
    @PUT("lectionmaterial/update/{id}/{name}/{groupid}/{comment}/{file}")
    Call<Integer> updateLectionMaterial(@Path("id") Long id, @Path("name") String name,
                                        @Path("groupid") Long groupid, @Path("comment") String comment,
                                        @Path("file") String file);
    @DELETE("lectionmaterial/delete/{id}")
    Call<Integer> deleteLectionMaterial(@Path("id") Long id);
}
