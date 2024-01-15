package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.AnnualAnalytics;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface AnalyticsService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("analytics/annualAnalytics/{year}/{accommodationId}/{hostUserId}")
    Call<AnnualAnalytics> getAnnualAnalytics(@Path("year") int year,
                                             @Path("accommodationId") Long accommodationId,
                                            @Path("hostUserId") Long hostUserId);
}
