package ru.sfedu.studentsystem.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.sfedu.studentsystem.model.Student;

public interface StudentService {
    @POST("student/create/{name}/{birthday}/{studygroupid}/{uid}")
    Call<Integer> registerStudent(@Path("name") String name, @Path("birthday") String birthday,
                                  @Path("studygroupid") Long studygroupid, @Path("uid") String uid);
    @GET("student/get/id/{id}")
    Call<Student> getStudentById(@Path("id")Long id);
    @GET("student/get/uid/{uid}")
    Call<Student> getStudentByUid(@Path("uid") String uid);
    @GET("student/get/name/{regex}")
    Call<List<Student>> getStudentByName(@Path("regex") String regex);
    @GET("student/get/groupid/{groupid}")
    Call<List<Student>> getStudentByGroupId(@Path("groupid") Long id);
    @PUT("student/update/{id}/{birthday}/{groupid}")
    Call<Integer> updateStudent(@Path("id") Long id, @Path("birthday") String birthday,
                                @Path("studygroupid")Long groupid);
    @DELETE("student/delete/{id}")
    Call<Integer> deleteStudent(@Path("id") Long id);

}
