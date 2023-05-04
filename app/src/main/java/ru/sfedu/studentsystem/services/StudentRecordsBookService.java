package ru.sfedu.studentsystem.services;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudentRecordsBookService {
    @GET("/recordsbook/get/discipline/{studentid}/{disciplineid}/{typesem}")
    Call<Map<String, Integer>> getScoreByDiscipline(@Path("studentid") Long studentId, @Path("disciplineid") Long disciplineid,
                                                    @Path("typesem") String typeSem);
    @POST("/recordsbook/create/{studentid}/{disciplineid}/{score}/{nametask}/{typesem}")
    Call<Integer> registryScore(@Path("studentid") Long studentid, @Path("disciplineid") Long disciplineid,
                                @Path("score") int score, @Path("nametask") String nametask,
                                @Path("typesem") String typeSem);
    @DELETE("/recordsbook/{studentid}/{disciplineid}/{typesem}")
    Call<Integer> deleteScore(@Path("studentid") Long studentId, @Path("disciplineid") Long disciplineId,
                              @Path("typesem") String typeSem);
    @PUT("/recordsbook/update/{studentid}/{disciplineid}/{score}/{nametask}/{typeSem}")
    Call<Integer> updateScore(@Path("studentid") Long studentid, @Path("disciplineid")Long disciplineid,
                              @Path("score") int score, @Path("nametask") String nameTask,
                              @Path("typeSem") String typeSem);
}
