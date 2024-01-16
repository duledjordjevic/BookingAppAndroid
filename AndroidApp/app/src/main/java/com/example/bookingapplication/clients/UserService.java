package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.AdminForUpdate;
import com.example.bookingapplication.model.User;
import com.example.bookingapplication.model.UserDelete;
import com.example.bookingapplication.model.UserForUpdate;
import com.example.bookingapplication.model.UserReport;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("users/{id}")
    Call<User> getUser(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("users/{id}")
    Call<User> updateProfile(@Path("id") Long id,@Body UserForUpdate userForUpdate);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("users/delete/{id}")
    Call<Boolean> deleteProfile(@Body UserDelete userDelete, @Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("users/{id}")
    Call<User> getAdmin(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("users/admin/{id}")
    Call<User> updateAdmin(@Path("id") Long id,@Body AdminForUpdate userForUpdate);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("users/report")
    Call<UserReport> createUserReport(@Body UserReport userReport);
}
