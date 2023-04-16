package com.hfad.studentsystemapp.services;

import com.hfad.studentsystemapp.model.Student;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudentService {
    @POST("student/create/{name}/{birthday}/{studygroupid}")
    Call<Student> registerStudent(@Path("name") String name, @Path("birthday") String birthday,
                                  @Path("studygroupid") Long studygroupid);
    @GET("student/get/id/{id}")
    Call<Student> getStudentById(@Path("id")Long id);
    @GET("student/get/name/{name}")
    Call<List<Student>> getStudentByGroupId(@Path("name") String name);
    @GET("student/get/groupid/{groupid}")
    Call<List<Student>> getStudentByGroupId(@Path("groupid") Long id);
    @PUT("student/update/{id}/{birthday}/{groupid}")
    Call<Integer> updateStudent(@Path("id") Long id, @Path("birthday") String birthday,
                                @Path("studygroupid")Long groupid);
    @DELETE("student/delete/{id}")
    Call<Integer> deleteStudent(@Path("id") Long id);
}
