package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.Analytics;
import com.example.bookingapplication.model.AnnualAnalytics;

import java.util.Collection;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface AnalyticsService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("analytics/annualAnalytics/{year}/{accommodationId}/{hostUserId}")
    Call<AnnualAnalytics> getAnnualAnalytics(@Path("year") int year,
                                             @Path("accommodationId") Long accommodationId,
                                            @Path("hostUserId") Long hostUserId);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("analytics/allAccommodations/{hostUserId}")
    Call<Collection<Analytics>> getAnalyticsForAll(@Path("hostUserId") Long hostUserId,
                                                @QueryMap Map<String, String> queryParams);
}
