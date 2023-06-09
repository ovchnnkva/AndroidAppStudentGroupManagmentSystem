package ru.sfedu.studentsystem.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RoleService {
    @GET("/role/get/{uid}")
    Call<ResponseBody> getRoleByUid(@Path("uid") String uid);
    @POST("/role/save/{uid}/{role}")
    Call<Integer> saveRole(@Path("uid") String uid, @Path("role") String role);
}
