package ru.sfedu.studentsystem.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.sfedu.studentsystem.model.PracticalMaterial;

public interface PracticalMaterialService {
    @POST("/practicalmaterial/create/{name}/{teachercom}/{disciplineid}/{maxscore}/{deadline}/{studentid}/{teacherid}/{typesem}")
    Call<Integer> registryPracticalMaterial(@Path("name") String name, @Path("teachercom") String teacherCom,
                                            @Path("disciplineid") Long disciplineid, @Path("maxscore") int maxscore,
                                            @Path("deadline") String deadline, @Path("studentid") Long studentId,
                                            @Path("teacherid") Long teacherId, @Path("typesem") String typeSem);

    @GET("/practicalmaterial/get/discipline/{studentid}/{disciplineid}/{typesem}")
    Call<List<PracticalMaterial>> getPracticalMaterialByDiscipline(@Path("studentid")Long studentId, @Path("disciplineid") Long disciplineId,
                                                                   @Path("typesem") String typeSemester);
    @GET("/practicalmaterial/get/id/{id}")
    Call<PracticalMaterial> getPracticalMaterialById(@Path("id") Long id);

    @GET("/practicalmaterial/get/name/{name}")
    Call<List<PracticalMaterial>> getPracticalMaterialByName(@Path("name") String name);

    @GET("/practicalmaterial/get/studentid/{studentid}/{typeSem}")
    Call<List<PracticalMaterial>> getPracticalMaterialByStudentId(@Path("studentid") Long groupid, @Path("typeSem") String typeSem);

    @PUT("/practicalmaterial/update/{id}/{name}/{studentscore}/{datescore}")
    Call<Integer> updatePracticalMaterialSCoresAndName(@Path("id")Long id, @Path("name") String name,
                                                       @Path("studentscore")int studentscore,
                                                       @Path("datescore") String appendScore);

    @DELETE("/practicalmaterial/delete/{id}")
    Call<Integer> deletePracticalMaterial(@Path("id") Long id);
}
