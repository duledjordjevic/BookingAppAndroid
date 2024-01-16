package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.Card;
import com.example.bookingapplication.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GuestService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("guests/{id}/favourites")
    Call<List<Card>> getFavouritesAccommodations(@Path("id") Long id);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("guests/favourites/{guestUserId}/{accId}")
    Call<Boolean> addFavourite(@Path("guestUserId") Long guestUserId,@Path("accId") Long accId);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("guests/favourites/{guestUserId}/{accId}")
    Call<Boolean> removeFavourite(@Path("guestUserId") Long guestUserId,@Path("accId") Long accId);
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("guests/isFavourite/{guestUserId}/{accId}")
    Call<Boolean> isFavourite(@Path("guestUserId") Long guestUserId,@Path("accId") Long accId);



}
