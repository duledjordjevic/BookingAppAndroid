package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.NotificationForGuest;
import com.example.bookingapplication.model.NotificationForHost;
import com.example.bookingapplication.model.NotificationTypeStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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
    Call<NotificationForHost> markHostNotificationAsRead(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("notificationsForGuest/guest/{id}")
    Call<List<NotificationForGuest>> getNotificationsForGuest(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("notificationsForGuest/{id}")
    Call<NotificationForGuest> markGuestNotificationAsRead(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("notificationsForHost/hostNotificationStatus/{id}")
    Call<List<NotificationTypeStatus>> getHostNotificationsTypeStatus(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("notificationsForHost/changeNotificationStatus")
    Call<Object> changeHostNotificationTypeStatus(@Body NotificationTypeStatus notificationTypeStatus);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("notificationsForGuest/guestNotificationStatus/{id}")
    Call<List<NotificationTypeStatus>> getGuestNotificationsTypeStatus(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("notificationsForGuest/changeNotificationStatus")
    Call<Object> changeGuestNotificationTypeStatus(@Body NotificationTypeStatus notificationTypeStatus);



}
