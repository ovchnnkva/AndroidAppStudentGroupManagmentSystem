package ru.sfedu.studentsystem.services;

import ru.sfedu.studentsystem.model.StudyGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudyGroupService {
    @GET("studygroup/get/id/{id}")
    Call<StudyGroup> getStudyGroupById(@Path("id") Long id);
    @GET("studygroup/get/code/{code}")
    Call<StudyGroup> getStudyGroupByCode(@Path("code") String code);
    @GET("studygroup/get/all")
    Call<List<StudyGroup>> getAll();
    @POST("studygroup/create/{course}/{specialization}/{groupscode}")
    Call<Integer> registerStudyGroup(@Path("course") int course, @Path("specialization") String specialization,
                                     @Path("groupscode") String groupscode);
    @PUT("studygroup/update/{id}/{groupscode}/{specialization}/{course}/{sessionid}" +
            "/{classesid}/{eventsid}")
    Call<Integer> updateStudyGroup(@Path("id")Long id, @Path("groupscode") String groupscode,
                                   @Path("specialization") String specialization, @Path("course") int course,
                                   @Path("sessionid")Long sessionid, @Path("classesid") Long classesid,
                                   @Path("eventsid") Long eventsid);
    @DELETE("studygroup/delete/{id}")
    Call<Integer> deleteStudyGroup(@Path("id") Long id);
}
