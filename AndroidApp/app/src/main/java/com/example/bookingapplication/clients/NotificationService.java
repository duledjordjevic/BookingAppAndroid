package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.NotificationForHost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NotificationService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("notificationsForHost/host/{id}")
    Call<List<NotificationForHost>> getNotificationsForHost(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("notificationsForHost/{id}")
    Call<NotificationForHost> markNotificationAsRead(@Path("id") Long id);

}
