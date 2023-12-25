package com.example.bookingapplication.clients;

import com.example.bookingapplication.model.Accommodation;
import com.example.bookingapplication.model.Card;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccommodationService {
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("accommodations")
    Call<Accommodation> createAccommodation(@Body Accommodation accommodation);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/{id}")
    Call<Accommodation> getAccommodationDetails(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/adminApproving")
    Call<List<Card>> getAccommodationsForApproving();
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("accommodations/host/{host_id}")
    Call<List<Card>> getAccommodationsForHosts(@Path("host_id") Long host_id);

}
