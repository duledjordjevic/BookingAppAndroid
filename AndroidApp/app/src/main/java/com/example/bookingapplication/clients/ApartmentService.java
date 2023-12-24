package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApartmentService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/cards")
    Call<User> getAccommodationsCards();
}
