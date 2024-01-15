package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.ApartmentCard;
import com.example.bookingapplication.model.Card;
import com.example.bookingapplication.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApartmentService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/cards")
    Call<List<Card>> getAccommodationsCards();
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("guests/favourites/{id}")
    Call<List<Card>> getAllAccommodations(@Path("id") Long id);
}
