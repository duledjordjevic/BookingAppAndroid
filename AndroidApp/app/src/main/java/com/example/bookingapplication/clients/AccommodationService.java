package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.Accommodation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AccommodationService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("accommodations")
    Call<Accommodation> createAccommodation(@Body Accommodation accommodation);
}
