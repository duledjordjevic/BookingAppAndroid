package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.User;
import com.example.bookingapplication.model.UserDelete;
import com.example.bookingapplication.model.UserForUpdate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UpdateUserService {
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
}
